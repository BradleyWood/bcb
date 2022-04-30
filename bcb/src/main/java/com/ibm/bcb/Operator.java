package com.ibm.bcb;

import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Type;

import static org.objectweb.asm.Opcodes.*;

public enum Operator {

    ADD1("+",  IADD, Type.INT_TYPE, Type.INT_TYPE, Type.INT_TYPE),
    ADD2("+",  IADD, Type.INT_TYPE, Type.INT_TYPE, Type.SHORT_TYPE),
    ADD3("+",  IADD, Type.INT_TYPE, Type.INT_TYPE, Type.BYTE_TYPE),
    ADD4("+",  FADD, Type.FLOAT_TYPE, Type.FLOAT_TYPE, Type.FLOAT_TYPE),
    ADD5("+",  FADD, Type.FLOAT_TYPE, Type.FLOAT_TYPE, Type.INT_TYPE),
    ADD6("+",  FADD, Type.FLOAT_TYPE, Type.FLOAT_TYPE, Type.SHORT_TYPE),
    ADD7("+",  FADD, Type.FLOAT_TYPE, Type.FLOAT_TYPE, Type.BYTE_TYPE),
    ADD8("+",  DADD, Type.DOUBLE_TYPE, Type.DOUBLE_TYPE, Type.DOUBLE_TYPE),
    ADD9("+",  DADD, Type.DOUBLE_TYPE, Type.DOUBLE_TYPE, Type.FLOAT_TYPE),
    ADD10("+", DADD, Type.DOUBLE_TYPE, Type.DOUBLE_TYPE, Type.INT_TYPE),
    ADD11("+", DADD, Type.DOUBLE_TYPE, Type.DOUBLE_TYPE, Type.SHORT_TYPE),
    ADD12("+", DADD, Type.DOUBLE_TYPE, Type.DOUBLE_TYPE, Type.BYTE_TYPE),

    SUB1("-",  ISUB, Type.INT_TYPE, Type.INT_TYPE, Type.INT_TYPE),
    SUB2("-",  ISUB, Type.INT_TYPE, Type.INT_TYPE, Type.SHORT_TYPE),
    SUB3("-",  ISUB, Type.INT_TYPE, Type.INT_TYPE, Type.BYTE_TYPE),
    SUB4("-",  FSUB, Type.FLOAT_TYPE, Type.FLOAT_TYPE, Type.FLOAT_TYPE),
    SUB5("-",  FSUB, Type.FLOAT_TYPE, Type.FLOAT_TYPE, Type.INT_TYPE),
    SUB6("-",  FSUB, Type.FLOAT_TYPE, Type.FLOAT_TYPE, Type.SHORT_TYPE),
    SUB7("-",  FSUB, Type.FLOAT_TYPE, Type.FLOAT_TYPE, Type.BYTE_TYPE),
    SUB8("-",  DSUB, Type.DOUBLE_TYPE, Type.DOUBLE_TYPE, Type.DOUBLE_TYPE),
    SUB9("-",  DSUB, Type.DOUBLE_TYPE, Type.DOUBLE_TYPE, Type.FLOAT_TYPE),
    SUB10("-", DSUB, Type.DOUBLE_TYPE, Type.DOUBLE_TYPE, Type.INT_TYPE),
    SUB11("-", DSUB, Type.DOUBLE_TYPE, Type.DOUBLE_TYPE, Type.SHORT_TYPE),
    SUB12("-", DSUB, Type.DOUBLE_TYPE, Type.DOUBLE_TYPE, Type.BYTE_TYPE),

