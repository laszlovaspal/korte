package hu.laszlovaspal

import hu.laszlovaspal.math.Vector3
import hu.laszlovaspal.renderer.Renderer
import hu.laszlovaspal.renderer.SimpleSequentialRenderer
import hu.laszlovaspal.renderer.tracer.Camera
import javafx.application.Application
import javafx.scene.Group
import javafx.scene.Scene
import javafx.scene.canvas.Canvas
import javafx.scene.paint.Color
import javafx.stage.Stage

fun main(args: Array<String>) {
    Application.launch(UIWindow::class.java, *args)
}

class UIWindow : Application() {

    val camera = Camera(
            width = 800, height = 500,
            position = Vector3(0.0, 0.0, 0.0),
            direction = Vector3(0.0, 0.0, 1.0),
            up = Vector3(0.0, 1.0, 0.0)
    )

    val renderer: Renderer = SimpleSequentialRenderer(camera)

    override fun start(primaryStage: Stage) {
        val canvas = Canvas(camera.width.toDouble(), camera.height.toDouble())
        val pixelWriter = canvas.graphicsContext2D.pixelWriter

        renderer.renderFrame(pixelWriter)

        primaryStage.scene = Scene(Group(canvas), Color.GREY)
        primaryStage.isResizable = false
        primaryStage.show()
    }

}
