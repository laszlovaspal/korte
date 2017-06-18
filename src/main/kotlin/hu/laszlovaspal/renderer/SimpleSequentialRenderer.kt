package hu.laszlovaspal.renderer

import hu.laszlovaspal.renderer.tracer.Camera
import hu.laszlovaspal.renderer.tracer.RayTracer
import javafx.scene.image.PixelWriter

class SimpleSequentialRenderer(override val camera: Camera) : Renderer {

    private val rayTracer = RayTracer(1)

    override fun renderFrame(pixelWriter: PixelWriter) {
        for (x in 0..camera.width) {
            for (y in 0..camera.height) {
                val ray = camera.rayForPixel(x, y)
                val traceResult = rayTracer.trace(ray)
                pixelWriter.setColor(x, y, traceResult.color)
            }
        }
    }
}
