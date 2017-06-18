package hu.laszlovaspal.shape

import hu.laszlovaspal.math.Vector3
import hu.laszlovaspal.renderer.tracer.Ray

class Sphere(val position: Vector3, val radius: Double) : Traceable {

    override fun intersect(ray: Ray): Distance? { // todo clean up
        val tmp = ray.startPoint - position
        val ocLength = Math.sqrt(tmp.x * tmp.x + tmp.y * tmp.y + tmp.z * tmp.z)
        val discriminant = (ray.direction dot tmp) * (ray.direction dot tmp) - ocLength * ocLength + radius * radius
        if (discriminant < 0) {
            return null
        } else if (discriminant == 0.0) {
            return Distance(this, -(ray.direction dot tmp))
        } else {
            val a = -(ray.direction dot tmp) + Math.sqrt(discriminant)
            val b = -(ray.direction dot tmp) - Math.sqrt(discriminant)
            return Distance(this, minOf(a, b))
        }
    }

    override fun normalAt(point: Vector3) = (point - position).normalize()
}
