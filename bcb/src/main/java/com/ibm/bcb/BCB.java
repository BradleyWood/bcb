package com.ibm.bcb;

import com.ibm.bcb.tree.*;
import org.objectweb.asm.Type;

import java.lang.reflect.Method;
import java.util.Arrays;

public class BCB {

    private static int anonymousClazzCounter = 0;

    public static BCBMethod anonymousMethod(final Statement... body) {
        return anonymousMethod(Type.VOID_TYPE);
    }

    public static BCBMethod anonymousMethod(final Type retType, final Statement... body) {
        final String clazzName = String.format("AnonymousClazz$%d", anonymousClazzCounter++);

        return BCBMethod.builder()
                .returnType(retType)
                .declaringClass(clazzName)
                .body(Block.of(body))
                .name("anonymous$0")
                .build();
    }

    public static Statement ifStmt(final Expression condition, final Statement then) {
        return ifStmt(condition, then, null);
    }

    public static Statement ifStmt(final Expression condition, final Statement then, final Statement _else) {
        return IfStatement.of(condition, then, _else);
    }

    public static Statement ret() {
        return ReturnStatement.of(null);
    }

    public static Statement ret(Expression retValue) {
        return ReturnStatement.of(retValue);
    }

    public static Block block(Statement... statements) {
        return Block.of(Arrays.asList(statements));
    }

    public static Expression constant(int constant) {
        return ConstantExpression.of(constant);
    }

    public static Expression constant(float constant) {
        return ConstantExpression.of(constant);
    }

    public static Expression constant(double constant) {
        return ConstantExpression.of(constant);
    }

    public static Expression constant(String constant) {
        return ConstantExpression.of(constant);
    }

    public static Expression constant(Class<?> type) {
        return ConstantExpression.of(type);
    }

    public static Expression constantType(Type type) {
        return ConstantExpression.of(type);
    }

    public static Expression load(int var) {
        throw new UnsupportedOperationException();
    }

    public static Expression load(String var) {
        return LoadExpression.of(var);
    }

    public static Expression load(String clazz, String var) {
        throw new UnsupportedOperationException();
    }

    public static Expression load(Expression instance, String var) {
        throw new UnsupportedOperationException();
    }

    public static Expression store(String var, Expression value) {
        return StoreExpression.of(var, value);
    }

    public static Expression store(String clazz, String name, Expression value) {
        throw new UnsupportedOperationException();
    }

    public static Expression store(Expression instance, String name, Expression value) {
        throw new UnsupportedOperationException();
    }

    public static Expression store(int var, Expression value) {
        throw new UnsupportedOperationException();
    }

    public static Expression inc(String var) {
        throw new UnsupportedOperationException();
    }

    public static Expression inc(int var) {
        throw new UnsupportedOperationException();
    }

    public static Expression neg(Expression expression) {
        throw new UnsupportedOperationException();
    }

    public static Expression sqrt(Expression expression) {
        throw new UnsupportedOperationException();
    }

    public static Expression not(Expression expression) {
        throw new UnsupportedOperationException();
    }

    public static Expression toInt(Expression expression) {
        throw new UnsupportedOperationException();
    }

    public static Expression toLong(Expression expression) {
        throw new UnsupportedOperationException();
    }

    public static Expression toFloat(Expression expression) {
        throw new UnsupportedOperationException();
    }

    public static Expression toDouble(Expression expression) {
        throw new UnsupportedOperationException();
    }

    public static Expression toShort(Expression expression) {
        throw new UnsupportedOperationException();
    }

    public static Expression toByte(Expression expression) {
        throw new UnsupportedOperationException();
    }

    public static Expression cast(Expression expression, Type type) {
        throw new UnsupportedOperationException();
    }

    public static Expression unaryOp(String opcode, Expression expression) {
        throw new UnsupportedOperationException();
    }

    public static Expression toString(Expression expression) {
        throw new UnsupportedOperationException();
    }

    public static Expression print(Expression expression) {
        throw new UnsupportedOperationException();
    }

    public static Expression println(Expression expression) {
        throw new UnsupportedOperationException();
    }

    public static Expression rem(Expression lhs, Expression rhs) {
        throw new UnsupportedOperationException();
    }

    public static Expression and(Expression lhs, Expression rhs) {
        throw new UnsupportedOperationException();
    }

    public static Expression or(Expression lhs, Expression rhs) {
        throw new UnsupportedOperationException();
    }

    public static Expression xor(Expression lhs, Expression rhs) {
        throw new UnsupportedOperationException();
    }

    public static Expression call(Method method, Expression... arguments) {
        throw new UnsupportedOperationException();
    }

    public static Expression call(String intrinsic, Expression... arguments) {
        throw new UnsupportedOperationException();
    }

    public static Expression call(String clazz, String method, Expression... arguments) {
        throw new UnsupportedOperationException();
    }

    public static Expression call(String clazz, String method, String desc, Expression... arguments) {
        throw new UnsupportedOperationException();
    }

    public static Expression call(int opcode, String clazz, String method, String desc, Expression... arguments) {
        throw new UnsupportedOperationException();
    }

    public static Expression add(Expression lhs, Expression rhs) {
        return bop("+", lhs, rhs);
    }

    public static Expression sub(Expression lhs, Expression rhs) {
        return bop("-", lhs, rhs);
    }

    public static Expression mul(Expression lhs, Expression rhs) {
        return bop("*", lhs, rhs);
    }

    public static Expression div(Expression lhs, Expression rhs) {
        return bop("/", lhs, rhs);
    }

    public static Expression bop(String opcode, Expression lhs, Expression rhs) {
        return BinaryExpression.of(lhs, rhs, opcode);
    }
}
