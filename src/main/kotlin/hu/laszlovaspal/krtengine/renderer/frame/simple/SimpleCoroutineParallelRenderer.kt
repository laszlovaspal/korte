package hu.laszlovaspal.krtengine.renderer.frame.simple

import hu.laszlovaspal.krtengine.renderer.Frame
import hu.laszlovaspal.krtengine.renderer.Renderer
import hu.laszlovaspal.krtengine.renderer.RenderingConfiguration
import hu.laszlovaspal.krtengine.renderer.pixel.tracer.RayTracer
import hu.laszlovaspal.krtengine.scene.Scene
import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.Job
import kotlinx.coroutines.experimental.launch
import kotlinx.coroutines.experimental.runBlocking

class SimpleCoroutineParallelRenderer(override val scene: Scene, override val configuration: RenderingConfiguration) : Renderer {

    private val rayTracer = RayTracer(1, scene, configuration)

    override fun renderFrame(frame: Frame) = runBlocking {
        val tasks = mutableListOf<Job>()
        for (x in 0..scene.camera.width - 1) {
            for (y in 0..scene.camera.height - 1) {
                tasks.add(
                        launch(CommonPool) {
                            val ray = scene.camera.rayForPixel(x, y)
                            val traceResult = rayTracer.trace(ray)
                            frame.setArgb(x, y, traceResult.color.argb)
                        }
                )
            }
        }
        tasks.forEach { it.join() }
    }
}
