package hu.laszlovaspal.renderer

import hu.laszlovaspal.scene.Scene
import javafx.scene.image.PixelWriter

interface Renderer {
    val scene: Scene
    fun renderFrame(pixelWriter: PixelWriter)
}
