package hu.laszlovaspal.krtengine.ui.javafx

import javafx.animation.AnimationTimer
import javafx.scene.control.Label

class FpsMeasurer(val informationLabel: Label) : AnimationTimer() {

    var renderedFrames = 0

    private var lastTimestamp = 0L

    override fun handle(now: Long) {
        val elapsed = now - lastTimestamp
        if (elapsed > 0.5.secondsToNanos()) {
            val fps = renderedFrames / elapsed.nanosToSeconds()
            informationLabel.text = "Fps: ${String.format("%.2f", fps)}"
            lastTimestamp = now
            renderedFrames = 0
        }
    }

    private fun Int.secondsToNanos() = this * 1000000000
    private fun Double.secondsToNanos() = (this * 1000000000).toInt()
    private fun Long.nanosToSeconds() = this / 1000000000.0

}
