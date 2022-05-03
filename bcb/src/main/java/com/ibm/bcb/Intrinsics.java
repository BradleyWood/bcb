package com.ibm.bcb;

import org.objectweb.asm.Type;

import java.util.ArrayList;
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

    public static final BCBMethod aPrintln = BCBMethod.builder()
            .name("println")
            .inline(true)
            .arg("x", Type.getType(Object.class))
            .body(
                    load(System.class, "out").call("println", load("x")),
                    ret()
            ).build();

    public static final BCBMethod iPrintln = BCBMethod.builder()
            .name("println")
            .inline(true)
            .arg("x", Type.getType(int.class))
            .body(
                    load(System.class, "out").call("println", load("x")),
                    ret()
            ).build();

    public static final BCBMethod dPrintln = BCBMethod.builder()
            .name("println")
            .inline(true)
            .arg("x", Type.getType(double.class))
            .body(
                    load(System.class, "out").call("println", load("x")),
                    ret()
            ).build();

    public static final BCBMethod fPrintln = BCBMethod.builder()
            .name("println")
            .inline(true)
            .arg("x", Type.getType(float.class))
            .body(
                    load(System.class, "out").call("println", load("x")),
                    ret()
            ).build();

    public static final BCBMethod aPrint = BCBMethod.builder()
            .name("print")
            .inline(true)
            .arg("x", Type.getType(Object.class))
            .body(
                    load(System.class, "out").call("print", load("x")),
                    ret()
            ).build();

    public static final BCBMethod iPrint = BCBMethod.builder()
            .name("print")
            .inline(true)
            .arg("x", Type.getType(int.class))
            .body(
                    load(System.class, "out").call("print", load("x")),
                    ret()
            ).build();

    public static final BCBMethod dPrint = BCBMethod.builder()
            .name("print")
            .inline(true)
            .arg("x", Type.getType(double.class))
            .body(
                    load(System.class, "out").call("print", load("x")),
                    ret()
            ).build();

    public static final BCBMethod fPrint = BCBMethod.builder()
            .name("print")
            .inline(true)
            .arg("x", Type.getType(float.class))
            .body(
                    load(System.class, "out").call("print", load("x")),
                    ret()
            ).build();

    public static final List<BCBMethod> GLOBAL_INTRINSICS = Arrays.asList(
            sqrt,
            aPrintln,
            iPrintln,
            dPrintln,
            fPrintln,
            aPrint,
            iPrint,
            dPrint,
            fPrint
    );

    public static final List<MethodRef> BUILTINS = new ArrayList<>();

}
