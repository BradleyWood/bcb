package com.ibm.bcb.tree;


import com.ibm.bcb.Primitive;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Type;

import static org.objectweb.asm.Opcodes.*;

public class TypeOperations {

    public static void pop(final Type type, final MethodVisitor mv) {
        if (type.equals(Type.VOID_TYPE)) {
            throw new IllegalArgumentException("Cannot pop void type");
        }

        Primitive primitive = Primitive.getPrimitive(type);

        if (primitive != null) {
            primitive.pop(mv);
        } else {
            mv.visitInsn(POP);
        }
    }

    public static void dup(final Type type, final MethodVisitor mv) {
        if (type.equals(Type.VOID_TYPE)) {
            throw new IllegalArgumentException("Cannot pop void type");
        }

        Primitive primitive = Primitive.getPrimitive(type);

        if (primitive != null) {
            primitive.pop(mv);
        } else {
            mv.visitInsn(DUP);
        }
    }

    public static void load(final Type type, final int idx, final MethodVisitor mv) {
        Primitive primitive = Primitive.getPrimitive(type);

        if (primitive != null) {
            primitive.load(idx, mv);
        } else {
            mv.visitVarInsn(ALOAD, idx);
        }
    }

    public static void store(final Type type, final int idx, final MethodVisitor mv) {
        Primitive primitive = Primitive.getPrimitive(type);

        if (primitive != null) {
            primitive.store(idx, mv);
        } else {
            mv.visitVarInsn(ASTORE, idx);
        }
    }

    public static void ret(final Type type, final MethodVisitor mv) {
        Primitive primitive = Primitive.getPrimitive(type);

        if (primitive != null) {
            primitive.ret(mv);
        } else if (type.equals(Type.VOID_TYPE)) {
            mv.visitInsn(RETURN);
        } else {
            mv.visitInsn(ARETURN);
        }
    }
}
