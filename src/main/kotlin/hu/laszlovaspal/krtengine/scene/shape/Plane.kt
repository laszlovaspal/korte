package hu.laszlovaspal.krtengine.scene.shape

import hu.laszlovaspal.color.Color
import hu.laszlovaspal.krtengine.renderer.pixel.tracer.Intersection
import hu.laszlovaspal.krtengine.renderer.pixel.tracer.Material
import hu.laszlovaspal.krtengine.renderer.pixel.tracer.Ray
import hu.laszlovaspal.krtengine.renderer.pixel.tracer.Traceable
import hu.laszlovaspal.math.Vector3

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
