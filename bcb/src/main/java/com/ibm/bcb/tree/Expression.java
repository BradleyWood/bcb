package com.ibm.bcb.tree;

import org.objectweb.asm.Type;

public interface Expression extends Statement {

    Type getType(MethodContext ctx);

    default Expression add(final Expression rhs) {
        return bop("+", rhs);
    }

    default Expression sub(Expression rhs) {
        return bop("-", rhs);
    }

    default Expression mul(Expression rhs) {
        return bop("*", rhs);
    }

    default Expression div(Expression rhs) {
        return bop("/", rhs);
    }

    default Expression bop(final String operation, final Expression rhs) {
        return BinaryExpression.of(this, rhs, operation);
    }
}
