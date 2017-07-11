package hu.laszlovaspal.krtengine.renderer.pixel.tracer

import hu.laszlovaspal.color.Color
import hu.laszlovaspal.math.Vector3

interface Movable {
    var position: Vector3
    var direction: Vector3
    var up: Vector3
    var speed: Vector3

    fun move(deltaTimeNanos: Long) {
        position += (speed * (deltaTimeNanos / 1000000000.0))
    }
}

class Camera(override var position: Vector3, direction: Vector3, up: Vector3) : Movable {
    val width = 800
    val height = 500

    override var direction = direction.normalize()
    override var up = up.normalize()
    override var speed = Vector3.ZERO

    fun rayForPixel(x: Int, y: Int): Ray = Ray(
            position,
            Vector3(x - width / 2.0, height / 2.0 - y, 800.0)
    )
}

class Ray(val startPoint: Vector3, direction: Vector3) {
    val direction: Vector3 = direction.normalize()
}

data class LightSource(val position: Vector3, var intensity: Double = 1.0) {
    init {
        if (intensity < 0.0) intensity = 0.0
        if (intensity > 1.0) intensity = 1.0
    }
}

interface Traceable {
    val material: Material
    fun intersect(ray: Ray): Intersection?
    fun normalAt(point: Vector3): Vector3
}

data class Material(val color: Color)

class Intersection(val traceable: Traceable, val ray: Ray, val distance: Double) {
    val point: Vector3 by lazy { ray.startPoint + ray.direction * distance }
    val normal: Vector3 by lazy { traceable.normalAt(point) }
}
