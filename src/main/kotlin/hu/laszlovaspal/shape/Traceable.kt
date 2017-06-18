package hu.laszlovaspal.shape

import hu.laszlovaspal.math.Vector3
import hu.laszlovaspal.renderer.tracer.Ray

interface Traceable {
    fun intersect(ray: Ray): Distance?
    fun normalAt(point: Vector3): Vector3
}

data class Distance(val traceable: Traceable, val distanceFromCamera: Double)
