package com.ibm.bcb.tree;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Type;

import static org.objectweb.asm.Opcodes.PUTFIELD;
import static org.objectweb.asm.Opcodes.PUTSTATIC;

@EqualsAndHashCode
@Data(staticConstructor = "of")
public class StoreExpression implements Expression {

    private final String declaringClass;
    private final String varName;
    private final Expression instance;
    private final Expression value;

    @Override
    public Type getType(final MethodContext ctx) {
        if (declaringClass != null) {
            return ctx.findField(declaringClass, varName).getType();
        } else if (instance != null) {
            return ctx.findField(instance.getType(ctx).getInternalName(), varName).getType();
        }

        return value.getType(ctx);
    }

    @Override
    public Type evaluate(final MethodContext ctx, final MethodVisitor mv) {
        final Type resultType = getType(ctx);

        if (instance != null) {
            instance.evaluate(ctx, mv);
        }


        if (instance == null && declaringClass == null) {
            MethodContext.Var var = ctx.findVar(varName);

            if (var == null) {
                ctx.putVar(varName, resultType, 0);
                var = ctx.findVar(varName);
            }

            value.evaluate(ctx, mv);
            TypeOperations.store(resultType, var.getIndex(), mv);
        } else if (instance == null) {
            value.evaluate(ctx, mv);
            mv.visitFieldInsn(PUTSTATIC, declaringClass, varName, resultType.getDescriptor());
        } else {
            instance.evaluate(ctx, mv);
            value.evaluate(ctx, mv);
            mv.visitFieldInsn(PUTFIELD, instance.getType(ctx).getInternalName(), varName, resultType.getDescriptor());
        }

        return Type.VOID_TYPE;
    }

    public static StoreExpression of(final String varName, final Expression expression) {
        return of(null, varName, null, expression);
    }
}
