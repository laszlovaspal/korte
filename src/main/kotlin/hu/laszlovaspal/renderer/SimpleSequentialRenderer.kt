package hu.laszlovaspal.renderer

import hu.laszlovaspal.renderer.tracer.RayTracer
import hu.laszlovaspal.scene.Scene

class SimpleSequentialRenderer(override val scene: Scene, override val configuration: RenderingConfiguration) : Renderer {

    private val rayTracer = RayTracer(1, scene, configuration)

    override fun renderFrame(frame: Frame) {
        val start = System.currentTimeMillis()
        for (x in 0..scene.camera.width - 1) {
            for (y in 0..scene.camera.height - 1) {
                val ray = scene.camera.rayForPixel(x, y)
                val traceResult = rayTracer.trace(ray)
                frame.setArgb(x, y, traceResult.color.argb)
            }
        }
        val end = System.currentTimeMillis()
        println("sequential: " + (end - start))
    }
}
