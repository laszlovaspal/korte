package hu.laszlovaspal.color

import org.junit.Assert.*
import org.junit.Test

class ColorTest {

    @Test
    fun instantiation() {
        val color1 = Color(0.2, 0.4, 0.8, 0.4)
        val color2 = Color(color1.argb)
        val epsilon = 0.0001
        assertEquals(color1.red, color2.red, epsilon)
        assertEquals(color1.green, color2.green, epsilon)
        assertEquals(color1.blue, color2.blue, epsilon)
        assertEquals(color1.alpha, color2.alpha, epsilon)
        assertEquals(color1.argb, color2.argb)
    }

}