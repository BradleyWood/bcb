package com.ibm.bcb.tree;

import com.ibm.bcb.BCBMethod;
import com.ibm.bcb.MethodRef;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.SneakyThrows;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Type;

import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.objectweb.asm.Opcodes.INVOKESTATIC;
import static org.objectweb.asm.Opcodes.INVOKEVIRTUAL;

@Data
@EqualsAndHashCode
@AllArgsConstructor(staticName = "of")
public class CallExpression implements Expression {

    private final Expression proceedingExpression;
    private final String declaringClass;
    private final String name;
    private final Expression[] args;

    public CallExpression(final Expression proceedingExpression, final String name, final Expression... args) {
        this.proceedingExpression = proceedingExpression;
        this.name = name;
        this.args = args;
        this.declaringClass = null;
    }

    public CallExpression(final String declaringClass, final String name, final Expression[] args) {
        this.declaringClass = declaringClass;
        this.proceedingExpression = null;
        this.name = name;
        this.args = args;
    }

    private MethodRef findMethod(final MethodContext ctx) {
        final List<Type> argTypes = Arrays.stream(args).map(a -> a.getType(ctx)).collect(Collectors.toList());

        if (proceedingExpression != null) {
            final Type instanceType = proceedingExpression.getType(ctx);
            return ctx.findMethod(instanceType.getInternalName(), name, argTypes);
        }

        return ctx.findMethod(declaringClass, name, argTypes);
    }

    @Override
    public Type getType(MethodContext ctx) {
        return findMethod(ctx).getType().getReturnType();
    }

    @Override
    @SneakyThrows
    public Type evaluate(final MethodContext ctx, final MethodVisitor mv) {
        final MethodRef method = findMethod(ctx);

        if (method == null) {
            throw new NoSuchMethodException(name);
        }

        if (proceedingExpression == null && !Modifier.isStatic(method.getModifiers())) {
            throw new IllegalStateException("Cannot call non-static method from a static context");
        }

        if (method.isIntrinsic() && method.isInline()) {
            ctx.beginScope();

            final BCBMethod intrinsic = ctx.findIntrinsic(name, method.getType().getArgumentTypes());
            final List<String> argNames = intrinsic.getArgNames();

            for (int i = 0; i < args.length; i++) {
                StoreExpression.of(argNames.get(i), args[i]).evaluate(ctx, mv);
            }

            final Label inlineMethodEnd = new Label();
            final Label currentMethodEnd = ctx.getMethodEnd();
            final boolean isInline = ctx.isInlineMethod();

            ctx.setMethodEnd(inlineMethodEnd);
            ctx.setInlineMethod(true);

            for (final Statement statement : intrinsic.getStatements()) {
                statement.evaluate(ctx, mv);
            }

            ctx.setInlineMethod(isInline);
            mv.visitLabel(inlineMethodEnd);
            ctx.setMethodEnd(currentMethodEnd);

            ctx.endScope();
        } else {
            if (proceedingExpression != null) {
                proceedingExpression.evaluate(ctx, mv);
            }

            for (final Expression arg : args) {
                arg.evaluate(ctx, mv);
            }

            if (Modifier.isStatic(method.getModifiers())) {
                mv.visitMethodInsn(INVOKESTATIC, method.getDeclaringClass(), name, method.getType().getDescriptor());
            } else {
                mv.visitMethodInsn(INVOKEVIRTUAL, method.getDeclaringClass(), name, method.getType().getDescriptor());
            }
        }

        return method.getType().getReturnType();
    }

    public static CallExpression of(final Expression proceedingExpression, final String name, final Expression... args) {
        return new CallExpression(proceedingExpression, name, args);
    }

    public static CallExpression of(final String declaringClass, final String name, final Expression... args) {
        return new CallExpression(null, declaringClass, name, args);
    }
}
