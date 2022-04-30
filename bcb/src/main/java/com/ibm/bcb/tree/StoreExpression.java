package com.ibm.bcb.tree;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Type;

import static org.objectweb.asm.Opcodes.*;

@EqualsAndHashCode
@Data(staticConstructor = "of")
public class StoreExpression implements Expression {

    private final String declaringClass;
    private final String varName;
    private final Expression instance;
    private final Expression value;

    @Override
    public Type getType(final MethodContext ctx) {
        return value.getType(ctx);
    }

    @Override
    public Type evaluate(final MethodContext ctx, final MethodVisitor mv) {
        final Type resultType = getType(ctx);

        if (instance != null) {
            instance.evaluate(ctx, mv);
        }

        value.evaluate(ctx, mv);

        if (instance == null && declaringClass == null) {
            MethodContext.Var var = ctx.findVar(varName);

            if (var == null) {
                ctx.putVar(varName, resultType, 0);
                var = ctx.findVar(varName);
            }

            TypeOperations.store(resultType, var.getIndex(), mv);
        } else if (instance == null) {
            mv.visitFieldInsn(PUTSTATIC, declaringClass, varName, resultType.getInternalName());
        } else {
            instance.evaluate(ctx, mv);
            mv.visitFieldInsn(PUTFIELD, declaringClass, varName, resultType.getInternalName());
        }

        return resultType;
    }

    public static StoreExpression of(final String varName, final Expression expression) {
        return of(null, varName, null, expression);
    }
}
