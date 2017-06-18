package hu.laszlovaspal.renderer

import hu.laszlovaspal.renderer.tracer.RayTracer
import hu.laszlovaspal.scene.Scene
import javafx.scene.image.PixelWriter

class SimpleSequentialRenderer(override val scene: Scene) : Renderer {

    private val rayTracer = RayTracer(1, scene)

    override fun renderFrame(pixelWriter: PixelWriter) {
        val start = System.currentTimeMillis()
        for (x in 0..scene.camera.width) {
            for (y in 0..scene.camera.height) {
                val ray = scene.camera.rayForPixel(x, y)
                val traceResult = rayTracer.trace(ray)
                pixelWriter.setColor(x, y, traceResult.color)
            }
        }
        val end = System.currentTimeMillis()
        println(end - start)
    }
}
