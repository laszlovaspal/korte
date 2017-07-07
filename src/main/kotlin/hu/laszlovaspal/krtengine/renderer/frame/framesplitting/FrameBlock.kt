package hu.laszlovaspal.krtengine.renderer.frame.framesplitting

import hu.laszlovaspal.color.Color
import hu.laszlovaspal.krtengine.renderer.Frame
import hu.laszlovaspal.krtengine.renderer.RenderingConfiguration
import hu.laszlovaspal.krtengine.renderer.SimpleFrame
import hu.laszlovaspal.krtengine.renderer.pixel.tracer.RayTracer
import hu.laszlovaspal.krtengine.scene.Scene
import java.util.*

class FrameBlock(private val frame: Frame,
                 val x: Int, val y: Int, val size: Int,
                 val rayTracer: RayTracer,
                 val scene: Scene,
                 val configuration: RenderingConfiguration) {

    private val random = Random()

    private val highlightColor = Color(1.0, 0.0, 1.0, 0.5)
    private val withoutHighlight = SimpleFrame(size, size)
    var isHighlighted = false

    fun shouldBeRedrawn(): Boolean {
        val sampleX = random.nextInt(size)
        val sampleY = random.nextInt(size)

        val ray = scene.camera.rayForPixel(sampleX + x, sampleY + y)
        val sampledColorInBlock = rayTracer.trace(ray).color
        val previousColor = Color(withoutHighlight.getArgb(sampleX, sampleY))

        return sampledColorInBlock.difference(previousColor) > 0.03
    }

    fun redraw() = when {
        configuration.debug -> redrawWithHighlight()
        else -> redrawWithoutHighlight()
    }

    fun redrawWithHighlight() {
        draw(highlightColor)
        isHighlighted = true
    }

    fun redrawWithoutHighlight() {
        draw()
        isHighlighted = false
    }

    private fun draw(highlight: Color? = null) {
        for (blockCol in 0..size - 1) {
            for (blockRow in 0..size - 1) {
                val newX = (x + blockRow)
                val newY = (y + blockCol)
                val ray = scene.camera.rayForPixel(newX, newY)
                val traceResult = rayTracer.trace(ray)

                var color = traceResult.color
                if (highlight != null) color += highlight

                frame.setArgb(newX, newY, color.argb)
                withoutHighlight.setArgb(blockRow, blockCol, traceResult.color.argb)
            }
        }
    }
}

fun Frame.split(size: Int, rayTracer: RayTracer,
                scene: Scene, configuration: RenderingConfiguration): List<FrameBlock> {
    val blocks = mutableListOf<FrameBlock>()
    for (y in 0..this.height - 1 step size) {
        for (x in 0..this.width - 1 step size) {
            blocks.add(FrameBlock(this, x, y, size, rayTracer, scene, configuration))
        }
    }
    return blocks
}
