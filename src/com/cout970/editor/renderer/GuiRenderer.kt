package com.cout970.editor.renderer

import com.cout970.editor.Editor
import com.cout970.editor.texture.TextureLoader
import com.cout970.editor.util.Log
import com.cout970.gl.input.MouseButtonEvent
import com.cout970.gl.model.AbstractRenderer
import com.cout970.gl.model.ModelVAO
import com.cout970.gl.resource.internal.ResourceLocation
import com.cout970.gl.shader.loader.ShaderLoader
import com.cout970.gl.tesellator.Tessellator
import org.lwjgl.opengl.GL11
import java.io.IOException

/**
 * Created by cout970 on 25/06/2016.
 */
class GuiRenderer : AbstractRenderer() {

    var guiVao: ModelVAO? = null
    lateinit var guiShader: GuiShader

    init {
        try {
            Log.info("Loading gui shader")
            val mainVertex = ShaderLoader.loadShader(Editor.resourceManager.getResource(ResourceLocation(Editor.PROJECT_NAME.toLowerCase(), "shader/guiVertex.glsl")))
            val mainFragment = ShaderLoader.loadShader(Editor.resourceManager.getResource(ResourceLocation(Editor.PROJECT_NAME.toLowerCase(), "shader/guiFragment.glsl")))
            guiShader = GuiShader(mainVertex.toString(), mainFragment.toString())
        } catch (e: IOException) {
            Log.printStackTrace(e)
        }
    }

    override fun render() {
        if (Editor.inputManager.isMouseButtonPressed(MouseButtonEvent.MouseButton.MIDDLE) ||
                Editor.inputManager.isMouseButtonPressed(MouseButtonEvent.MouseButton.RIGHT)) {
            guiShader.start()
            guiShader.setSize(Editor.window)
            drawCenter()
            guiShader.stop()
        }
    }

    private fun drawCenter() {
        if (guiVao == null) {
            val zIndex = -1.0f
            val tes = Tessellator()
            tes.begin(GL11.GL_QUADS)
            tes.glTexCoord2f(0f, 0f).glVertex3f(0f, 0f, zIndex).endVertex()
            tes.glTexCoord2f(1f, 0f).glVertex3f(1f, 0f, zIndex).endVertex()
            tes.glTexCoord2f(1f, 1f).glVertex3f(1f, 1f, zIndex).endVertex()
            tes.glTexCoord2f(0f, 1f).glVertex3f(0f, 1f, zIndex).endVertex()
            tes.end()
            guiVao = tes.data
        }
        bindTexture(TextureLoader.centerTexture)
        drawVAO(guiVao)
    }
}