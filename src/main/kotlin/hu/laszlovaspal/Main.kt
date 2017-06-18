package hu.laszlovaspal

import hu.laszlovaspal.math.Vector3
import hu.laszlovaspal.tracer.Camera
import hu.laszlovaspal.tracer.PixelCoordinate
import hu.laszlovaspal.tracer.PixelTracer
import hu.laszlovaspal.tracer.screenHeight
import hu.laszlovaspal.tracer.screenWidth
import javafx.application.Application
import javafx.scene.Group
import javafx.scene.Scene
import javafx.scene.canvas.Canvas
import javafx.scene.paint.Color
import javafx.stage.Stage

fun main(args: Array<String>) {
    Application.launch(Renderer::class.java, *args)
}

class Renderer : Application() {
    override fun start(primaryStage: Stage) {
        val camera = Camera(
                position = Vector3(0.0, 0.0, 0.0),
                direction = Vector3(0.0, 0.0, 1.0),
                up = Vector3(0.0, 1.0, 0.0)
        )

        val canvas = Canvas(screenWidth.toDouble(), screenHeight.toDouble())
        val pixelWriter = canvas.graphicsContext2D.pixelWriter

        val pixelTracer = PixelTracer(camera, 1)
        for (x in 0..screenWidth) {
            for (y in 0..screenHeight) {
                val traceResult = pixelTracer.tracePixel(PixelCoordinate(x, y))
                pixelWriter.setColor(x, y, traceResult.color)
            }
        }

        primaryStage.scene = Scene(Group(canvas), Color.GREY)
        primaryStage.isResizable = false
        primaryStage.show()
    }

}
