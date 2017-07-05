package hu.laszlovaspal.krtengine.renderer

import hu.laszlovaspal.color.Color
import hu.laszlovaspal.krtengine.scene.Scene
import java.io.Closeable

interface Renderer : Closeable {
    val configuration: RenderingConfiguration
    val scene: Scene
    fun renderFrame(frame: Frame)
    override fun close() {}
}

data class RenderingConfiguration(var shadowsVisible: Boolean = false,
                                  var debug: Boolean = false,
                                  var blockSize: Int = 10)

interface Frame {
    val width: Int
    val height: Int
    val pixels: IntArray
    fun getArgb(x: Int, y: Int): Int
    fun setArgb(x: Int, y: Int, argb: Int)
    fun clear()
}

class SimpleFrame(override val width: Int, override val height: Int) : Frame {
    override var pixels = IntArray(width * height)
    override fun getArgb(x: Int, y: Int) = pixels[y * width + x]
    override fun setArgb(x: Int, y: Int, argb: Int) {
        pixels[y * width + x] = argb
    }

    override fun clear() {
        pixels = IntArray(width * height, { Color.BLACK.argb })
    }
}
