package com.ibm.bcb;

import com.ibm.bcb.tree.Block;
import com.ibm.bcb.tree.MethodContext;
import com.ibm.bcb.tree.Statement;
import lombok.*;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Type;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.Method;
import java.util.List;

import static org.objectweb.asm.Opcodes.*;

@Builder
@AllArgsConstructor
public @Data class BCBMethod {

    @Singular
    private final List<Statement> statements;

    @Singular
    private final List<String> argNames;

    @Singular
    private final List<Type> argTypes;

    @Builder.Default
    private final Type returnType = Type.VOID_TYPE;

    @Builder.Default
    private final boolean inline = false;

    @Builder.Default
    private final String declaringClass = "AnonymousClass";

    @Builder.Default
    private final String name = "anonymousMethod";

    @Builder.Default
    private final int modifiers = ACC_PUBLIC + ACC_STATIC;

    public static class BCBMethodBuilder {

        public BCBMethodBuilder arg(final String name, final Type type) {
            return argName(name).argType(type);
        }

        public BCBMethodBuilder arg(final String name, final String type) {
            return argName(name).argType(Type.getType(type));
        }

        public BCBMethodBuilder methodType(final Type type) {
            returnType(type.getReturnType());

            for (final Type argumentType : type.getArgumentTypes()) {
                argType(argumentType);
            }

            return this;
        }

        public BCBMethodBuilder args(final String... argNames) {
            for (final String argName : argNames) {
                argName(argName);
            }

            return this;
        }

        public BCBMethodBuilder body(final Statement... statements) {
            final Block block = Block.of(statements);
            statement(block);

            return this;
        }
    }

    public Type getMethodType() {
        return Type.getMethodType(returnType, argTypes.toArray(new Type[0]));
    }

    public byte[] toByteArray() {
        final ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_MAXS | ClassWriter.COMPUTE_FRAMES);
        cw.visit(V1_8, ACC_PUBLIC + ACC_SUPER, declaringClass, null, "java/lang/Object", null);

        final Label methodEnd = new Label();
        final MethodVisitor mv = cw.visitMethod(modifiers, name, getMethodType().getDescriptor(), null, null);
        final Type clazzType = Type.getType("L" + declaringClass + ";");
        final MethodContext ctx = new MethodContext(clazzType, name, ACC_PUBLIC + ACC_STATIC, getMethodType(), argNames, methodEnd);

        Block.of(statements).evaluate(ctx, mv);

        mv.visitLabel(methodEnd);

        for (final String argName : argNames) {
            mv.visitParameter(argName, 0);
        }

        mv.visitMaxs(0, 0);
        mv.visitEnd();

        cw.visitEnd();
        return cw.toByteArray();
    }

    @SneakyThrows
    private Class<?> loadClass() {
        final byte[] bytes = toByteArray();

        final ClassLoader loader = new ClassLoader() {
            @Override
            protected Class<?> findClass(final String name) {
                if (name.equals(declaringClass.replace("/", "."))) {
                    return defineClass(name, bytes, 0, bytes.length);
                }

                return null;
            }
        };

        return loader.loadClass(declaringClass.replace("/", "."));
    }

    @SneakyThrows
    public void save() {
        saveToFolder(new File("."));
    }

    @SneakyThrows
    public void saveToFolder(final File file) {
        final FileOutputStream fos = new FileOutputStream(new File(file, String.format("%s.class", getDeclaringClass())));
        saveToFile(fos);
        fos.close();
    }

    @SneakyThrows
    public void saveToFile(final File file) {
        final FileOutputStream fos = new FileOutputStream(file);
        saveToFile(fos);
        fos.close();
    }

    @SneakyThrows
    public void saveToFile(final OutputStream out) {
        out.write(toByteArray());
    }

    @SneakyThrows
    public Method toMethod() {
        final Method[] declaredMethods = loadClass().getDeclaredMethods();

        for (final Method declaredMethod : declaredMethods) {
            if (Type.getType(declaredMethod).equals(getMethodType())) {
                return declaredMethod;
            }
        }

        return null;
    }

    @SneakyThrows
    public MethodHandle toMethodHandle() {
        return MethodHandles.lookup().unreflect(toMethod());
    }
}
