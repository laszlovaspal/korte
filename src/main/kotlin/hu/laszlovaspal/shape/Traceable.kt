package hu.laszlovaspal.shape

import hu.laszlovaspal.math.Vector3
import hu.laszlovaspal.renderer.tracer.Ray

interface Traceable {
    fun intersect(ray: Ray): Intersection?
    fun normalAt(point: Vector3): Vector3
}

// todo lazy point, lazy normal
data class Intersection(val traceable: Traceable, val distance: Double, val point: Vector3, val normal: Vector3)
