package hu.laszlovaspal.krtengine.renderer.frame.framesplitting

import hu.laszlovaspal.krtengine.renderer.Frame
import hu.laszlovaspal.krtengine.renderer.Renderer
import hu.laszlovaspal.krtengine.renderer.RenderingConfiguration
import hu.laszlovaspal.krtengine.renderer.pixel.tracer.RayTracer
import hu.laszlovaspal.krtengine.scene.Scene

class FrameSplittingSequentialRenderer(override val scene: Scene,
                                       override val configuration: RenderingConfiguration) : Renderer {

    private val rayTracer = RayTracer(1, scene, configuration)
    private var blocks = emptyList<FrameBlock>()

    override fun renderFrame(frame: Frame) {

        if (blocks.isEmpty() || blocks.first().size != configuration.blockSize) {
            blocks = frame.split(configuration.blockSize, rayTracer, scene, configuration)
        }

        for (block in blocks) {
            if (block.shouldBeRedrawn()) block.redraw()
            else if (configuration.debug && block.isHighlighted) block.redrawWithoutHighlight()
        }
    }

}