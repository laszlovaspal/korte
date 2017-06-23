package hu.laszlovaspal.shape

import hu.laszlovaspal.math.Vector3
import hu.laszlovaspal.renderer.tracer.Color
import hu.laszlovaspal.renderer.tracer.Ray

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
