package com.ibm.bcb.tree;

import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Type;

public interface Statement {

    Type evaluate(MethodContext ctx, MethodVisitor mv);

}
