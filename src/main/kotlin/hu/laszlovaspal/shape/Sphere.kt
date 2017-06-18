package hu.laszlovaspal.shape

import hu.laszlovaspal.math.Vector3
import hu.laszlovaspal.renderer.tracer.Ray

class Sphere(val position: Vector3, val radius: Double) : Traceable {

    override fun intersect(ray: Ray): Intersection? { // todo clean up
        val tmp = ray.startPoint - position
        val ocLength = Math.sqrt(tmp.x * tmp.x + tmp.y * tmp.y + tmp.z * tmp.z)
        val discriminant = (ray.direction dot tmp) * (ray.direction dot tmp) - ocLength * ocLength + radius * radius
        if (discriminant < 0) {
            return null
        } else if (discriminant == 0.0) {
            val distance = -(ray.direction dot tmp)
            if (distance < 0) return null
            val intersectionPoint = ray.startPoint + ray.direction * distance
            return Intersection(this, distance, intersectionPoint, normalAt(intersectionPoint))
        } else {
            val a = -(ray.direction dot tmp) + Math.sqrt(discriminant)
            val b = -(ray.direction dot tmp) - Math.sqrt(discriminant)
            val distance = minOf(a, b)
            if (distance < 0) return null
            val intersectionPoint = ray.startPoint + ray.direction * distance
            return Intersection(this, distance, intersectionPoint, normalAt(intersectionPoint))
        }
    }

    override fun normalAt(point: Vector3) = (point - position).normalize()
}
