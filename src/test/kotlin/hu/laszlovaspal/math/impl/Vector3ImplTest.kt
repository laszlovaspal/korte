package hu.laszlovaspal.math.impl

import hu.laszlovaspal.math.Vector3Impl
import org.junit.Assert.assertEquals
import org.junit.Test

class Vector3ImplTest {

    @Test
    fun addition() {
        assertEquals(
                Vector3Impl(1.0, 1.0, 1.0),
                Vector3Impl(0.0, 0.0, 0.0) + Vector3Impl(1.0, 1.0, 1.0)
        )
    }

    @Test
    fun subtraction() {
        assertEquals(
                Vector3Impl(-1.0, 1.0, 0.0),
                Vector3Impl(0.0, 1.0, 0.0) - Vector3Impl(1.0, 0.0, 0.0)
        )
    }

    @Test
    fun dotProduct1() {
        val epsilon = 0.001
        assertEquals(
                0.0,
                Vector3Impl(1.0, 0.0, 0.0) dot Vector3Impl(0.0, 1.0, 0.0),
                epsilon
        )
    }

    @Test
    fun dotProduct2() {
        val epsilon = 0.001
        assertEquals(
                3.0,
                Vector3Impl(1.0, 3.0, -5.0) dot Vector3Impl(4.0, -2.0, -1.0),
                epsilon
        )
    }

    @Test
    fun crossProduct() {
        assertEquals(
                Vector3Impl(0.0, 0.0, 1.0),
                Vector3Impl(1.0, 0.0, 0.0) cross Vector3Impl(0.0, 1.0, 0.0)
        )
    }

    @Test
    fun times() {
        assertEquals(
                Vector3Impl(2.0, 2.0, 0.0),
                Vector3Impl(1.0, 1.0, 0.0) * 2.0
        )
    }

    @Test
    fun normalize() {
        assertEquals(
                Vector3Impl(1.0, 0.0, 0.0),
                Vector3Impl(12.0, 0.0, 0.0).normalize()
        )
    }

}
