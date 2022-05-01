package com.ibm.bcb;

import org.objectweb.asm.Type;

import java.util.Arrays;
import java.util.List;

import static com.ibm.bcb.BCB.*;

public class Intrinsics {

    public static final BCBMethod sqrt = BCBMethod.builder()
            .name("sqrt")
            .inline(true)
            .returnType(Type.DOUBLE_TYPE)
            .arg("x", Type.DOUBLE_TYPE)
            .body(
                    ret(
                            call(Type.getType(Math.class), "sqrt", load("x"))
                    )
            ).build();

    public static final List<BCBMethod> GLOBAL_INTRINSICS = Arrays.asList(
            sqrt
    );
}
