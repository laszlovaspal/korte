package hu.laszlovaspal.krtengine.scene

import hu.laszlovaspal.krtengine.renderer.tracer.Camera
import hu.laszlovaspal.krtengine.renderer.tracer.LightSource
import hu.laszlovaspal.krtengine.shape.Traceable

interface Scene {
    val camera: Camera
    val objects: List<Traceable>
    val lights: List<LightSource>
}
