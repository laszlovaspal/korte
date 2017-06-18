package hu.laszlovaspal.renderer

import hu.laszlovaspal.renderer.tracer.Camera
import javafx.scene.image.PixelWriter

interface Renderer {
    val camera: Camera
    fun renderFrame(pixelWriter: PixelWriter)
}
