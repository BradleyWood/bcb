package com.ibm.bcb;

import lombok.AllArgsConstructor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Type;

import static org.objectweb.asm.Opcodes.*;

@AllArgsConstructor
public enum Primitive {

    INT(Type.INT_TYPE, Type.getType(Integer.class), I2B, I2S, NOP, I2L, I2F, I2D, ILOAD, ISTORE, IALOAD, IASTORE, IRETURN, DUP, POP),
    BYTE(Type.BYTE_TYPE, Type.getType(Byte.class), NOP, NOP, NOP, I2L, I2F, I2D, ILOAD, ISTORE, BALOAD, BALOAD, IRETURN, DUP, POP),
    CHAR(Type.CHAR_TYPE, Type.getType(Character.class), I2B, NOP, NOP, I2L, I2F, I2D, ILOAD, ISTORE, CALOAD, CASTORE, IRETURN, DUP, POP),
    LONG(Type.LONG_TYPE, Type.getType(Long.class), -1, -1, L2I, L2D, L2F, L2D, LLOAD, LSTORE, LALOAD, LASTORE, LRETURN, DUP2, POP2),
    SHORT(Type.SHORT_TYPE, Type.getType(Short.class), I2B, NOP, NOP, I2L, I2F, I2D, ILOAD, ISTORE, SALOAD, SASTORE, IRETURN, DUP, POP),
    FLOAT(Type.FLOAT_TYPE, Type.getType(Float.class), -1, -1, F2I, F2L, NOP, F2D, FLOAD, FSTORE, FALOAD, FASTORE, FRETURN, DUP, POP),
    DOUBLE(Type.DOUBLE_TYPE, Type.getType(Double.class), -1, -1, D2I, D2L, D2F, NOP, DLOAD, DSTORE, DALOAD, DASTORE, DRETURN, DUP2, POP2),
    BOOLEAN(Type.BOOLEAN_TYPE, Type.getType(Boolean.class), I2B, I2S, NOP, I2L, I2F, I2D, ILOAD, ISTORE, BALOAD, BASTORE, IRETURN, DUP, POP);

    private final Type primitive;
    private final Type boxed;
    private final int toByteOpcode;
    private final int toShortOpcode;
    private final int toIntOpcode;
    private final int toLongOpcode;
    private final int toFloatOpcode;
    private final int toDoubleOpcode;

    private final int loadOpcode;
    private final int storeOpcode;
    private final int arrayLoadOpcode;
    private final int arrayStoreOpcode;
    private final int retOpcode;
    private final int dupOpcode;
    private final int popOpcode;

    public static Primitive byType(final Type type) {
        for (Primitive value : values()) {
            if (value.primitive.equals(type)) {
                return value;
            } else if (value.boxed.equals(type)) {
                return value;
            }
        }

        return null;
    }

    public static Primitive getBoxed(final Type type) {
        for (final Primitive value : values()) {
            if (value.boxed.equals(type)) {
                return value;
            }
        }

        return null;
    }

    public static Primitive getPrimitive(final Type type) {
        for (final Primitive value : values()) {
            if (value.primitive.equals(type)) {
                return value;
            }
        }

        return null;
    }

    public void cast(final MethodVisitor mv, final Primitive target) {
        switch (target) {
            case INT:
                if (toIntOpcode != NOP) {
                    mv.visitInsn(toIntOpcode);
                }
                break;
            case BOOLEAN:
            case BYTE:
                if (toByteOpcode == -1) {
                    mv.visitInsn(toIntOpcode);
                    mv.visitInsn(INT.toByteOpcode);
                } else if (toByteOpcode != NOP) {
                    mv.visitInsn(toIntOpcode);
                }
                break;
            case LONG:
                if (toLongOpcode != NOP) {
                    mv.visitInsn(toLongOpcode);
                }
                break;
            case FLOAT:
                if (toFloatOpcode != NOP) {
                    mv.visitInsn(toFloatOpcode);
                }
                break;
            case CHAR:
            case SHORT:
                if (toByteOpcode == -1) {
                    mv.visitInsn(toIntOpcode);
                    mv.visitInsn(INT.toShortOpcode);
                } else if (toShortOpcode != NOP) {
                    mv.visitInsn(toShortOpcode);
                }
                break;
            case DOUBLE:
                if (toDoubleOpcode != NOP) {
                    mv.visitInsn(toDoubleOpcode);
                }
                break;
        }
    }

    public void unbox(final MethodVisitor mv) {
        final String methodName = String.format("%sValue", name().toLowerCase());
        mv.visitMethodInsn(INVOKEVIRTUAL, boxed.getInternalName(), methodName, Type.getMethodType(primitive).getDescriptor(), false);
    }

    public void box(final MethodVisitor mv) {
        mv.visitMethodInsn(INVOKESTATIC, boxed.getInternalName(), "valueOf", Type.getMethodType(boxed, primitive).getDescriptor(), false);
    }

    public void pop(final MethodVisitor mv) {
        mv.visitInsn(popOpcode);
    }

    public void dup(final MethodVisitor mv) {
        mv.visitInsn(dupOpcode);
    }

    public void ret(final MethodVisitor mv) {
        mv.visitInsn(retOpcode);
    }

    public void load(final int idx, final MethodVisitor mv) {
        mv.visitVarInsn(loadOpcode, idx);
    }

    public void store(final int idx, final MethodVisitor mv) {
        mv.visitVarInsn(storeOpcode, idx);
    }
}
