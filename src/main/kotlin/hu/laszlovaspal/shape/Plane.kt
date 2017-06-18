package hu.laszlovaspal.shape

import hu.laszlovaspal.math.Vector3
import hu.laszlovaspal.renderer.tracer.Ray

class Plane(val position: Vector3, val direction: Vector3) : Traceable {
    override fun intersect(ray: Ray): Distance? {
        val t = ((position - ray.startPoint) dot direction) / (ray.direction dot direction)
        if (t < 0.01) {
            return null
        }

        return Distance(this, t)
    }

    override fun normalAt(point: Vector3) = direction
}
