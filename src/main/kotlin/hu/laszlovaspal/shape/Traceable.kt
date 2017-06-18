package hu.laszlovaspal.shape

import hu.laszlovaspal.math.Vector3
import hu.laszlovaspal.renderer.tracer.Ray

interface Traceable {
    fun intersect(ray: Ray): Intersection?
    fun normalAt(point: Vector3): Vector3
}

data class Intersection(val distanceFromCamera: Double, val point: Vector3)
