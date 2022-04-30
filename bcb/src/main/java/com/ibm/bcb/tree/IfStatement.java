package com.ibm.bcb.tree;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Type;

import static org.objectweb.asm.Opcodes.*;

@EqualsAndHashCode
@Data(staticConstructor = "of")
public class IfStatement implements Statement {

    private final Expression condition;
    private final Statement then;
    private final Statement _else;

    @Override
    public Type evaluate(final MethodContext ctx, final MethodVisitor mv) {
        final Type conditionType = condition.evaluate(ctx, mv);

        if (conditionType.equals(Type.FLOAT_TYPE) || conditionType.equals(Type.DOUBLE_TYPE) ||
                conditionType.equals(Type.VOID_TYPE) || conditionType.equals(Type.CHAR_TYPE) ||
                conditionType.getInternalName().contains("[")) {
            throw new IllegalArgumentException("Conditional expression cannot evaluate to void, char, float, double or array type");
        }

        final Label elseLabel = new Label();

        if (conditionType.equals(Type.BYTE_TYPE) || conditionType.equals(Type.SHORT_TYPE) || conditionType.equals(Type.INT_TYPE)) {
            mv.visitJumpInsn(IFEQ, elseLabel);
        } else {
            mv.visitJumpInsn(IFNULL, elseLabel);
        }

        final Type thenResult = then.evaluate(ctx, mv);

        if (!thenResult.equals(Type.VOID_TYPE)) {
            TypeOperations.pop(thenResult, mv);
        }

        if (_else != null) {
            final Label doneLabel = new Label();

            mv.visitJumpInsn(GOTO, doneLabel);
            mv.visitLabel(elseLabel);

            final Type elseResult = _else.evaluate(ctx, mv);

            if (!elseResult.equals(Type.VOID_TYPE)) {
                TypeOperations.pop(elseResult, mv);
            }

            mv.visitLabel(doneLabel);
        } else {
            mv.visitLabel(elseLabel);
        }

        return Type.VOID_TYPE;
    }
}
