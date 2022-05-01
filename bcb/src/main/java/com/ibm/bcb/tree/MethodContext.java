package com.ibm.bcb.tree;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.objectweb.asm.Type;

import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;

import static org.objectweb.asm.Opcodes.ACC_PUBLIC;
import static org.objectweb.asm.Opcodes.ACC_STATIC;

@EqualsAndHashCode
public @Data class MethodContext {

    private final Stack<ArrayList<Var>> scope = new Stack<>();
    private final String name;
    private final Type declaringClass;
    private final Type methodType;
    private final List<String> argNames;
    private int modifiers;

    public MethodContext() {
        this("<AnonymousMethod>");
    }

    public MethodContext(final String name) {
        this(null, name, Type.getMethodType(Type.VOID_TYPE));
    }

    public MethodContext(final Type declaringClass, final String name, final Type type, final String... argNames) {
        this(declaringClass, name, type, Arrays.asList(argNames));
    }

    public MethodContext(final Type declaringClass, final String name, final Type type, final List<String> argNames) {
        this(declaringClass, name, ACC_PUBLIC + ACC_STATIC, type, argNames);
    }

    public MethodContext(final Type declaringClass, final String name, int modifiers, final Type type, final String... argNames) {
        this(declaringClass, name, modifiers, type, Arrays.asList(argNames));
    }

    public MethodContext(final Type declaringClass, final String name, int modifiers, final Type type, final List<String> argNames) {
        this.declaringClass = declaringClass;
        this.name = name;
        this.modifiers = modifiers;
        this.methodType = type;
        this.argNames = argNames;

        final Type[] argumentTypes = type.getArgumentTypes();

        if (argumentTypes.length != argNames.size()) {
            throw new IllegalArgumentException("Number of argument names must match number of argument types");
        }

        beginScope();

        if (!isStatic() && declaringClass != null) {
            putVar("this", declaringClass, 0);
        }

        for (int i = 0; i < argumentTypes.length; i++) {
            final String argName = argNames.get(i);
            putVar(argName, argumentTypes[i], 0);
        }
    }

    public void applyModifier(int modifiers) {
        this.modifiers |= modifiers;
    }

    public boolean isStatic() {
        return Modifier.isStatic(modifiers);
    }

    public void beginScope() {
        scope.push(new ArrayList<>());
    }

    public void endScope() {
        scope.pop();
    }

    public Field findField(final String declaringClass, final String name) {
        try {
            final Class<?> decl = Class.forName(declaringClass.replace("/", "."));
            final java.lang.reflect.Field field = decl.getField(name);

            return new Field(declaringClass, name, Type.getType(field.getType()), field.getModifiers());
        } catch (Exception e) {
            return null;
        }
    }

    public boolean putVar(final String name, final Type type, final int modifiers) {
        return putVar(name, type, modifiers, false);
    }

    public boolean putVar(final String name, final Type type, final int modifiers, final boolean dup) {
        if (!dup && findVar(name) != null) {
            return false;
        }

        int idx = 0;
        for (final ArrayList<Var> vars : scope) {
            idx += vars.size();
        }

        final Var var = new Var(name, type, count(), idx, modifiers);

        scope.peek().add(var);

        if (type.equals(Type.LONG_TYPE) || type.equals(Type.DOUBLE_TYPE)) {
            final Var second = new Var(name + " _2", type, count(), idx + 1, modifiers);
            scope.peek().add(second);
            // long, double use 2 local indices
        }

        return true;
    }

    public Var findVar(final String name) {
        for (final ArrayList<Var> list : scope) {
            for (final Var var : list) {
                if (var.getName().equals(name)) {
                    return var;
                }
            }
        }
        return null;
    }

    public int count() {
        return scope.size();
    }

    public void clear() {
        scope.clear();
    }

    public static @Data class Var {
        private final String name;
        private final Type type;
        private final int scope;
        private final int index;
        private final int modifiers;
    }

    public static @Data class Field {
        private final String declaringClass;
        private final String name;
        private final Type type;
        private final int modifiers;
    }
}
