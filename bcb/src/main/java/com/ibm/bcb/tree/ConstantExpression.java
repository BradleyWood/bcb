package com.ibm.bcb.tree;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Type;

import static org.objectweb.asm.Opcodes.*;

@EqualsAndHashCode
@Data(staticConstructor = "of")
public class ConstantExpression implements Expression {

    private final Object constant;

    @Override
    public Type getType(MethodContext ctx) {
        if (constant == null) {
            return Type.getType(Object.class);
        } else if (constant instanceof Byte || constant instanceof Short || constant instanceof Integer) {
            return Type.INT_TYPE;
        } else if (constant instanceof Long) {
            return Type.LONG_TYPE;
        } else if (constant instanceof Float) {
            return Type.FLOAT_TYPE;
        } else if (constant instanceof Double) {
            return Type.DOUBLE_TYPE;
        } else if (constant instanceof String) {
            return Type.getType(String.class);
        } else if (constant instanceof Class || constant instanceof Type) {
            return Type.getType(Class.class);
        }

        throw new IllegalArgumentException("Unknown constant type: " + constant);
    }

    @Override
    public Type evaluate(MethodContext ctx, MethodVisitor mv) {
        final Type type = getType(ctx);

        if (constant == null) {
            mv.visitInsn(ACONST_NULL);
            return type;
        }

        if (type.equals(Type.INT_TYPE)) {
            int value = ((Number) constant).intValue();

            if (value >= -1 && value <= 5) {
                mv.visitInsn(ICONST_0 + value);
            } else if (value >= Byte.MIN_VALUE && value <= Byte.MAX_VALUE) {
                mv.visitIntInsn(BIPUSH, value);
            } else if (value >= Short.MIN_VALUE && value <= Short.MAX_VALUE) {
                mv.visitIntInsn(SIPUSH, value);
            } else {
                mv.visitLdcInsn(value);
            }
        } else if (type.equals(Type.LONG_TYPE)) {
            long value = ((Number) constant).longValue();

            if (value == 0 || value == 1) {
                mv.visitInsn(LCONST_0 + (int) value);
            } else {
                mv.visitLdcInsn(constant);
            }
        } else if (type.equals(Type.FLOAT_TYPE)) {
            float value = ((Number) constant).floatValue();

            if (value == 0f || value == 1f) {
                mv.visitInsn(FCONST_0 + (int) value);
            } else {
                mv.visitLdcInsn(constant);
            }
        } else if (type.equals(Type.DOUBLE_TYPE)) {
            double value = ((Number) constant).doubleValue();

            if (value == 0d || value == 1d) {
                mv.visitInsn(DCONST_0 + (int) value);
            } else {
                mv.visitLdcInsn(constant);
            }
        } else if (constant instanceof Class) {
            mv.visitLdcInsn(Type.getType((Class<?>) constant));
        } else {
            mv.visitLdcInsn(constant);
        }

        return type;
    }
}
