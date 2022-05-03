package com.ibm.bcb;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.objectweb.asm.Type;

@EqualsAndHashCode
public @Data class Field {
    private final String declaringClass;
    private final String name;
    private final Type type;
    private final int modifiers;
}
