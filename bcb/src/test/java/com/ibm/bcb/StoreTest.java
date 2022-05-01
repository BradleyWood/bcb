package com.ibm.bcb;

import lombok.SneakyThrows;
import org.junit.Test;
import org.objectweb.asm.Type;

import java.lang.invoke.MethodHandle;

import static com.ibm.bcb.BCB.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class StoreTest {

    public static String storeStr;

    @Test
    @SneakyThrows
    public void testPutStatic() {
        BCBMethod method = BCBMethod.builder()
                .name("Test")
                .declaringClass("TestClass")
                .body(
                        store(Type.getType(StoreTest.class), "storeStr", constant("Hello, World!")),
                        ret()
                ).build();

        storeStr = null;
        final MethodHandle handle = method.toMethodHandle();
        handle.invoke();

        assertEquals("Hello, World!", storeStr);
    }

    @Test
    @SneakyThrows
    public void testPutField() {
        final PutFieldTestClass instance = new PutFieldTestClass();
        final Type iType = Type.getType(PutFieldTestClass.class);

        BCBMethod method = BCBMethod.builder()
                .name("Test")
                .declaringClass("TestClass")
                .arg("instance", iType)
                .body(
                        store(load("instance"), "field", constant("Hello, World!")),
                        ret()
                ).build();

        assertNull(instance.field);
        final MethodHandle handle = method.toMethodHandle();
        handle.invoke(instance);

        assertEquals("Hello, World!", instance.field);
    }

    public static class PutFieldTestClass {
        public String field;
    }
}
