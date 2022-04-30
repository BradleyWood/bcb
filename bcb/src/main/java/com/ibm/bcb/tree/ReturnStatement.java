package com.ibm.bcb.tree;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Type;

@EqualsAndHashCode
@Data(staticConstructor = "of")
public class ReturnStatement implements Statement {

    private final Expression result;

    @Override
    public Type evaluate(final MethodContext ctx, final MethodVisitor mv) {
        final Type resultType = result != null ? result.evaluate(ctx, mv) : Type.VOID_TYPE;

        TypeOperations.ret(resultType, mv);

        return Type.VOID_TYPE;
    }
}
