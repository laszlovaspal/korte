package hu.laszlovaspal.krtengine

import hu.laszlovaspal.krtengine.ui.javafx.UIWindow
import javafx.application.Application

fun main(args: Array<String>) {
    Application.launch(UIWindow::class.java, *args)
}
