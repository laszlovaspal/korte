package hu.laszlovaspal.shape

import hu.laszlovaspal.math.Vector3
import hu.laszlovaspal.tracer.Ray

interface Renderable {
    fun intersect(ray: Ray): Intersection?
    fun normalAt(point: Vector3): Vector3
}

data class Intersection(val distanceFromCamera: Double, val point: Vector3)
