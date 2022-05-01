package com.ibm.bcb;

import lombok.SneakyThrows;
import org.junit.Test;
import org.objectweb.asm.Type;

import java.lang.invoke.MethodHandle;

import static com.ibm.bcb.BCB.load;
import static com.ibm.bcb.BCB.ret;
import static org.junit.Assert.assertEquals;

public class TypeCastTest {

    @Test
    @SneakyThrows
    public void testDoubleToBoxedByte() {
        BCBMethod method = BCBMethod.builder()
                .name("Test")
                .declaringClass("TestClass")
                .returnType(Type.getType(Byte.class))
                .arg("a", Type.DOUBLE_TYPE)
                .body(
                        ret(
                                load("a").cast(Type.getType(Byte.class))
                        )
                ).build();

        final MethodHandle handle = method.toMethodHandle();

        assertEquals((byte) 5, (byte) handle.invoke(5.0));
        assertEquals((byte) 10, (byte) handle.invoke(10.0));
    }

    @Test
    @SneakyThrows
    public void testI2F() {
        BCBMethod method = BCBMethod.builder()
                .name("Test")
                .declaringClass("TestClass")
                .returnType(Type.FLOAT_TYPE)
                .arg("a", Type.INT_TYPE)
                .body(
                        ret(
                                load("a").toFloat()
                        )
                ).build();

        final MethodHandle handle = method.toMethodHandle();

        assertEquals(5f, (float) handle.invoke(5), 0.0001);
    }

    @Test
    @SneakyThrows
    public void testI2D() {
        BCBMethod method = BCBMethod.builder()
                .name("Test")
                .declaringClass("TestClass")
                .returnType(Type.DOUBLE_TYPE)
                .arg("a", Type.INT_TYPE)
                .body(
                        ret(
                                load("a").toDouble()
                        )
                ).build();

        final MethodHandle handle = method.toMethodHandle();

        assertEquals(5.0, (double) handle.invoke(5), 0.0001);
    }

    @Test
    @SneakyThrows
    public void testI2L() {
        BCBMethod method = BCBMethod.builder()
                .name("Test")
                .declaringClass("TestClass")
                .returnType(Type.LONG_TYPE)
                .arg("a", Type.INT_TYPE)
                .body(
                        ret(
                                load("a").toLong()
                        )
                ).build();

        final MethodHandle handle = method.toMethodHandle();

        assertEquals(5L, (long) handle.invoke(5));
    }

    @Test
    @SneakyThrows
    public void testF2I() {
        BCBMethod method = BCBMethod.builder()
                .name("Test")
                .declaringClass("TestClass")
                .returnType(Type.INT_TYPE)
                .arg("a", Type.FLOAT_TYPE)
                .body(
                        ret(
                                load("a").toInt()
                        )
                ).build();

        final MethodHandle handle = method.toMethodHandle();

        assertEquals(5, (int) handle.invoke(5f));
    }

    @Test
    @SneakyThrows
    public void testF2D() {
        BCBMethod method = BCBMethod.builder()
                .name("Test")
                .declaringClass("TestClass")
                .returnType(Type.DOUBLE_TYPE)
                .arg("a", Type.FLOAT_TYPE)
                .body(
                        ret(
                                load("a").toDouble()
                        )
                ).build();

        final MethodHandle handle = method.toMethodHandle();

        assertEquals(5.0, (double) handle.invoke(5f), 0.0001);
    }

    @Test
    @SneakyThrows
    public void testD2F() {
        BCBMethod method = BCBMethod.builder()
                .name("Test")
                .declaringClass("TestClass")
                .returnType(Type.FLOAT_TYPE)
                .arg("a", Type.DOUBLE_TYPE)
                .body(
                        ret(
                                load("a").toFloat()
                        )
                ).build();

        final MethodHandle handle = method.toMethodHandle();

        assertEquals(5f, (float) handle.invoke(5.0), 0.0001);
    }

    @Test
    @SneakyThrows
    public void testL2I() {
        BCBMethod method = BCBMethod.builder()
                .name("Test")
                .declaringClass("TestClass")
                .returnType(Type.INT_TYPE)
                .arg("a", Type.LONG_TYPE)
                .body(
                        ret(
                                load("a").toInt()
                        )
                ).build();

        final MethodHandle handle = method.toMethodHandle();

        assertEquals(5, (int) handle.invoke(5L));
    }

    @Test
    @SneakyThrows
    public void testL2F() {
        BCBMethod method = BCBMethod.builder()
                .name("Test")
                .declaringClass("TestClass")
                .returnType(Type.FLOAT_TYPE)
                .arg("a", Type.LONG_TYPE)
                .body(
                        ret(
                                load("a").toFloat()
                        )
                ).build();

        final MethodHandle handle = method.toMethodHandle();

        assertEquals(5f, (float) handle.invoke(5L), 0.0001);
    }

    @Test
    @SneakyThrows
    public void testL2D() {
        BCBMethod method = BCBMethod.builder()
                .name("Test")
                .declaringClass("TestClass")
                .returnType(Type.DOUBLE_TYPE)
                .arg("a", Type.LONG_TYPE)
                .body(
                        ret(
                                load("a").toDouble()
                        )
                ).build();

        final MethodHandle handle = method.toMethodHandle();

        assertEquals(5.0, (double) handle.invoke(5L), 0.0001);
    }

    @Test
    @SneakyThrows
    public void testI2Object() {
        BCBMethod method = BCBMethod.builder()
                .name("Test")
                .declaringClass("TestClass")
                .returnType(Type.getType(Object.class))
                .arg("a", Type.INT_TYPE)
                .body(
                        ret(
                                load("a").cast(Type.getType(Object.class))
                        )
                ).build();

        final MethodHandle handle = method.toMethodHandle();

        assertEquals(5, handle.invoke(5));
    }

    @Test
    @SneakyThrows
    public void testD2Object() {
        BCBMethod method = BCBMethod.builder()
                .name("Test")
                .declaringClass("TestClass")
                .returnType(Type.getType(Object.class))
                .arg("a", Type.DOUBLE_TYPE)
                .body(
                        ret(
                                load("a").cast(Type.getType(Object.class))
                        )
                ).build();

        final MethodHandle handle = method.toMethodHandle();

        assertEquals(5.0, handle.invoke(5.0));
    }

    @Test
    @SneakyThrows
    public void testStr2Object() {
        BCBMethod method = BCBMethod.builder()
                .name("Test")
                .declaringClass("TestClass")
                .returnType(Type.getType(Object.class))
                .arg("a", Type.getType(String.class))
                .body(
                        ret(
                                load("a").cast(Type.getType(Object.class))
                        )
                ).build();

        final MethodHandle handle = method.toMethodHandle();

        assertEquals("Hello, World!", handle.invoke("Hello, World!"));
    }

}
