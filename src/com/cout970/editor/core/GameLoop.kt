package com.cout970.editor.core

import com.cout970.editor.Editor
import com.cout970.editor.export.backupTick
import com.cout970.editor.gui.WindowTexture
import com.cout970.editor.texture.TextureLoader
import com.cout970.editor.util.FocusManager
import com.cout970.editor.util.Log
import com.cout970.gl.matrix.MainMatrixHandler
import com.cout970.gl.util.vector.Vector3
import com.cout970.gl.window.GameLoop
import org.lwjgl.opengl.GL11
import org.lwjgl.opengl.GL32

/**
 * Created by cout970 on 30/05/2016.
 */
class GameLoop : GameLoop(Editor.window) {

    override fun tick() {
        GL11.glEnable(GL32.GL_TEXTURE_CUBE_MAP_SEAMLESS)
        FocusManager.tick()
        backupTick()
        TextureLoader.tickTextures(timer)
        window.setTitle("${Editor.PROJECT_NAME} [${timer.fps} FPS]")
        Editor.inputManager.updateMouse()
        Editor.camera.checkInput()
        loadView()
        Editor.modelRenderer.render()
        Editor.spaceRenderer.render()
        Editor.guiRenderer.render()
        Editor.skyboxRenderer.render()
        WindowTexture.window.repaint()
        Log.writer.flush()
    }

    private fun loadView() {
        val matrix = MainMatrixHandler.getView()
        matrix.setIdentity()

        val rot = Vector3(0, 0, 0)
        matrix.translate(Vector3(0.0, 0.0, -Editor.camera.distance))
        matrix.translate(rot.inverse())
        matrix.rotate(Math.toRadians(Editor.camera.rotation.pitch.toDouble()).toFloat(), Vector3(1, 0, 0))
        matrix.rotate(Math.toRadians(Editor.camera.rotation.yaw.toDouble()).toFloat(), Vector3(0, 1, 0))
        matrix.translate(rot)
        matrix.translate(Editor.camera.position)
    }
}