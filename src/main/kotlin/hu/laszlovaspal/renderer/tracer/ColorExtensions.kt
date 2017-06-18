package hu.laszlovaspal.renderer.tracer

import javafx.scene.paint.Color

internal operator fun Color.plus(closestIntersectedColor: Color): Color {
    var red = this.red + closestIntersectedColor.red
    if (red > 1) red = 1.0

    var green = this.green + closestIntersectedColor.green
    if (green > 1) green = 1.0

    var blue = this.blue + closestIntersectedColor.blue
    if (blue > 1) blue = 1.0

    return Color(red, green, blue, this.opacity)
}

internal operator fun Color.times(cosTheta: Double) =
        Color(this.red * cosTheta, this.green * cosTheta, this.blue * cosTheta, this.opacity)
