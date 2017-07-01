package hu.laszlovaspal.krtengine.renderer

import hu.laszlovaspal.color.Color
import hu.laszlovaspal.krtengine.renderer.tracer.RayTracer
import hu.laszlovaspal.krtengine.scene.Scene
import java.util.concurrent.Callable
import java.util.concurrent.Executors

class SimpleParallelRenderer(override val scene: Scene, override val configuration: RenderingConfiguration) : Renderer {

    private val rayTracer = RayTracer(1, scene, configuration)

    private val numberOfThreads = 4
    private val executorService = Executors.newFixedThreadPool(numberOfThreads)

    override fun renderFrame(frame: Frame): Long {
        val start = System.currentTimeMillis()

        val tasks = (0..numberOfThreads - 1).map { getRenderTaskForThread(it, frame) }
        executorService.invokeAll(tasks)

        val end = System.currentTimeMillis()
        return end - start
    }

    override fun close() {
        executorService.shutdown()
    }

    private fun getRenderTaskForThread(threadId: Int, frame: Frame) = Callable {
        val segment: Int = scene.camera.width / numberOfThreads
        val start = threadId * segment
        for (x in start..(start + segment - 1)) {
            for (y in 0..scene.camera.height - 1) {
                val ray = scene.camera.rayForPixel(x, y)
                val traceResult = rayTracer.trace(ray)
                frame.setArgb(x, y, traceResult.color.argb)
            }
        }
    }

    private fun getDebugColorForThread(threadId: Int): Color {
        return when (threadId) {
            0 -> Color(1.0, 0.0, 0.0)
            1 -> Color(0.0, 1.0, 0.0)
            2 -> Color(0.0, 0.0, 1.0)
            3 -> Color(0.0, 1.0, 1.0)
            else -> Color.BLACK
        }
    }
}
