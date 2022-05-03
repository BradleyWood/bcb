package com.ibm.bcb;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.objectweb.asm.Type;

@EqualsAndHashCode
public @Data class Var {
    private final String name;
    private final Type type;
    private final int scope;
    private final int index;
    private final int modifiers;
}
