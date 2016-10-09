package com.cout970.editor.core

import com.cout970.editor.Editor
import com.cout970.editor.gui.*
import com.cout970.gl.input.AbstractInputEvent
import com.cout970.gl.input.AbstractInputManager
import com.cout970.gl.input.KeyboardEvent
import com.cout970.gl.input.MouseButtonEvent
import com.cout970.gl.util.vector.Vector2
import org.lwjgl.glfw.GLFW.*

/**
 * Created by cout970 on 30/05/2016.
 */
class InputManager : AbstractInputManager(Editor.window) {

    var mousePos = Vector2()
    var mouseDiff = Vector2()

    override fun onEvent(event: AbstractInputEvent?) {
        Editor.camera.event(event)
        if (event is MouseButtonEvent) {
            onMouseClick(event)
        } else if (event is KeyboardEvent) {
            keyPressed(event)
        }
    }

    private fun keyPressed(event: KeyboardEvent) {
        if (event.action == GLFW_PRESS) {
            when (event.keycode) {
                GLFW_KEY_Z -> {
                    if (isKeyPressed(GLFW_KEY_LEFT_CONTROL)) {
                        undo()
                    } else if (isKeyPressed(GLFW_KEY_LEFT_ALT)) {
                        toggleGridX()
                    }
                }
                GLFW_KEY_C -> {
                    if (isKeyPressed(GLFW_KEY_LEFT_CONTROL)) {
                        copy()
                    }
                }
                GLFW_KEY_V -> {
                    if (isKeyPressed(GLFW_KEY_LEFT_CONTROL)) {
                        paste()
                    }
                }
                GLFW_KEY_X -> {
                    if (isKeyPressed(GLFW_KEY_LEFT_CONTROL)) {
                        cut()
                    }
                }
                GLFW_KEY_Y -> {
                    if (isKeyPressed(GLFW_KEY_LEFT_CONTROL)) {
                        redo()
                    }
                }
                GLFW_KEY_Q -> {
                    if (isKeyPressed(GLFW_KEY_LEFT_CONTROL)) {
                        toggleSkybox()
                    }
                }
                GLFW_KEY_W -> {
                    if (isKeyPressed(GLFW_KEY_LEFT_CONTROL)) {
                        toggleBaseBlock()
                    }
                }
                GLFW_KEY_D -> {
                    if (isKeyPressed(GLFW_KEY_LEFT_CONTROL)) {
                        toggleDebugLines()
                    }
                }
                GLFW_KEY_0 -> {
                    if (isKeyPressed(GLFW_KEY_LEFT_ALT)) {
                        toggleMenuWindow()
                    }
                }
                GLFW_KEY_1 -> {
                    if (isKeyPressed(GLFW_KEY_LEFT_ALT)) {
                        toggleEditorWindow()
                    }
                }
                GLFW_KEY_2 -> {
                    if (isKeyPressed(GLFW_KEY_LEFT_ALT)) {
                        toggleModelTreeWindow()
                    }
                }
                GLFW_KEY_3 -> {
                    if (isKeyPressed(GLFW_KEY_LEFT_ALT)) {
                        toggleTextureWindow()
                    }
                }
                GLFW_KEY_4 -> {
                    if (isKeyPressed(GLFW_KEY_LEFT_ALT)) {
                        toggleGridsWindow()
                    }
                }
                GLFW_KEY_X -> {
                    if (isKeyPressed(GLFW_KEY_LEFT_ALT)) {
                        toggleGridY()
                    }
                }
                GLFW_KEY_C -> {
                    if (isKeyPressed(GLFW_KEY_LEFT_ALT)) {
                        toggleGridZ()
                    }
                }
                GLFW_KEY_M -> {
                    toggleMenuWindow()
                }
                GLFW_KEY_DELETE -> {
                    deletePart()
                }
                GLFW_KEY_F2 -> {
                    takeScreenShot()
                }
                GLFW_KEY_E -> {
                    if (isKeyPressed(GLFW_KEY_LEFT_CONTROL)) {
                        saveModelAs()
                    }
                }
                GLFW_KEY_I -> {
                    if (isKeyPressed(GLFW_KEY_LEFT_CONTROL)) {
                        importModel()
                    }
                }
            }
        }
    }

    fun updateMouse() {
        val pos = mousePosition
        mouseDiff = mousePos.copy().sub(pos)
        mousePos = pos
    }
}