package hu.laszlovaspal.color

class Color {

    val argb: Int
    val red: Double
    val green: Double
    val blue: Double
    val alpha: Double

    constructor(red: Double, green: Double, blue: Double, alpha: Double = 1.0) {
        validateParameters(alpha, red, green, blue)

        this.red = red
        this.green = green
        this.blue = blue
        this.alpha = alpha

        this.argb = toArgb(alpha, red, green, blue)
    }

    constructor(argb: Int) {
        this.argb = argb

        this.alpha = ((argb shr 24) and 0x000000FF) / 255.0
        this.red   = ((argb shr 16) and 0x000000FF) / 255.0
        this.green = ((argb shr 8)  and 0x000000FF) / 255.0
        this.blue  = ( argb         and 0x000000FF) / 255.0
    }

    private fun validateParameters(alpha: Double, red: Double, green: Double, blue: Double) {
        if (alpha < 0 || alpha > 1.0) throw IllegalArgumentException("Invalid value for alpha")
        if (red < 0 || red > 1.0) throw IllegalArgumentException("Invalid value for red")
        if (green < 0 || green > 1.0) throw IllegalArgumentException("Invalid value for green")
        if (blue < 0 || blue > 1.0) throw IllegalArgumentException("Invalid value for green")
    }

    private fun toArgb(alpha: Double, red: Double, green: Double, blue: Double): Int {
        val alphaComponent = (alpha * 255).toInt() shl 24
        val redComponent = (red * 255).toInt() shl 16
        val greenComponent = (green * 255).toInt() shl 8
        val blueComponent = (blue * 255).toInt()

        return alphaComponent or redComponent or greenComponent or blueComponent
    }

    operator fun plus(colorToAdd: Color): Color {
        val red = (this.red + colorToAdd.red * colorToAdd.alpha).coerceAtMost(1.0)
        val green = (this.green + colorToAdd.green * colorToAdd.alpha).coerceAtMost(1.0)
        val blue = (this.blue + colorToAdd.blue * colorToAdd.alpha).coerceAtMost(1.0)

        return Color(red, green, blue, this.alpha)
    }

    operator fun times(cosTheta: Double) =
            Color(this.red * cosTheta, this.green * cosTheta, this.blue * cosTheta, this.alpha)

    fun difference(color: Color): Double {
        return maxOf(
                Math.abs(red - color.red),
                Math.abs(green - color.green),
                Math.abs(blue - color.blue)
        )
    }

    companion object {
        val BLACK = Color(red = 0.0, green = 0.0, blue = 0.0)
        val GRAY = Color(red = 0.5, green = 0.5, blue = 0.5)
        val WHITE = Color(red = 1.0, green = 1.0, blue = 1.0)
    }

}