    MUL1("*",  IMUL, Type.INT_TYPE, Type.INT_TYPE, Type.INT_TYPE),
    MUL2("*",  IMUL, Type.INT_TYPE, Type.INT_TYPE, Type.SHORT_TYPE),
    MUL3("*",  IMUL, Type.INT_TYPE, Type.INT_TYPE, Type.BYTE_TYPE),
    MUL4("*",  FMUL, Type.FLOAT_TYPE, Type.FLOAT_TYPE, Type.FLOAT_TYPE),
    MUL5("*",  FMUL, Type.FLOAT_TYPE, Type.FLOAT_TYPE, Type.INT_TYPE),
    MUL6("*",  FMUL, Type.FLOAT_TYPE, Type.FLOAT_TYPE, Type.SHORT_TYPE),
    MUL7("*",  FMUL, Type.FLOAT_TYPE, Type.FLOAT_TYPE, Type.BYTE_TYPE),
    MUL8("*",  DMUL, Type.DOUBLE_TYPE, Type.DOUBLE_TYPE, Type.DOUBLE_TYPE),
    MUL9("*",  DMUL, Type.DOUBLE_TYPE, Type.DOUBLE_TYPE, Type.FLOAT_TYPE),
    MUL10("*", DMUL, Type.DOUBLE_TYPE, Type.DOUBLE_TYPE, Type.INT_TYPE),
    MUL11("*", DMUL, Type.DOUBLE_TYPE, Type.DOUBLE_TYPE, Type.SHORT_TYPE),
    MUL12("*", DMUL, Type.DOUBLE_TYPE, Type.DOUBLE_TYPE, Type.BYTE_TYPE),

    DIV1("/",  IDIV, Type.INT_TYPE, Type.INT_TYPE, Type.INT_TYPE),
    DIV2("/",  IDIV, Type.INT_TYPE, Type.INT_TYPE, Type.SHORT_TYPE),
    DIV3("/",  IDIV, Type.INT_TYPE, Type.INT_TYPE, Type.BYTE_TYPE),
    DIV4("/",  FDIV, Type.FLOAT_TYPE, Type.FLOAT_TYPE, Type.FLOAT_TYPE),
    DIV5("/",  FDIV, Type.FLOAT_TYPE, Type.FLOAT_TYPE, Type.INT_TYPE),
    DIV6("/",  FDIV, Type.FLOAT_TYPE, Type.FLOAT_TYPE, Type.SHORT_TYPE),
    DIV7("/",  FDIV, Type.FLOAT_TYPE, Type.FLOAT_TYPE, Type.BYTE_TYPE),
    DIV8("/",  DDIV, Type.DOUBLE_TYPE, Type.DOUBLE_TYPE, Type.DOUBLE_TYPE),
    DIV9("/",  DDIV, Type.DOUBLE_TYPE, Type.DOUBLE_TYPE, Type.FLOAT_TYPE),
    DIV10("/", DDIV, Type.DOUBLE_TYPE, Type.DOUBLE_TYPE, Type.INT_TYPE),
    DIV11("/", DDIV, Type.DOUBLE_TYPE, Type.DOUBLE_TYPE, Type.SHORT_TYPE),
    DIV12("/", DDIV, Type.DOUBLE_TYPE, Type.DOUBLE_TYPE, Type.BYTE_TYPE);

    private final String operator;
    private final int opcode;
    private final Type resultType;
    private final Type lhs;
    private final Type rhs;

    Operator(String operator, int opcode, Type resultType, Type lhs, Type rhs) {
        this.operator = operator;
        this.opcode = opcode;
        this.resultType = resultType;
        this.lhs = lhs;
        this.rhs = rhs;
    }

    public void evaluate(final MethodVisitor mv) {
        mv.visitInsn(opcode);
    }

    public Type getResultType() {
        return resultType;
    }

    public static Operator findOperator(final String op, final Type lhs, final Type rhs) {
        for (final Operator value : values()) {
            if (value.operator.equals(op)) {
                if (value.lhs.equals(lhs) && value.rhs.equals(rhs)) {
                    return value;
                } else if (value.lhs.equals(rhs) && value.rhs.equals(lhs)) {
                    return value;
                }
            }
        }

        return null;
    }
}
