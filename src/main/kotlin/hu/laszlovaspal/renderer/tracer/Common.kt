package hu.laszlovaspal.renderer.tracer

import hu.laszlovaspal.math.Vector3

class Camera(val width: Int, val height: Int, val position: Vector3, direction: Vector3, up: Vector3) {
    val direction = direction.normalize()
    val up = up.normalize()

    fun rayForPixel(x: Int, y: Int): Ray = Ray(
            position,
            Vector3(x - width / 2.0, height / 2.0 - y, 800.0)
    )
}

class Ray(val startPoint: Vector3, direction: Vector3) {
    val direction: Vector3 = direction.normalize()
}

data class LightSource(val position: Vector3)
