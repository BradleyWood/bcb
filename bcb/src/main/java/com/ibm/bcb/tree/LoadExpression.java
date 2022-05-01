package com.ibm.bcb.tree;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Type;

import static org.objectweb.asm.Opcodes.GETFIELD;
import static org.objectweb.asm.Opcodes.GETSTATIC;

@EqualsAndHashCode
@Data(staticConstructor = "of")
public class LoadExpression implements Expression {

    private final String declaringClass;
    private final String varName;
    private final String type;
    private final Expression instance;

    @Override
    public Type getType(final MethodContext ctx) {
        if (type != null) {
            return Type.getType(type);
        } else if (declaringClass != null) {
            return ctx.findField(declaringClass, varName).getType();
        }

        return ctx.findVar(varName).getType();
    }

    @Override
    public Type evaluate(MethodContext ctx, MethodVisitor mv) {
        final Type resultType = getType(ctx);

        if (instance == null && declaringClass == null) {
            TypeOperations.load(resultType, ctx.findVar(varName).getIndex(), mv);
        } else if (instance == null) {
            mv.visitFieldInsn(GETSTATIC, declaringClass, varName, resultType.getDescriptor());
        } else {
            instance.evaluate(ctx, mv);
            mv.visitFieldInsn(GETFIELD, declaringClass, varName, resultType.getDescriptor());
        }

        return resultType;
    }

    public static LoadExpression of(final String varName) {
        return of(null, varName, null, null);
    }
}
