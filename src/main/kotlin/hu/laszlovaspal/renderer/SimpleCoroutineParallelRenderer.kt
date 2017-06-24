package hu.laszlovaspal.renderer

import hu.laszlovaspal.renderer.tracer.RayTracer
import hu.laszlovaspal.scene.Scene
import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.Job
import kotlinx.coroutines.experimental.launch
import kotlinx.coroutines.experimental.runBlocking

class SimpleCoroutineParallelRenderer(override val scene: Scene) : Renderer {

    private val rayTracer = RayTracer(1, scene)

    override fun renderFrame(frame: Frame) = runBlocking {
        val start = System.currentTimeMillis()

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

        val end = System.currentTimeMillis()
        println(end - start)
    }
}
