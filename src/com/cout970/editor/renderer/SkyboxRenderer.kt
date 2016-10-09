package com.cout970.editor.renderer

import com.cout970.editor.Editor
import com.cout970.editor.config.Config
import com.cout970.editor.util.Log
import com.cout970.gl.matrix.MainMatrixHandler
import com.cout970.gl.model.AbstractRenderer
import com.cout970.gl.model.ModelVAO
import com.cout970.gl.resource.IResource
import com.cout970.gl.resource.internal.ResourceLocation
import com.cout970.gl.shader.loader.ShaderLoader
import com.cout970.gl.tesellator.Tessellator
import com.cout970.gl.texture.ITexture
import org.lwjgl.opengl.GL11.GL_TRIANGLES
import org.lwjgl.opengl.GL11.glBindTexture
import org.lwjgl.opengl.GL13
import java.io.IOException

/**
 * Created by cout970 on 14/06/2016.
 */
class SkyboxRenderer : AbstractRenderer() {

    private val SIZE = 1000f

    private val VERTICES = floatArrayOf(-SIZE, SIZE, -SIZE, -SIZE, -SIZE, -SIZE, SIZE, -SIZE, -SIZE, SIZE, -SIZE, -SIZE, SIZE, SIZE, -SIZE, -SIZE, SIZE, -SIZE,
            -SIZE, -SIZE, SIZE, -SIZE, -SIZE, -SIZE, -SIZE, SIZE, -SIZE, -SIZE, SIZE, -SIZE, -SIZE, SIZE, SIZE, -SIZE, -SIZE, SIZE,
            SIZE, -SIZE, -SIZE, SIZE, -SIZE, SIZE, SIZE, SIZE, SIZE, SIZE, SIZE, SIZE, SIZE, SIZE, -SIZE, SIZE, -SIZE, -SIZE,
            -SIZE, -SIZE, SIZE, -SIZE, SIZE, SIZE, SIZE, SIZE, SIZE, SIZE, SIZE, SIZE, SIZE, -SIZE, SIZE, -SIZE, -SIZE, SIZE,
            -SIZE, SIZE, -SIZE, SIZE, SIZE, -SIZE, SIZE, SIZE, SIZE, SIZE, SIZE, SIZE, -SIZE, SIZE, SIZE, -SIZE, SIZE, -SIZE,
            -SIZE, -SIZE, -SIZE, -SIZE, -SIZE, SIZE, SIZE, -SIZE, -SIZE, SIZE, -SIZE, -SIZE, -SIZE, -SIZE, SIZE, SIZE, -SIZE, SIZE)

    var model: ModelVAO? = null
    var texture: ITexture
    lateinit var shader: SkyboxShader

    init {
        createModel()
        try {
            Log.info("Loading skybox shader")
            val mainVertex = ShaderLoader.loadShader(Editor.resourceManager.getResource(ResourceLocation(Editor.PROJECT_NAME.toLowerCase(), "shader/skyboxVertex.glsl")))
            val mainFragment = ShaderLoader.loadShader(Editor.resourceManager.getResource(ResourceLocation(Editor.PROJECT_NAME.toLowerCase(), "shader/skyboxFragment.glsl")))
            shader = SkyboxShader(mainVertex.toString(), mainFragment.toString())
        } catch (e: IOException) {
            Log.printStackTrace(e)
        }
        val res = arrayOfNulls<IResource>(6)
        val texNames = arrayOf("right", "left", "top", "bottom", "back", "front")
        Log.info("Loading skybox textures")
        for (i in 0..5) {
            res[i] = Editor.resourceManager.getResource(ResourceLocation(Editor.PROJECT_NAME.toLowerCase(), "skybox/" + texNames[i] + ".png"))
        }
        texture = Editor.textureManager.loadTextureCube(res)
    }

    fun createModel() {
        val tes = Tessellator()
        tes.begin(GL_TRIANGLES)
        var i = 0
        while (i < VERTICES.size) {
            tes.glVertex3f(VERTICES[i++], VERTICES[i++], VERTICES[i++]).endVertex()
        }
        tes.end()
        model = tes.data
    }

    override fun render() {
        if (!Config.showSkybox) return
        shader.start()
        MainMatrixHandler.loadMatrix(shader)
        bindVAO(model)
        glBindTexture(GL13.GL_TEXTURE_CUBE_MAP, texture.id)
        bindVBOs(1)
        draw(model!!.drawMode, model!!.vertexCount, model!!.useElements())
        unbindVBOs(1)
        unbindVAO()
        shader.stop()
    }
}