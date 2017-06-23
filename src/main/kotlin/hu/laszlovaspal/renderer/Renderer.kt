package hu.laszlovaspal.renderer

import hu.laszlovaspal.scene.Scene

interface Renderer {
    val scene: Scene
    fun renderFrame(frame: Frame)
}

interface Frame {
    val pixels: IntArray
    fun setArgb(x: Int, y: Int, argb: Int)
}

class SimpleFrame(val width: Int, val height: Int) : Frame {
    override val pixels = IntArray(width * height)
    override fun setArgb(x: Int, y: Int, argb: Int) {
        pixels[y * width + x] = argb
    }
}
