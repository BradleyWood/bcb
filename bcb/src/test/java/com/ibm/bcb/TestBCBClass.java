package com.ibm.bcb;

import lombok.SneakyThrows;
import org.junit.Assert;
import org.junit.Test;
import org.objectweb.asm.Type;

import static com.ibm.bcb.BCB.constant;
import static com.ibm.bcb.BCB.ret;

public class TestBCBClass {

    @Test
    @SneakyThrows
    public void testInvokeMR() {
        final Class<?> clazz = BCBClass.builder()
                .method(BCBMethod.builder()
                        .name("test")
                        .returnType(Type.INT_TYPE)
                        .body(
                                ret(constant(100))
                        ).build())
                .method(BCBMethod.builder()
                        .name("test2")
                        .returnType(Type.INT_TYPE)
                        .body(
                                ret(constant(200))
                        ).build())
                .build()
                .loadClass();

        Assert.assertEquals(100, (int) clazz.getMethod("test").invoke(null));
        Assert.assertEquals(200, (int) clazz.getMethod("test2").invoke(null));
    }
}
