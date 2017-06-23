package hu.laszlovaspal

import hu.laszlovaspal.renderer.Renderer
import hu.laszlovaspal.renderer.SimpleSequentialRenderer
import hu.laszlovaspal.scene.SimpleScene
import javafx.application.Application
import javafx.scene.Group
import javafx.scene.Scene
import javafx.scene.canvas.Canvas
import javafx.stage.Stage

fun main(args: Array<String>) {
    Application.launch(UIWindow::class.java, *args)
}

class UIWindow : Application() {

    val scene = SimpleScene()
    val renderer: Renderer = SimpleSequentialRenderer(scene)

    override fun start(primaryStage: Stage) {
        val canvas = Canvas(scene.camera.width.toDouble(), scene.camera.height.toDouble())
        val pixelWriter = canvas.graphicsContext2D.pixelWriter

        renderer.renderFrame(pixelWriter)

        primaryStage.scene = Scene(Group(canvas))
        primaryStage.isResizable = false
        primaryStage.show()
    }

}
