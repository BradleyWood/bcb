package com.ibm.bcb;

import lombok.SneakyThrows;
import org.junit.Test;
import org.objectweb.asm.Type;

import java.io.PrintStream;
import java.lang.invoke.MethodHandle;

import static com.ibm.bcb.BCB.load;
import static com.ibm.bcb.BCB.ret;
import static org.junit.Assert.assertEquals;

public class LoadTest {

    @Test
    @SneakyThrows
    public void testD2Object() {
        BCBMethod method = BCBMethod.builder()
                .name("Test")
                .declaringClass("TestClass")
                .returnType(Type.getType(PrintStream.class))
                .body(
                        ret(
                                load(Type.getType(System.class), "out")
                        )
                ).build();

        final MethodHandle handle = method.toMethodHandle();

        assertEquals(System.out, handle.invoke());
    }

}
