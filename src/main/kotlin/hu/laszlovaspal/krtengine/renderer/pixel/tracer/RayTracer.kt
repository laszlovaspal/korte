package hu.laszlovaspal.krtengine.renderer.pixel.tracer

import hu.laszlovaspal.color.Color
import hu.laszlovaspal.krtengine.renderer.RenderingConfiguration
import hu.laszlovaspal.krtengine.scene.Scene

data class TraceResult(val color: Color) {
    companion object {
        val INFINITY = TraceResult(Color.BLACK)
    }
}

class RayTracer(val maxDepthOfRecursion: Int, val scene: Scene, val configuration: RenderingConfiguration) {

    fun trace(ray: Ray) = trace(ray, maxDepthOfRecursion)

    private fun trace(ray: Ray, currentDepthOfRecursion: Int): TraceResult {
        val intersection = findClosestIntersection(ray) ?: return TraceResult.INFINITY

        var color = Color.BLACK
        for (light in scene.lights) {
            color += calculateColor(intersection, light)
        }

        return TraceResult(color)
    }

    private fun findClosestIntersection(ray: Ray): Intersection? {
        return scene.objects.map { it.intersect(ray) }
                .filterNotNull()
                .sortedBy { it.distance }
                .firstOrNull()
    }

    private fun calculateColor(intersection: Intersection, light: LightSource): Color {
        val rayToLight = createRayToLight(intersection, light)
        var cosTheta = rayToLight.direction dot intersection.traceable.normalAt(intersection.point)
        if (cosTheta < 0) cosTheta = 0.0
        val color = intersection.traceable.material.color * cosTheta * light.intensity
        if (configuration.shadowsVisible) {
            return addShadow(rayToLight, color, intersection)
        }
        return color
    }

    private fun createRayToLight(intersection: Intersection, light: LightSource): Ray {
        val rayToLightStartPoint = intersection.point + (intersection.normal * 0.01)
        return Ray(rayToLightStartPoint, (light.position - rayToLightStartPoint).normalize())
    }

    private fun addShadow(rayToLight: Ray, color: Color, intersection: Intersection): Color {
        if (scene.objects.any { it != intersection.traceable && it.intersect(rayToLight) != null })
            return Color.BLACK
        return color
    }

}
