package hu.laszlovaspal.krtengine.ui.javafx

import hu.laszlovaspal.krtengine.renderer.pixel.tracer.Camera
import hu.laszlovaspal.math.Vector3
import javafx.event.EventHandler
import javafx.scene.input.KeyCode
import javafx.scene.input.KeyEvent

class KeyboardController(val camera: Camera) {

    val keyPressedHandler = EventHandler<KeyEvent> {
        val stepForward = Vector3(0.0, 0.0, 1.0)
        val stepSide = Vector3(1.0, 0.0, 0.0)
        when (it.code) {
            KeyCode.W -> camera.position = camera.position + stepForward
            KeyCode.S -> camera.position = camera.position - stepForward
            KeyCode.A -> camera.position = camera.position - stepSide
            KeyCode.D -> camera.position = camera.position + stepSide
        }
    }

}
