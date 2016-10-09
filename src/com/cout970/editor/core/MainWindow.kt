package com.cout970.editor.core

import com.cout970.editor.Editor
import com.cout970.editor.gui.*
import com.cout970.editor.util.FocusManager
import com.cout970.editor.util.Log
import com.cout970.gl.util.vector.Vector2
import com.cout970.gl.window.Window
import org.lwjgl.glfw.GLFW.*
import org.lwjgl.glfw.GLFWCursorEnterCallback
import org.lwjgl.glfw.GLFWWindowFocusCallback

/**
 * Created by cout970 on 07/06/2016.
 */
class MainWindow() : Window(WindowProperties) {

    var minimize = false
    val subWindows = mutableListOf<CustomWindow>()
    lateinit var minimized: BooleanArray
    var enableListeners = false
    var subWindowsOnTop = false

    init {
        Log.info("Creating main window")
        glfwSetWindowFocusCallback(id, WindowFocusCallback)
        glfwSetCursorEnterCallback(id, MouseEnterCallback)
    }

    fun screenSize(): Vector2 {
        val vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor())
        return Vector2(vidmode.width(), vidmode.height())
    }

    fun bindSubWindows() {
        subWindows.clear()
        subWindows.add(WindowMenu.window)
        subWindows.add(WindowEdit.window)
        subWindows.add(WindowTexture.window)
        subWindows.add(WindowModelTree.window)
        subWindows.add(WindowGrids.window)
        minimized = BooleanArray(subWindows.size, { true })
    }

    override fun onChange(x: Int, y: Int) {
        if (x != 0 && y != 0) {
            if (minimize) {
                minimize = false
                for (i in subWindows.indices) {
                    subWindows[i].isVisible = minimized[i]
                }
            }
            if (enableListeners) {
                subWindowsOnTop = true
                subWindows.forEach { it.isAlwaysOnTop = true }
                Editor.modelRenderer.updateProjection()
            }
        } else {
            minimize = true

            for (i in subWindows.indices) {
                minimized[i] = subWindows[i].isVisible
                subWindows[i].isVisible = false
                subWindowsOnTop = false
            }
        }
    }

    override fun close() {
        WindowFocusCallback.close()
        MouseEnterCallback.close()
        super.close()
    }

    override fun tick() {
        super.tick()

    }

    object MouseEnterCallback : GLFWCursorEnterCallback() {
        override fun invoke(window: Long, entered: Boolean) {
        }
    }

    object WindowFocusCallback : GLFWWindowFocusCallback() {

        override fun invoke(window: Long, set: Boolean) {
            FocusManager.onMainWindowChangeFocus(Editor.window, set)
        }
    }
}