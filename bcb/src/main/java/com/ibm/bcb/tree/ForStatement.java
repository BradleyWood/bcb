package com.ibm.bcb.tree;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Type;

import static org.objectweb.asm.Opcodes.*;

@EqualsAndHashCode
@Data(staticConstructor = "of")
public class ForStatement implements Statement {

    private final Expression init;
    private final Expression condition;
    private final Expression after;
    private final Statement body;

    @Override
    public Type evaluate(final MethodContext ctx, final MethodVisitor mv) {
        ctx.beginScope();

        if (init != null) {
            init.evaluate(ctx, mv);
        }

        final Type conditionType = condition.getType(ctx);

        if (conditionType.equals(Type.FLOAT_TYPE) || conditionType.equals(Type.DOUBLE_TYPE) ||
                conditionType.equals(Type.VOID_TYPE) || conditionType.equals(Type.CHAR_TYPE) ||
                conditionType.getInternalName().contains("[")) {
            throw new IllegalArgumentException("Conditional expression cannot evaluate to void, char, float, double or array type");
        }

        final Label beginLoop = new Label();
        final Label endLoop = new Label();

        mv.visitLabel(beginLoop);
        condition.evaluate(ctx, mv);

        if (conditionType.equals(Type.BOOLEAN_TYPE) || conditionType.equals(Type.BYTE_TYPE) || conditionType.equals(Type.SHORT_TYPE) || conditionType.equals(Type.INT_TYPE)) {
            mv.visitJumpInsn(IFEQ, endLoop);
        } else {
            mv.visitJumpInsn(IFNULL, endLoop);
        }

        final Type bodyType = body.evaluate(ctx, mv);

        if (!bodyType.equals(Type.VOID_TYPE)) {
            TypeOperations.pop(bodyType, mv);
        }

        if (after != null) {
            after.evaluate(ctx, mv);
        }

        mv.visitJumpInsn(GOTO, beginLoop);
        mv.visitLabel(endLoop);

        ctx.endScope();

        return Type.VOID_TYPE;
    }
}
