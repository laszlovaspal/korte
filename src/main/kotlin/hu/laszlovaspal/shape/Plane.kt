package hu.laszlovaspal.shape

import hu.laszlovaspal.math.Vector3
import hu.laszlovaspal.renderer.tracer.Ray
import javafx.scene.paint.Color

class Plane(val position: Vector3, val direction: Vector3, override val material: Material = Material(Color.WHITE)) : Traceable {
    override fun intersect(ray: Ray): Intersection? {
        val distance = ((position - ray.startPoint) dot direction) / (ray.direction dot direction)
        if (distance < 0.0) {
            return null
        }

        return Intersection(this, ray, distance)
    }

    override fun normalAt(point: Vector3) = direction
}
