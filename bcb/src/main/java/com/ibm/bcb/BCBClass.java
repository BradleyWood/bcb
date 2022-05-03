package com.ibm.bcb;

import com.ibm.bcb.tree.Block;
import com.ibm.bcb.tree.MethodContext;
import lombok.*;
import org.objectweb.asm.*;

import java.util.List;

import static org.objectweb.asm.Opcodes.*;

@Data
@Builder
@EqualsAndHashCode
public class BCBClass {

    @Builder.Default
    private final String name = "AnonymousClass";
    @Builder.Default
    private final String parent = "java/lang/Object";
    @Singular
    private final List<BCBMethod> methods;

    public byte[] toByteArray() {
        final ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_MAXS | ClassWriter.COMPUTE_FRAMES);
        cw.visit(V1_8, ACC_PUBLIC + ACC_SUPER, name, null, parent, null);

        // Todo; Add default constructor if required
        // Modify existing default constructor to initial default fields

        // Todo; Add class initializer if required
        // Modify existing initializer to initial static fields

        for (final BCBMethod method : methods) {
            writeToClass(method, cw);
        }

        cw.visitEnd();
        return cw.toByteArray();
    }

    public void writeToClass(final BCBMethod method, final ClassVisitor cv) {
        final Label methodEnd = new Label();
        final MethodVisitor mv = cv.visitMethod(method.getModifiers(), method.getName(), method.getMethodType().getDescriptor(), null, null);
        final Type clazzType = Type.getType("L" + name + ";");
        final MethodContext ctx = new MethodContext(clazzType, method.getName(), ACC_PUBLIC + ACC_STATIC, method.getMethodType(), method.getArgNames(), methodEnd);

        Block.of(method.getStatements()).evaluate(ctx, mv);

        mv.visitLabel(methodEnd);

        for (final String argName : method.getArgNames()) {
            mv.visitParameter(argName, 0);
        }

        mv.visitMaxs(0, 0);
        mv.visitEnd();
    }

    @SneakyThrows
    public Class<?> loadClass() {
        final byte[] bytes = toByteArray();

        final ClassLoader loader = new ClassLoader() {
            @Override
            protected Class<?> findClass(final String name) {
                if (name.equals(name.replace("/", "."))) {
                    return defineClass(name, bytes, 0, bytes.length);
                }

                return null;
            }
        };

        return loader.loadClass(name.replace("/", "."));
    }
}
