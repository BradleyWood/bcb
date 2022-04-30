package com.ibm.bcb;

import org.objectweb.asm.Label;
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
    DIV12("/", DDIV, Type.DOUBLE_TYPE, Type.DOUBLE_TYPE, Type.BYTE_TYPE),

    EQ1("==", IF_ICMPNE, Type.BOOLEAN_TYPE, Type.INT_TYPE, Type.INT_TYPE),
    EQ2("==", IF_ICMPNE, Type.BOOLEAN_TYPE, Type.INT_TYPE, Type.SHORT_TYPE),
    EQ3("==", IF_ICMPNE, Type.BOOLEAN_TYPE, Type.INT_TYPE, Type.BYTE_TYPE),

    EQ4("==", FCMPG, IFNE, Type.BOOLEAN_TYPE, Type.FLOAT_TYPE, Type.FLOAT_TYPE),
    EQ5("==", FCMPG, IFNE, Type.BOOLEAN_TYPE, Type.FLOAT_TYPE, Type.INT_TYPE),
    EQ6("==", FCMPG, IFNE, Type.BOOLEAN_TYPE, Type.FLOAT_TYPE, Type.SHORT_TYPE),
    EQ7("==", FCMPG, IFNE, Type.BOOLEAN_TYPE, Type.FLOAT_TYPE, Type.BYTE_TYPE),

    EQ8("==",  DCMPG, IFNE, Type.BOOLEAN_TYPE, Type.DOUBLE_TYPE, Type.DOUBLE_TYPE),
    EQ9("==",  DCMPG, IFNE, Type.BOOLEAN_TYPE, Type.DOUBLE_TYPE, Type.FLOAT_TYPE),
    EQ10("==", DCMPG, IFNE, Type.BOOLEAN_TYPE, Type.DOUBLE_TYPE, Type.INT_TYPE),
    EQ11("==", DCMPG, IFNE, Type.BOOLEAN_TYPE, Type.DOUBLE_TYPE, Type.SHORT_TYPE),
    EQ12("==", DCMPG, IFNE, Type.BOOLEAN_TYPE, Type.DOUBLE_TYPE, Type.BYTE_TYPE),
    EQ13("==", IF_ICMPNE, Type.BOOLEAN_TYPE, Type.BOOLEAN_TYPE, Type.BOOLEAN_TYPE),

    GT1(">", IF_ICMPLE, Type.BOOLEAN_TYPE, Type.INT_TYPE, Type.INT_TYPE),
    GT2(">", IF_ICMPLE, Type.BOOLEAN_TYPE, Type.INT_TYPE, Type.SHORT_TYPE),
    GT3(">", IF_ICMPLE, Type.BOOLEAN_TYPE, Type.INT_TYPE, Type.BYTE_TYPE),

    GT4(">", FCMPG, IFLE, Type.BOOLEAN_TYPE, Type.FLOAT_TYPE, Type.FLOAT_TYPE),
    GT5(">", FCMPG, IFLE, Type.BOOLEAN_TYPE, Type.FLOAT_TYPE, Type.INT_TYPE),
    GT6(">", FCMPG, IFLE, Type.BOOLEAN_TYPE, Type.FLOAT_TYPE, Type.SHORT_TYPE),
    GT7(">", FCMPG, IFLE, Type.BOOLEAN_TYPE, Type.FLOAT_TYPE, Type.BYTE_TYPE),

    GT8(">",  DCMPG, IFLE, Type.BOOLEAN_TYPE, Type.DOUBLE_TYPE, Type.DOUBLE_TYPE),
    GT9(">",  DCMPG, IFLE, Type.BOOLEAN_TYPE, Type.DOUBLE_TYPE, Type.FLOAT_TYPE),
    GT10(">", DCMPG, IFLE, Type.BOOLEAN_TYPE, Type.DOUBLE_TYPE, Type.INT_TYPE),
    GT11(">", DCMPG, IFLE, Type.BOOLEAN_TYPE, Type.DOUBLE_TYPE, Type.SHORT_TYPE),
    GT12(">", DCMPG, IFLE, Type.BOOLEAN_TYPE, Type.DOUBLE_TYPE, Type.BYTE_TYPE),

    GTE1(">=", IF_ICMPLT, Type.BOOLEAN_TYPE, Type.INT_TYPE, Type.INT_TYPE),
    GTE2(">=", IF_ICMPLT, Type.BOOLEAN_TYPE, Type.INT_TYPE, Type.SHORT_TYPE),
    GTE3(">=", IF_ICMPLT, Type.BOOLEAN_TYPE, Type.INT_TYPE, Type.BYTE_TYPE),

    GTE4(">=", FCMPG, IFLT, Type.BOOLEAN_TYPE, Type.FLOAT_TYPE, Type.FLOAT_TYPE),
    GTE5(">=", FCMPG, IFLT, Type.BOOLEAN_TYPE, Type.FLOAT_TYPE, Type.INT_TYPE),
    GTE6(">=", FCMPG, IFLT, Type.BOOLEAN_TYPE, Type.FLOAT_TYPE, Type.SHORT_TYPE),
    GTE7(">=", FCMPG, IFLT, Type.BOOLEAN_TYPE, Type.FLOAT_TYPE, Type.BYTE_TYPE),

    GTE8(">=",  DCMPG, IFLT, Type.BOOLEAN_TYPE, Type.DOUBLE_TYPE, Type.DOUBLE_TYPE),
    GTE9(">=",  DCMPG, IFLT, Type.BOOLEAN_TYPE, Type.DOUBLE_TYPE, Type.FLOAT_TYPE),
    GTE10(">=", DCMPG, IFLT, Type.BOOLEAN_TYPE, Type.DOUBLE_TYPE, Type.INT_TYPE),
    GTE11(">=", DCMPG, IFLT, Type.BOOLEAN_TYPE, Type.DOUBLE_TYPE, Type.SHORT_TYPE),
    GTE12(">=", DCMPG, IFLT, Type.BOOLEAN_TYPE, Type.DOUBLE_TYPE, Type.BYTE_TYPE),

    LT1("<", IF_ICMPGE, Type.BOOLEAN_TYPE, Type.INT_TYPE, Type.INT_TYPE),
    LT2("<", IF_ICMPGE, Type.BOOLEAN_TYPE, Type.INT_TYPE, Type.SHORT_TYPE),
    LT3("<", IF_ICMPGE, Type.BOOLEAN_TYPE, Type.INT_TYPE, Type.BYTE_TYPE),

    LT4("<", FCMPG, IFGE, Type.BOOLEAN_TYPE, Type.FLOAT_TYPE, Type.FLOAT_TYPE),
    LT5("<", FCMPG, IFGE, Type.BOOLEAN_TYPE, Type.FLOAT_TYPE, Type.INT_TYPE),
    LT6("<", FCMPG, IFGE, Type.BOOLEAN_TYPE, Type.FLOAT_TYPE, Type.SHORT_TYPE),
    LT7("<", FCMPG, IFGE, Type.BOOLEAN_TYPE, Type.FLOAT_TYPE, Type.BYTE_TYPE),

    LT8("<",  DCMPG, IFGE, Type.BOOLEAN_TYPE, Type.DOUBLE_TYPE, Type.DOUBLE_TYPE),
    LT9("<",  DCMPG, IFGE, Type.BOOLEAN_TYPE, Type.DOUBLE_TYPE, Type.FLOAT_TYPE),
    LT10("<", DCMPG, IFGE, Type.BOOLEAN_TYPE, Type.DOUBLE_TYPE, Type.INT_TYPE),
    LT11("<", DCMPG, IFGE, Type.BOOLEAN_TYPE, Type.DOUBLE_TYPE, Type.SHORT_TYPE),
    LT12("<", DCMPG, IFGE, Type.BOOLEAN_TYPE, Type.DOUBLE_TYPE, Type.BYTE_TYPE),

    LTE1("<=", IF_ICMPGT, Type.BOOLEAN_TYPE, Type.INT_TYPE, Type.INT_TYPE),
    LTE2("<=", IF_ICMPGT, Type.BOOLEAN_TYPE, Type.INT_TYPE, Type.SHORT_TYPE),
    LTE3("<=", IF_ICMPGT, Type.BOOLEAN_TYPE, Type.INT_TYPE, Type.BYTE_TYPE),

    LTE4("<=", FCMPG, IFGT, Type.BOOLEAN_TYPE, Type.FLOAT_TYPE, Type.FLOAT_TYPE),
    LTE5("<=", FCMPG, IFGT, Type.BOOLEAN_TYPE, Type.FLOAT_TYPE, Type.INT_TYPE),
    LTE6("<=", FCMPG, IFGT, Type.BOOLEAN_TYPE, Type.FLOAT_TYPE, Type.SHORT_TYPE),
    LTE7("<=", FCMPG, IFGT, Type.BOOLEAN_TYPE, Type.FLOAT_TYPE, Type.BYTE_TYPE),

    LTE8("<=",  DCMPG, IFGT, Type.BOOLEAN_TYPE, Type.DOUBLE_TYPE, Type.DOUBLE_TYPE),
    LTE9("<=",  DCMPG, IFGT, Type.BOOLEAN_TYPE, Type.DOUBLE_TYPE, Type.FLOAT_TYPE),
    LTE10("<=", DCMPG, IFGT, Type.BOOLEAN_TYPE, Type.DOUBLE_TYPE, Type.INT_TYPE),
    LTE11("<=", DCMPG, IFGT, Type.BOOLEAN_TYPE, Type.DOUBLE_TYPE, Type.SHORT_TYPE),
    LTE12("<=", DCMPG, IFGT, Type.BOOLEAN_TYPE, Type.DOUBLE_TYPE, Type.BYTE_TYPE);

    private final String operator;
    private final int opcode;
    private final int opcode2;
    private final Type resultType;
    private final Type lhs;
    private final Type rhs;

    Operator(String operator, int opcode, Type resultType, Type lhs, Type rhs) {
        this.operator = operator;
        this.opcode = opcode;
        this.opcode2 = NOP;
        this.resultType = resultType;
        this.lhs = lhs;
        this.rhs = rhs;
    }

    Operator(String operator, int opcode, int opcode2, Type resultType, Type lhs, Type rhs) {
        this.operator = operator;
        this.opcode = opcode;
        this.opcode2 = opcode2;
        this.resultType = resultType;
        this.lhs = lhs;
        this.rhs = rhs;
    }

    public void evaluate(final MethodVisitor mv) {
        mv.visitInsn(opcode);
    }

    public void evaluate(final Label elseLabel, final MethodVisitor mv) {
        if (opcode2 != NOP) {
            mv.visitInsn(opcode);
            mv.visitJumpInsn(opcode2, elseLabel);
        } else {
            mv.visitJumpInsn(opcode, elseLabel);
        }
    }

    public Type getResultType() {
        return resultType;
    }

    public boolean isCompareInstruction() {
        return opcode >= LCMP && opcode < GOTO;
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
