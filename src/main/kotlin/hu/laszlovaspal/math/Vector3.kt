package hu.laszlovaspal.math

interface Vector3 {
    val x: Double
    val y: Double
    val z: Double

    operator fun unaryMinus(): Vector3
    operator fun plus(v: Vector3): Vector3
    operator fun minus(v: Vector3): Vector3
    fun dot(v: Vector3): Double
    fun cross(v: Vector3): Vector3
}

data class Vector3Impl(
        override val x: Double,
        override val y: Double,
        override val z: Double) : Vector3 {

    override fun unaryMinus() = Vector3Impl(-x, -y, -z)
    override fun plus(v: Vector3) = Vector3Impl(x + v.x, y + v.y, z + v.z)
    override fun minus(v: Vector3) = this + (-v)
    override fun dot(v: Vector3) = x * v.x + y * v.y + z * v.z
    override fun cross(v: Vector3) = Vector3Impl(
            y * v.z - z * v.y,
            z * v.x - x * v.z,
            x * v.y - y * v.x
    )
}
