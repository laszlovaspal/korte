package hu.laszlovaspal.krtengine.renderer

import hu.laszlovaspal.color.Color
import hu.laszlovaspal.krtengine.scene.Scene
import java.io.Closeable

interface Renderer : Closeable {
    val configuration: RenderingConfiguration
    val scene: Scene
    fun renderFrame(frame: Frame): Long
    override fun close() {}
}

data class RenderingConfiguration(var shadowsVisible: Boolean = false)

interface Frame {
    val width: Int
    val height: Int
    val pixels: IntArray
    fun setArgb(x: Int, y: Int, argb: Int)
    fun clear()
}

class SimpleFrame(override val width: Int, override val height: Int) : Frame {
    override var pixels = IntArray(width * height)
    override fun setArgb(x: Int, y: Int, argb: Int) {
        pixels[y * width + x] = argb
    }
    override fun clear() {
        pixels = IntArray(width * height, { Color.BLACK.argb })
    }
}
