package hu.laszlovaspal.scene

import hu.laszlovaspal.renderer.tracer.Camera
import hu.laszlovaspal.renderer.tracer.LightSource
import hu.laszlovaspal.shape.Traceable

interface Scene {
    val camera: Camera
    val objects: List<Traceable>
    val lights: List<LightSource>
}
