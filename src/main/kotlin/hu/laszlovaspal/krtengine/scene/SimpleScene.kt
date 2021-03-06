package hu.laszlovaspal.krtengine.scene

import hu.laszlovaspal.color.Color
import hu.laszlovaspal.krtengine.renderer.pixel.tracer.Camera
import hu.laszlovaspal.krtengine.renderer.pixel.tracer.LightSource
import hu.laszlovaspal.krtengine.renderer.pixel.tracer.Material
import hu.laszlovaspal.krtengine.renderer.pixel.tracer.Traceable
import hu.laszlovaspal.krtengine.scene.shape.Plane
import hu.laszlovaspal.krtengine.scene.shape.Sphere
import hu.laszlovaspal.math.Vector3

class SimpleScene : Scene {

    override val camera = Camera(
            position = Vector3(0.0, 0.0, 0.0),
            direction = Vector3(0.0, 0.0, 1.0),
            up = Vector3(0.0, 1.0, 0.0)
    )

    override val objects: List<Traceable> = listOf(
            Plane(position = Vector3(0.0, -8.0, 0.0), direction = Vector3(0.0, 1.0, 0.0)),
            Sphere(position = Vector3(0.0, 0.0, 100.0), radius = 10.0),
            Sphere(position = Vector3(20.0, 0.0, 100.0), radius = 8.0, material = Material(Color(0.4, 0.1, 0.1)))
    )

    override val lights: List<LightSource> = listOf(
            LightSource(position = Vector3(-100.0, 20.0, 20.0)),
            LightSource(position = Vector3(1000.0, 1000.0, -20.0), intensity = 0.3)
    )
}
