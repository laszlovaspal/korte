package hu.laszlovaspal.krtengine.ui.javafx

import hu.laszlovaspal.krtengine.renderer.Frame
import hu.laszlovaspal.krtengine.renderer.Renderer
import hu.laszlovaspal.krtengine.renderer.RenderingConfiguration
import hu.laszlovaspal.krtengine.renderer.SimpleFrame
import hu.laszlovaspal.krtengine.renderer.frame.framesplitting.FrameSplittingSequentialRenderer
import hu.laszlovaspal.krtengine.renderer.frame.simple.SimpleCoroutineParallelRenderer
import hu.laszlovaspal.krtengine.renderer.frame.simple.SimpleParallelRenderer
import hu.laszlovaspal.krtengine.renderer.frame.simple.SimpleSequentialRenderer
import hu.laszlovaspal.krtengine.scene.SimpleScene
import javafx.animation.AnimationTimer
import javafx.application.Application
import javafx.event.ActionEvent
import javafx.geometry.Insets
import javafx.scene.Scene
import javafx.scene.canvas.Canvas
import javafx.scene.control.CheckBox
import javafx.scene.control.ComboBox
import javafx.scene.control.Label
import javafx.scene.control.Slider
import javafx.scene.image.PixelFormat
import javafx.scene.image.PixelWriter
import javafx.scene.layout.BorderPane
import javafx.scene.layout.HBox
import javafx.scene.layout.VBox
import javafx.stage.Stage
import javafx.util.StringConverter

class UIWindow : Application() {

    val scene = SimpleScene()

    val configuration: RenderingConfiguration = RenderingConfiguration(shadowsVisible = true)

    val renderers = listOf(
            SimpleSequentialRenderer(scene, configuration),
            SimpleCoroutineParallelRenderer(scene, configuration),
            SimpleParallelRenderer(scene, configuration),
            FrameSplittingSequentialRenderer(scene, configuration)
    )
    var selectedRenderer = renderers.last()

    override fun start(primaryStage: Stage) {

        val frame = SimpleFrame(scene.camera.width, scene.camera.height)
        val canvas = Canvas(scene.camera.width.toDouble(), scene.camera.height.toDouble())
        val shadowSelector = createShadowSelectorCheckbox()
        val debugCheckbox = createDebugCheckbox()
        val rendererSelector = createRendererSelectorCombobox()
        val informationLabel = Label("Rendering...")
        val blockSizeSlider = createBlockSizeSlider()
        val settings = VBox(rendererSelector, shadowSelector, debugCheckbox, blockSizeSlider).apply {
            spacing = 4.0
        }
        val controlPanel = BorderPane().apply {
            padding = Insets(4.0)
            top = settings
            bottom = informationLabel
        }

        val controller = KeyboardController(scene.camera)
        primaryStage.scene = Scene(HBox(canvas, controlPanel))
        primaryStage.scene.onKeyPressed = controller.keyPressedHandler
        primaryStage.isResizable = false
        primaryStage.show()

        val fpsMeasurer = FpsMeasurer(informationLabel).apply { start() }

        object : AnimationTimer() {
            override fun handle(now: Long) {
                selectedRenderer.renderFrame(frame)
                fpsMeasurer.renderedFrames++
                canvas.graphicsContext2D.pixelWriter.setPixelsFromFrame(frame)
            }
        }.start()

    }

    override fun stop() {
        renderers.map { it.close() }
    }

    private fun createShadowSelectorCheckbox(): CheckBox {
        return CheckBox("Shadows").apply {
            isSelected = configuration.shadowsVisible
            addEventHandler(ActionEvent.ACTION) { configuration.shadowsVisible = !configuration.shadowsVisible }
        }
    }

    private fun createDebugCheckbox(): CheckBox {
        return CheckBox("Debug").apply {
            isSelected = configuration.debug
            addEventHandler(ActionEvent.ACTION) { configuration.debug = !configuration.debug }
        }
    }

    private fun createBlockSizeSlider(): HBox {
        val label = Label("Blocksize: ")
        val valueLabel = Label(" 10")
        val slider = Slider(5.0, 50.0, 10.0).apply {
            majorTickUnit = 5.0
            minorTickCount = 0
            isShowTickMarks = true
            isSnapToTicks = true
            valueProperty().addListener { _ ->
                if (value % majorTickUnit == 0.0) {
                    configuration.blockSize = value.toInt()
                    valueLabel.text = " ${configuration.blockSize}"
                }
            }
        }
        return HBox(label, slider, valueLabel)
    }

    private fun createRendererSelectorCombobox(): ComboBox<Renderer> {
        return ComboBox<Renderer>().apply {
            converter = object : StringConverter<Renderer>() {
                override fun toString(renderer: Renderer) = renderer.javaClass.simpleName
                override fun fromString(string: String?) = TODO("not implemented")
            }
            items.addAll(renderers)
            value = selectedRenderer
            addEventHandler(ActionEvent.ACTION) { selectedRenderer = value }
        }
    }

    private fun PixelWriter.setPixelsFromFrame(frame: Frame) {
        this.setPixels(0, 0, frame.width, frame.height,
                PixelFormat.getIntArgbInstance(), frame.pixels, 0, frame.width)
    }

}
