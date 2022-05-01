package com.ibm.bcb.tree;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Type;

import static org.objectweb.asm.Opcodes.GOTO;

@EqualsAndHashCode
@Data(staticConstructor = "of")
public class ReturnStatement implements Statement {

    private final Expression result;

    @Override
    public Type evaluate(final MethodContext ctx, final MethodVisitor mv) {
        final Type resultType = result != null ? result.evaluate(ctx, mv) : Type.VOID_TYPE;

        if (ctx.isInlineMethod()) {
            mv.visitJumpInsn(GOTO, ctx.getMethodEnd());
        } else {
            TypeOperations.ret(resultType, mv);
        }

        return Type.VOID_TYPE;
    }
}
