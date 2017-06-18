package hu.laszlovaspal.renderer.tracer

import hu.laszlovaspal.math.Vector3
import hu.laszlovaspal.shape.Sphere
import javafx.scene.paint.Color

val sphere = Sphere(Vector3(0.0, 0.0, 100.0), 10.0)
val light = LightSource(Vector3(-100.0, 100.0, 20.0))

data class TraceResult(val color: Color) {
    companion object {
        val INFINITY = TraceResult(Color.BLACK)
    }
}

class RayTracer(val maxDepthOfRecursion: Int) {

    fun trace(ray: Ray) = trace(ray, maxDepthOfRecursion)

    private fun trace(ray: Ray, currentDepthOfRecursion: Int): TraceResult {
        val intersection = sphere.intersect(ray) ?: return TraceResult.INFINITY

        val normal = sphere.normalAt(intersection.point)
        val rayToLightStartPoint = intersection.point + (normal * 0.01)

        val rayToLight = Ray(rayToLightStartPoint, (light.position - rayToLightStartPoint).normalize())
        var cosTheta = rayToLight.direction dot normal
        if (cosTheta < 0) cosTheta = 0.0

        val closestIntersectedColor = Color.WHITE * cosTheta

        return TraceResult(Color.BLACK + closestIntersectedColor)
    }
}
