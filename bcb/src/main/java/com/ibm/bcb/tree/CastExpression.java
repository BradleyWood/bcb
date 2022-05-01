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

        expression.evaluate(ctx, mv);

        if (ep == null && tp == null) {
            // todo: check if this cast is guaranteed to fail
            if (!targetType.equals(Type.getType(Object.class)))
                mv.visitTypeInsn(CHECKCAST, targetType.getInternalName());
        } else if (ep != null && tp == null) {
            if (targetType.equals(Type.getType(Number.class)) || targetType.equals(Type.getType(Object.class))) {
                ep.box(mv);
            } else {
                throw new IllegalStateException("Cannot cast primitive to type: " + targetType.getInternalName());
            }
        } else if (ep == null) {
            throw new IllegalStateException("Cannot cast non-primitive type " + expressionType.getInternalName() +
                    " to type " + targetType.getInternalName());
        } else {
            if (Primitive.getBoxed(expressionType) != null) {
                // This is a wrapped primitive value (such as Integer)
                // Unbox to its primitive type
                ep.unbox(mv);
            }

            ep.cast(mv, tp);

            if (Primitive.getBoxed(targetType) != null) {
                // The target type is a wrapper primitive
                tp.box(mv);
            }
        }

        return getType(ctx);
    }
}
