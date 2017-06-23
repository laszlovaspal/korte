package hu.laszlovaspal.renderer

import hu.laszlovaspal.renderer.tracer.RayTracer
import hu.laszlovaspal.scene.Scene
import java.util.concurrent.Callable
import java.util.concurrent.Executors

class SimpleParallelRenderer(override val scene: Scene) : Renderer {

    private val rayTracer = RayTracer(1, scene)

    private val executorService = Executors.newFixedThreadPool(8)

    override fun renderFrame(frame: Frame) {
        val start = System.currentTimeMillis()

        val tasks = mutableListOf<Callable<Unit>>()
        for (x in 0..scene.camera.width - 1) {
            for (y in 0..scene.camera.height - 1) {
                tasks.add(
                    Callable {
                        val ray = scene.camera.rayForPixel(x, y)
                        val traceResult = rayTracer.trace(ray)
                        frame.setArgb(x, y, traceResult.color.argb)
                    }
                )
            }
        }
        executorService.invokeAll(tasks)
        executorService.shutdown()

        val end = System.currentTimeMillis()
        println(end - start)
    }
}