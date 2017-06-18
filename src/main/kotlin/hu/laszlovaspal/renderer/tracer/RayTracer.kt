package hu.laszlovaspal.renderer.tracer

import hu.laszlovaspal.math.Vector3
import hu.laszlovaspal.scene.Scene
import hu.laszlovaspal.shape.Traceable
import javafx.scene.paint.Color

data class TraceResult(val color: Color) {
    companion object {
        val INFINITY = TraceResult(Color.BLACK)
    }
}

class RayTracer(val maxDepthOfRecursion: Int, val scene: Scene) {

    fun trace(ray: Ray) = trace(ray, maxDepthOfRecursion)

    private fun trace(ray: Ray, currentDepthOfRecursion: Int): TraceResult {
        val intersection = findClosestIntersection(ray, scene.objects) ?: return TraceResult.INFINITY

        var color = Color.BLACK
        for (light in scene.lights) {
            val rayToLight = createRayToLight(intersection, light)
            var cosTheta = rayToLight.direction dot intersection.normal
            if (cosTheta < 0) cosTheta = 0.0

            color += Color.WHITE * cosTheta
        }

        return TraceResult(color)
    }

    private fun findClosestIntersection(ray: Ray, objects: List<Traceable>): Intersection? {
        val closest = objects.map { it.intersect(ray) }
                .filterNotNull()
                .sortedBy { it.distanceFromCamera }
                .firstOrNull() ?: return null

        val intersectionPoint = ray.startPoint + ray.direction * closest.distanceFromCamera
        return Intersection(intersectionPoint, closest.traceable.normalAt(intersectionPoint))
    }

    private fun createRayToLight(intersection: Intersection, light: LightSource): Ray {
        val rayToLightStartPoint = intersection.point + (intersection.normal * 0.01)
        return Ray(rayToLightStartPoint, (light.position - rayToLightStartPoint).normalize())
    }

}

data class Intersection(val point: Vector3, val normal: Vector3)
