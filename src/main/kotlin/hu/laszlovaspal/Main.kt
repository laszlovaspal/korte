package hu.laszlovaspal

import hu.laszlovaspal.renderer.Renderer
import hu.laszlovaspal.renderer.SimpleFrame
import hu.laszlovaspal.renderer.SimpleSequentialRenderer
import hu.laszlovaspal.scene.SimpleScene
import javafx.application.Application
import javafx.scene.Group
import javafx.scene.Scene
import javafx.scene.canvas.Canvas
import javafx.scene.image.PixelFormat
import javafx.stage.Stage

fun main(args: Array<String>) {
    Application.launch(UIWindow::class.java, *args)
}

class UIWindow : Application() {

    val scene = SimpleScene()
    val renderer: Renderer = SimpleSequentialRenderer(scene)

    override fun start(primaryStage: Stage) {

        val frame = SimpleFrame(scene.camera.width, scene.camera.height)
        renderer.renderFrame(frame)

        val canvas = Canvas(scene.camera.width.toDouble(), scene.camera.height.toDouble())
        val pixelWriter = canvas.graphicsContext2D.pixelWriter
        pixelWriter.setPixels(0, 0, frame.width, frame.height,
                PixelFormat.getIntArgbInstance(), frame.pixels, 0, frame.width)

        primaryStage.scene = Scene(Group(canvas))
        primaryStage.isResizable = false
        primaryStage.show()
    }

}
