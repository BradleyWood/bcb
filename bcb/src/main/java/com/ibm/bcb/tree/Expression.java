package com.ibm.bcb.tree;

import org.objectweb.asm.Type;

public interface Expression extends Statement {

    Type getType(MethodContext ctx);

    default Expression add(final Expression rhs) {
        return bop("+", rhs);
    }

    default Expression sub(final Expression rhs) {
        return bop("-", rhs);
    }

    default Expression mul(final Expression rhs) {
        return bop("*", rhs);
    }

    default Expression div(final Expression rhs) {
        return bop("/", rhs);
    }

    default Expression eq(final Expression rhs) {
        return bop("==", rhs);
    }

    default Expression gt(final Expression rhs) {
        return bop(">", rhs);
    }

    default Expression gte(final Expression rhs) {
        return bop(">=", rhs);
    }

    default Expression lt(final Expression rhs) {
        return bop("<", rhs);
    }

    default Expression lte(final Expression rhs) {
        return bop("<=", rhs);
    }

    default Expression toByte() {
        return cast(Type.BYTE_TYPE);
    }

    default Expression toShort() {
        return cast(Type.SHORT_TYPE);
    }

    default Expression toInt() {
        return cast(Type.INT_TYPE);
    }

    default Expression toLong() {
        return cast(Type.LONG_TYPE);
    }

    default Expression toFloat() {
        return cast(Type.FLOAT_TYPE);
    }

    default Expression toDouble() {
        return cast(Type.DOUBLE_TYPE);
    }

    default Expression cast(final Type targetType) {
        return CastExpression.of(this, targetType);
    }

    default Expression bop(final String operation, final Expression rhs) {
        return BinaryExpression.of(this, rhs, operation);
    }
}
