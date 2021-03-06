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

data class RenderingConfiguration(var shadowsVisible: Boolean = true,
                                  var debug: Boolean = false,
                                  var blockSize: Int = 10,
                                  var randomSamplesPerBlock: Int = 8)

interface Frame {
    val width: Int
    val height: Int
    val pixels: IntArray
    fun getArgb(x: Int, y: Int): Int
    fun setArgb(x: Int, y: Int, argb: Int)
}

class SimpleFrame(override val width: Int, override val height: Int) : Frame {
    override var pixels = IntArray(width * height, { Color.WHITE.argb })
    override fun getArgb(x: Int, y: Int) = pixels[y * width + x]
    override fun setArgb(x: Int, y: Int, argb: Int) {
        pixels[y * width + x] = argb
    }
}
