package hu.laszlovaspal.krtengine

import hu.laszlovaspal.krtengine.renderer.Frame
import hu.laszlovaspal.krtengine.renderer.Renderer
import hu.laszlovaspal.krtengine.renderer.RenderingConfiguration
import hu.laszlovaspal.krtengine.renderer.SimpleCoroutineParallelRenderer
import hu.laszlovaspal.krtengine.renderer.SimpleFrame
import hu.laszlovaspal.krtengine.renderer.SimpleParallelRenderer
import hu.laszlovaspal.krtengine.renderer.SimpleSequentialRenderer
import hu.laszlovaspal.krtengine.scene.SimpleScene
import javafx.application.Application
import javafx.application.Platform
import javafx.event.ActionEvent
import javafx.geometry.Insets
import javafx.scene.Scene
import javafx.scene.canvas.Canvas
import javafx.scene.control.CheckBox
import javafx.scene.control.ComboBox
import javafx.scene.control.Label
import javafx.scene.image.PixelFormat
import javafx.scene.image.PixelWriter
import javafx.scene.layout.BorderPane
import javafx.scene.layout.HBox
import javafx.scene.layout.VBox
import javafx.stage.Stage
import javafx.util.StringConverter
import kotlin.concurrent.thread

fun main(args: Array<String>) {
    Application.launch(UIWindow::class.java, *args)
}

class UIWindow : Application() {

    val scene = SimpleScene()

    val configuration: RenderingConfiguration = RenderingConfiguration(shadowsVisible = true)

    val sequentialRenderer: Renderer = SimpleSequentialRenderer(scene, configuration)
    val threadingRenderer: Renderer = SimpleParallelRenderer(scene, configuration)
    val coroutineRenderer: Renderer = SimpleCoroutineParallelRenderer(scene, configuration)

    var currentRenderer: Renderer = sequentialRenderer

    override fun start(primaryStage: Stage) {

        val frame = SimpleFrame(scene.camera.width, scene.camera.height)
        val canvas = Canvas(scene.camera.width.toDouble(), scene.camera.height.toDouble())
        val shadowSelector = createShadowSelectorCheckbox()
        val rendererSelector = createRendererSelectorCombobox()
        val informationLabel = Label("Label")
        val settings = VBox(rendererSelector, shadowSelector).apply {
            spacing = 4.0
        }
        val controlPanel = BorderPane().apply {
            padding = Insets(4.0)
            top = settings
            bottom = informationLabel
        }

        shadowSelector.addEventHandler(ActionEvent.ACTION) {
            renderFrameToCanvas(currentRenderer, frame, canvas.graphicsContext2D.pixelWriter, informationLabel)
        }

        rendererSelector.addEventHandler(ActionEvent.ACTION) {
            currentRenderer = rendererSelector.value
            renderFrameToCanvas(currentRenderer, frame, canvas.graphicsContext2D.pixelWriter, informationLabel)
        }

        renderFrameToCanvas(currentRenderer, frame, canvas.graphicsContext2D.pixelWriter, informationLabel)

        primaryStage.scene = Scene(HBox(canvas, controlPanel))
        primaryStage.isResizable = false
        primaryStage.show()
    }

    private fun createShadowSelectorCheckbox(): CheckBox {
        return CheckBox("Shadows").apply {
            isSelected = configuration.shadowsVisible
            addEventHandler(ActionEvent.ACTION) { configuration.shadowsVisible = !configuration.shadowsVisible }
        }
    }

    private fun createRendererSelectorCombobox(): ComboBox<Renderer> {
        return ComboBox<Renderer>().apply {
            converter = object : StringConverter<Renderer>() {
                override fun toString(renderer: Renderer) = renderer.javaClass.simpleName
                override fun fromString(string: String?) = TODO("not implemented")
            }
            items.addAll(sequentialRenderer, threadingRenderer, coroutineRenderer)
            value = currentRenderer
        }
    }

    private fun renderFrameToCanvas(renderer: Renderer, frame: Frame, pixelWriter: PixelWriter, informationLabel: Label? = null) {
        informationLabel?.text = "Rendering..."
        thread {
            val milliseconds = renderer.renderFrame(frame)

            Platform.runLater {
                pixelWriter.setPixelsFromFrame(frame)
                informationLabel?.text = "${renderer.javaClass.simpleName}: ${milliseconds}ms"
            }
        }
    }

    private fun PixelWriter.setPixelsFromFrame(frame: Frame) {
        this.setPixels(0, 0, frame.width, frame.height,
                PixelFormat.getIntArgbInstance(), frame.pixels, 0, frame.width)
    }

}
