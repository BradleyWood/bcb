package com.ibm.bcb.tree;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Type;

import java.util.Arrays;
import java.util.List;

@EqualsAndHashCode
@Data(staticConstructor = "of")
public class Block implements Statement {

    private final List<Statement> statements;

    @Override
    public Type evaluate(final MethodContext ctx, final MethodVisitor mv) {
        Type type;

        ctx.beginScope();

        for (final Statement statement : statements) {
            type = statement.evaluate(ctx, mv);

            if (!type.equals(Type.VOID_TYPE)) {
                TypeOperations.pop(type, mv);
            }
        }

        ctx.endScope();

        return Type.VOID_TYPE;
    }

    public static Block of(final Statement... statements) {
        return of(Arrays.asList(statements));
    }
}
