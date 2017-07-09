package hu.laszlovaspal.krtengine.ui.javafx

import hu.laszlovaspal.krtengine.renderer.Frame
import hu.laszlovaspal.krtengine.renderer.RenderingConfiguration
import hu.laszlovaspal.krtengine.renderer.SimpleFrame
import hu.laszlovaspal.krtengine.renderer.frame.framesplitting.FrameSplittingSequentialRenderer
import hu.laszlovaspal.krtengine.renderer.frame.simple.SimpleCoroutineParallelRenderer
import hu.laszlovaspal.krtengine.renderer.frame.simple.SimpleParallelRenderer
import hu.laszlovaspal.krtengine.renderer.frame.simple.SimpleSequentialRenderer
import hu.laszlovaspal.krtengine.scene.SimpleScene
import javafx.animation.AnimationTimer
import javafx.application.Application
import javafx.scene.Scene
import javafx.scene.canvas.Canvas
import javafx.scene.image.PixelFormat
import javafx.scene.image.PixelWriter
import javafx.scene.layout.HBox
import javafx.stage.Stage

class UIWindow : Application() {

    val scene = SimpleScene()
    val configuration = RenderingConfiguration()
    val renderers = listOf(
            SimpleSequentialRenderer(scene, configuration),
            SimpleCoroutineParallelRenderer(scene, configuration),
            SimpleParallelRenderer(scene, configuration),
            FrameSplittingSequentialRenderer(scene, configuration)
    )

    override fun start(primaryStage: Stage) {
        val frame = SimpleFrame(scene.camera.width, scene.camera.height)

        val canvas = Canvas(scene.camera.width.toDouble(), scene.camera.height.toDouble())
        val controlPanel = ControlPanel(scene, renderers, configuration)
        val fpsMeasurer = FpsMeasurer(controlPanel.fpsLabel).apply { start() }

        primaryStage.scene = Scene(HBox(canvas, controlPanel.controls))
        primaryStage.scene.onKeyPressed = KeyboardController(scene.camera).keyPressedHandler
        primaryStage.isResizable = false
        primaryStage.show()

        object : AnimationTimer() {
            override fun handle(now: Long) {
                controlPanel.selectedRenderer.renderFrame(frame)
                fpsMeasurer.renderedFrames++
                canvas.graphicsContext2D.pixelWriter.setPixelsFromFrame(frame)
            }
        }.start()
    }

    override fun stop() {
        renderers.map { it.close() }
    }

    private fun PixelWriter.setPixelsFromFrame(frame: Frame) {
        this.setPixels(0, 0, frame.width, frame.height,
                PixelFormat.getIntArgbInstance(), frame.pixels, 0, frame.width)
    }

}
