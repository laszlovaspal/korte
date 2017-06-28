package hu.laszlovaspal.krtengine.renderer

import hu.laszlovaspal.krtengine.renderer.tracer.RayTracer
import hu.laszlovaspal.krtengine.scene.Scene

class SimpleSequentialRenderer(override val scene: Scene, override val configuration: RenderingConfiguration) : Renderer {

    private val rayTracer = RayTracer(1, scene, configuration)

    override fun renderFrame(frame: Frame): Long {
        val start = System.currentTimeMillis()
        for (y in 0..scene.camera.height - 1) {
            for (x in 0..scene.camera.width - 1) {
                val ray = scene.camera.rayForPixel(x, y)
                val traceResult = rayTracer.trace(ray)
                frame.setArgb(x, y, traceResult.color.argb)
            }
        }
        val end = System.currentTimeMillis()
        return end - start
    }
}
