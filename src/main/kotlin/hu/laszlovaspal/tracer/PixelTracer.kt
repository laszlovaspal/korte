package hu.laszlovaspal.tracer

import hu.laszlovaspal.math.Vector3
import hu.laszlovaspal.shape.Sphere
import javafx.scene.paint.Color

val screenWidth = 800
val screenHeight = 500

val sphere = Sphere(Vector3(0.0, 0.0, 100.0), 10.0)
val light = LightSource(Vector3(-100.0, 100.0, 20.0))

data class PixelCoordinate(val x: Int, val y: Int)

class Camera(val position: Vector3, direction: Vector3, up: Vector3) {
    val direction = direction.normalize()
    val up = up.normalize()

    fun rayForPixel(pixelCoordinate: PixelCoordinate): Ray = Ray(
            position,
            Vector3(pixelCoordinate.x - screenWidth / 2.0, screenHeight / 2.0 - pixelCoordinate.y, 800.0)
    )
}

class Ray(val startPoint: Vector3, direction: Vector3) {
    val direction: Vector3 = direction.normalize()
}

data class LightSource(val position: Vector3)

data class TraceResult(val color: Color) {
    companion object {
        val INFINITY = TraceResult(Color.BLACK)
    }
}

class PixelTracer(val camera: Camera, val maxDepthOfRecursion: Int) {

    fun tracePixel(pixelCoordinate: PixelCoordinate) = tracePixel(pixelCoordinate, maxDepthOfRecursion)

    private fun tracePixel(pixelCoordinate: PixelCoordinate, currentDepthOfRecursion: Int): TraceResult {
        val ray = camera.rayForPixel(pixelCoordinate)
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

private operator fun Color.plus(closestIntersectedColor: Color): Color {
    var red = this.red + closestIntersectedColor.red
    if (red > 1) red = 1.0

    var green = this.green + closestIntersectedColor.green
    if (green > 1) green = 1.0

    var blue = this.blue + closestIntersectedColor.blue
    if (blue > 1) blue = 1.0

    return Color(red, green, blue, this.opacity)
}

private operator fun Color.times(cosTheta: Double) =
        Color(this.red * cosTheta, this.green * cosTheta, this.blue * cosTheta, this.opacity)
