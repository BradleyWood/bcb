package com.ibm.bcb.tree;

import com.ibm.bcb.Primitive;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Type;

import static org.objectweb.asm.Opcodes.CHECKCAST;

@EqualsAndHashCode
@Data(staticConstructor = "of")
public class CastExpression implements Expression {

    private final Expression expression;
    private final Type targetType;

    @Override
    public Type getType(MethodContext ctx) {
        return targetType;
    }

    @Override
    public Type evaluate(final MethodContext ctx, final MethodVisitor mv) {
        final Type expressionType = expression.getType(ctx);
        final Primitive ep = Primitive.byType(expressionType);
        final Primitive tp = Primitive.byType(targetType);

        if (ep == null && tp == null) {
            // todo: check if this cast is guaranteed to fail
            mv.visitTypeInsn(CHECKCAST, targetType.getInternalName());
        } else if (ep == null || tp == null) {
            throw new IllegalStateException("Cast is guaranteed to fail");
        } else {
            expression.evaluate(ctx, mv);

            if (Primitive.getBoxed(expressionType) != null) {
                ep.unbox(mv);
            }

            ep.cast(mv, tp);

            if (Primitive.getBoxed(targetType) != null) {
                tp.box(mv);
            }
        }

        return getType(ctx);
    }
}
