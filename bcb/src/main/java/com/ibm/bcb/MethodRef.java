package com.ibm.bcb;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.objectweb.asm.Type;

import java.lang.reflect.Modifier;

@EqualsAndHashCode
@Data(staticConstructor = "of")
public class MethodRef {
    private final String declaringClass;
    private final String name;
    private final Type type;
    private final int modifiers;
    private final boolean isIntrinsic;
    private final boolean isInline;

    public boolean isStatic() {
        return Modifier.isStatic(modifiers);
    }
}
