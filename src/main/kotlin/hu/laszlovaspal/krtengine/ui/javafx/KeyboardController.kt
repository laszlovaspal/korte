package hu.laszlovaspal.krtengine.ui.javafx

import hu.laszlovaspal.krtengine.renderer.pixel.tracer.Camera
import hu.laszlovaspal.math.Vector3
import javafx.event.EventHandler
import javafx.scene.input.KeyCode
import javafx.scene.input.KeyEvent

class KeyboardController(val camera: Camera) {

    val keyPressedHandler = EventHandler<KeyEvent> {
        val stepForward = Vector3(0.0, 0.0, 100.0)
        val stepSide = Vector3(50.0, 0.0, 0.0)
        when (it.code) {
            KeyCode.W -> camera.speed = stepForward
            KeyCode.S -> camera.speed = -stepForward
            KeyCode.A -> camera.speed = -stepSide
            KeyCode.D -> camera.speed = stepSide
        }
    }

    val keyReleasedHandler = EventHandler<KeyEvent> {
        camera.speed = Vector3.ZERO
    }

}
