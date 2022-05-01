package com.ibm.bcb;

import lombok.SneakyThrows;
import org.junit.Assert;
import org.junit.Test;
import org.objectweb.asm.Type;

import java.lang.invoke.MethodHandle;

import static com.ibm.bcb.BCB.*;

public class ControlFlowTest {

    @Test
    @SneakyThrows
    public void testForI() {
        final BCBMethod method = BCBMethod.builder()
                .name("Test")
                .declaringClass("TestClass")
                .returnType(Type.INT_TYPE)
                .body(
                        store("x", constant(0)),                                   // x = 0
                        fori(
                                store("i", constant(0)),                           // i = 0;
                                load("i").lt(constant(100)),                       // i < 100;
                                store("i", load("i").add(constant(1))),        // i = i + 1

                                block(
                                        store("x", load("x").add(constant(2))) // x += 2
                                )),
                        ret(load("x"))                                             // return x
                ).build();

        method.save();

        final MethodHandle handle = method.toMethodHandle();
        Assert.assertEquals(200, handle.invoke());
    }

    @Test
    @SneakyThrows
    public void testWhile() {
        final BCBMethod method = BCBMethod.builder()
                .name("Test")
                .declaringClass("TestClass")
                .returnType(Type.INT_TYPE)
                .body(
                        store("x", constant(0)),                                   // x = 0
                        whileLoop(
                                load("x").lt(constant(100)),                       // x < 100;
                                block(
                                        store("x", load("x").add(constant(1))) // x += 1
                                )
                        ),
                        ret(load("x"))                                             // return x
                ).build();

        final MethodHandle handle = method.toMethodHandle();
        Assert.assertEquals(100, handle.invoke());
    }

}
