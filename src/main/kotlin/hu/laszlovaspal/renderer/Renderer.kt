package hu.laszlovaspal.renderer

import hu.laszlovaspal.renderer.tracer.Color
import hu.laszlovaspal.scene.Scene

interface Renderer {
    val configuration: RenderingConfiguration
    val scene: Scene
    fun renderFrame(frame: Frame)
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
