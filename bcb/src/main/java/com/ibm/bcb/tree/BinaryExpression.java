package com.ibm.bcb.tree;

import com.ibm.bcb.Operator;
import com.ibm.bcb.Primitive;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Type;

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

        lhs.evaluate(ctx, mv);
        final Primitive lhsHandler = Primitive.getPrimitive(lhsType);
        final Primitive resultHandler = Primitive.getPrimitive(resultType);
        lhsHandler.cast(mv, resultHandler);

        rhs.evaluate(ctx, mv);
        final Primitive rhsHandler = Primitive.getPrimitive(rhsType);
        rhsHandler.cast(mv, resultHandler);

        operator.evaluate(mv);

        return resultType;
    }
}
