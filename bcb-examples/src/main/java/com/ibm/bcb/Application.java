package com.ibm.bcb;

import org.objectweb.asm.Type;

import java.lang.invoke.MethodHandle;

import static com.ibm.bcb.BCB.*;

public class Application {

    public static void main(String[] args) throws Throwable {
        BCBMethod method = BCBMethod.builder()
                .name("Test")
                .declaringClass("TestClass")
                .returnType(Type.FLOAT_TYPE)
                .arg("a", Type.FLOAT_TYPE)
                .arg("b", Type.FLOAT_TYPE)
                .arg("c", Type.FLOAT_TYPE)
                .body(
                        ret(
                                add(
                                        mul(
                                                load("a"),
                                                load("b")
                                        ),
                                        load("c")
                                )
                        )
                ).build();

        final MethodHandle fmaMethodHandle = method.toMethodHandle();

        for (int i = 0; i < 10; i++) {
            System.out.println(fmaMethodHandle.invoke((float) i, (float) i, (float) i));
        }
    }
}
