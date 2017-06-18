package hu.laszlovaspal.scene

import hu.laszlovaspal.math.Vector3
import hu.laszlovaspal.renderer.tracer.Camera
import hu.laszlovaspal.renderer.tracer.LightSource
import hu.laszlovaspal.shape.Sphere
import hu.laszlovaspal.shape.Traceable

class SimpleScene : Scene {

    override val camera = Camera(
            position = Vector3(0.0, 0.0, 0.0),
            direction = Vector3(0.0, 0.0, 1.0),
            up = Vector3(0.0, 1.0, 0.0)
    )

    override val objects: List<Traceable> = listOf(Sphere(Vector3(0.0, 0.0, 100.0), 10.0), Sphere(Vector3(20.0, 0.0, 150.0), 10.0))

    override val lights: List<LightSource> = listOf(LightSource(Vector3(-100.0, 100.0, 20.0)), LightSource(Vector3(1000.0, 1000.0, -20.0)))
}
