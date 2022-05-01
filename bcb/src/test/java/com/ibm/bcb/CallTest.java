package com.ibm.bcb;

import lombok.SneakyThrows;
import org.junit.Assert;
import org.junit.Test;
import org.objectweb.asm.Type;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.lang.invoke.MethodHandle;

import static com.ibm.bcb.BCB.*;

public class CallTest {

    public static int getInt() {
        return 100;
    }

    @Test
    @SneakyThrows
    public void testInvokeStatic() {
        final BCBMethod method = BCBMethod.builder()
                .name("Test")
                .declaringClass("TestClass")
                .returnType(Type.INT_TYPE)
                .body(
                        ret(call(Type.getType(CallTest.class), "getInt"))
                ).build();

        final MethodHandle handle = method.toMethodHandle();
        Assert.assertEquals(100, handle.invoke());
    }

    @Test
    @SneakyThrows
    public void testInvokeVirtual() {
        final ByteArrayOutputStream baos = new ByteArrayOutputStream();
        final PrintStream ps = new PrintStream(baos);

        final BCBMethod method = BCBMethod.builder()
                .name("Test")
                .declaringClass("TestClass")
                .arg("out", Type.getType(PrintStream.class))
                .body(
                        load("out").call("println", constant(100)),
                        ret()
                ).build();

        final MethodHandle handle = method.toMethodHandle();
        handle.invoke(ps);
        Assert.assertEquals("100" + System.lineSeparator(), baos.toString());
    }
}
