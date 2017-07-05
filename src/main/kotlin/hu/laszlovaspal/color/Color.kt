package hu.laszlovaspal.color

data class Color(val red: Double, val green: Double, val blue: Double, val alpha: Double = 1.0) {

    val argb: Int

    init {
        if (alpha < 0 || alpha > 1.0) throw IllegalArgumentException("Invalid value for alpha")
        if (red < 0 || red > 1.0) throw IllegalArgumentException("Invalid value for red")
        if (green < 0 || green > 1.0) throw IllegalArgumentException("Invalid value for green")
        if (blue < 0 || blue > 1.0) throw IllegalArgumentException("Invalid value for green")

        val alphaComponent = (alpha * 255).toInt() shl 24
        val redComponent = (red * 255).toInt() shl 16
        val greenComponent = (green * 255).toInt() shl 8
        val blueComponent = (blue * 255).toInt()

        argb = alphaComponent or redComponent or greenComponent or blueComponent
    }

    operator fun plus(colorToAdd: Color): Color {
        var red = this.red + colorToAdd.red * colorToAdd.alpha
        if (red > 1) red = 1.0

        var green = this.green + colorToAdd.green * colorToAdd.alpha
        if (green > 1) green = 1.0

        var blue = this.blue + colorToAdd.blue * colorToAdd.alpha
        if (blue > 1) blue = 1.0

        return Color(red, green, blue, this.alpha)
    }

    operator fun times(cosTheta: Double) =
            Color(red = this.red * cosTheta, green = this.green * cosTheta, blue = this.blue * cosTheta, alpha = this.alpha)

    override fun toString(): String {
        return "Color(red=$red, green=$green, blue=$blue, alpha=$alpha, argb=${Integer.toHexString(argb)})"
    }


    companion object {
        val BLACK = Color(red = 0.0, green = 0.0, blue = 0.0)
        val GRAY = Color(red = 0.5, green = 0.5, blue = 0.5)
        val WHITE = Color(red = 1.0, green = 1.0, blue = 1.0)
    }

}
