package hu.laszlovaspal.krtengine.ui.javafx

import hu.laszlovaspal.krtengine.renderer.Renderer
import hu.laszlovaspal.krtengine.renderer.RenderingConfiguration
import hu.laszlovaspal.krtengine.scene.Scene
import javafx.event.ActionEvent
import javafx.geometry.Insets
import javafx.scene.control.CheckBox
import javafx.scene.control.ComboBox
import javafx.scene.control.Label
import javafx.scene.control.Slider
import javafx.scene.layout.BorderPane
import javafx.scene.layout.HBox
import javafx.scene.layout.VBox
import javafx.util.StringConverter

class ControlPanel(val scene: Scene, val renderers: List<Renderer>, val configuration: RenderingConfiguration) {

    var selectedRenderer = renderers.last()

    val fpsLabel = Label("Calculating fps...")

    private val settings = VBox(
            createRendererSelectorCombobox(),
            createShadowSelectorCheckbox(),
            createDebugCheckbox(),
            createBlockSizeSlider(),
            createRandomSamplesPerBlockSlider()).apply {
        spacing = 4.0
    }

    val controls = BorderPane().apply {
        padding = Insets(4.0)
        top = settings
        bottom = fpsLabel
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

    private fun createBlockSizeSlider(): VBox {
        val label = Label("Blocksize: ")
        val valueLabel = Label(" ${configuration.blockSize}")
        val possibleValues = listOf(2, 5, 10, 20, 25, 50)
        val slider = Slider(0.0, (possibleValues.size - 1).toDouble(), possibleValues.indexOf(configuration.blockSize).toDouble()).apply {
            majorTickUnit = 1.0
            minorTickCount = 0
            isShowTickMarks = true
            isSnapToTicks = true
            valueProperty().addListener { _ ->
                configuration.blockSize = possibleValues[value.toInt()]
                valueLabel.text = " ${configuration.blockSize}"
            }
        }
        return VBox(label, HBox(slider, valueLabel))
    }

    private fun createRandomSamplesPerBlockSlider(): VBox {
        val label = Label("Number of random samples / block: ")
        val valueLabel = Label(" ${configuration.randomSamplesPerBlock}")
        val slider = Slider(1.0, 24.0, configuration.randomSamplesPerBlock.toDouble()).apply {
            majorTickUnit = 1.0
            minorTickCount = 0
            isShowTickMarks = true
            isSnapToTicks = true
            valueProperty().addListener { _ ->
                configuration.randomSamplesPerBlock = value.toInt()
                valueLabel.text = " ${configuration.randomSamplesPerBlock}"
            }
        }
        return VBox(label, HBox(slider, valueLabel))
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

}