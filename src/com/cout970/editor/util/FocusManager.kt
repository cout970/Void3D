package com.cout970.editor.util

import com.cout970.editor.Editor
import com.cout970.editor.core.MainWindow

/**
 * Created by cout970 on 15/07/2016.
 */
object FocusManager {

    var onTop: Boolean = false
    var mainWindowFocused = true
    val map = mutableMapOf<Any, Boolean>()
    var ignore = false

    fun tick() {
        val textFocus = map.entries.any { it.value }
        if (onTop != (textFocus || mainWindowFocused)) {
            onTop = (textFocus || mainWindowFocused)
            if (!ignore && onTop) {
                toFront()
            } else {
                toBack()
            }
        }
    }

    fun onMainWindowChangeFocus(window: MainWindow, focus: Boolean) {
        mainWindowFocused = focus
    }

    fun onComponentChangeFocus(field: Any, focus: Boolean) {
        map.put(field, focus)
    }

    fun toFront() {
        Editor.window.subWindows.forEach { it.isAlwaysOnTop = true }
    }

    fun toBack() {
        Editor.window.subWindows.forEach { it.isAlwaysOnTop = false }
        Editor.window.subWindows.forEach { it.toBack() }
    }

    fun hideMainWindow(){
        ignore = true
    }

    fun showMainWindow(){
        ignore = false
    }
}