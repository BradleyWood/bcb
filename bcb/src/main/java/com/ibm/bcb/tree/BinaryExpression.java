package com.ibm.bcb.tree;

import com.ibm.bcb.Operator;
import com.ibm.bcb.Primitive;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Type;

import static org.objectweb.asm.Opcodes.*;

@EqualsAndHashCode
@Data(staticConstructor = "of")
public class BinaryExpression implements Expression {

    private final Expression lhs;
    private final Expression rhs;
    private final String opcode;

    @Override
    public Type getType(final MethodContext ctx) {
        final Type lhsType = lhs.getType(ctx);
        final Type rhsType = rhs.getType(ctx);

        final Operator operator = Operator.findOperator(opcode, lhsType, rhsType);

        if (operator == null) {
            throw new IllegalArgumentException("No such operator!");
        }

        return operator.getResultType();
    }

    @Override
    public Type evaluate(final MethodContext ctx, final MethodVisitor mv) {
        final Type resultType = getType(ctx);

        final Type lhsType = lhs.getType(ctx);
        final Type rhsType = rhs.getType(ctx);

        final Operator operator = Operator.findOperator(opcode, lhsType, rhsType);

        if (operator == null) {
            throw new IllegalArgumentException("No such operator!");
        }

        final Primitive resultHandler = Primitive.getPrimitive(resultType);
        final Primitive lhsHandler = Primitive.getPrimitive(lhsType);
        final Primitive rhsHandler = Primitive.getPrimitive(rhsType);

        if (operator.isCompareInstruction()) {
            final Operator conversionTrick = Operator.findOperator("+", lhsType, rhsType);
            final Label elseLabel = new Label();
            final Label doneLabel = new Label();

            if (conversionTrick != null) {
                final Primitive typesToCompare = Primitive.getPrimitive(conversionTrick.getResultType());
                lhs.evaluate(ctx, mv);
                lhsHandler.cast(mv, typesToCompare);

                rhs.evaluate(ctx, mv);
                rhsHandler.cast(mv, typesToCompare);

                operator.evaluate(elseLabel, mv);

                mv.visitInsn(ICONST_1);
                mv.visitJumpInsn(GOTO, doneLabel);
            } else {
                throw new UnsupportedOperationException("Reference comparisons not supported");
            }

            mv.visitLabel(elseLabel);
            mv.visitInsn(ICONST_0);
            mv.visitLabel(doneLabel);
        } else {
            lhs.evaluate(ctx, mv);
            lhsHandler.cast(mv, resultHandler);

            rhs.evaluate(ctx, mv);
            rhsHandler.cast(mv, resultHandler);

            operator.evaluate(mv);
        }

        return resultType;
    }
}
