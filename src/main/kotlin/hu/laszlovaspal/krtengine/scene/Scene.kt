package hu.laszlovaspal.krtengine.scene

import hu.laszlovaspal.krtengine.renderer.pixel.tracer.Camera
import hu.laszlovaspal.krtengine.renderer.pixel.tracer.LightSource
import hu.laszlovaspal.krtengine.renderer.pixel.tracer.Traceable

interface Scene {
    val camera: Camera
    val objects: List<Traceable>
    val lights: List<LightSource>
}
