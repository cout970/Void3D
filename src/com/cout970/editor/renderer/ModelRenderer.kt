package com.cout970.editor.renderer

import com.cout970.editor.Editor
import com.cout970.editor.modeltree.AbstractTreeNode
import com.cout970.editor.modeltree.Cube
import com.cout970.editor.modeltree.Group
import com.cout970.editor.renderer.util.createSphereVao
import com.cout970.editor.texture.TextureLoader
import com.cout970.editor.util.Log
import com.cout970.gl.matrix.MainMatrixHandler.*
import com.cout970.gl.model.AbstractRenderer
import com.cout970.gl.model.IVAO
import com.cout970.gl.resource.internal.ResourceLocation
import com.cout970.gl.shader.loader.ShaderLoader
import com.cout970.gl.tesellator.ITessellator
import com.cout970.gl.tesellator.Tessellator
import org.joml.Matrix4f
import org.lwjgl.opengl.GL11
import java.io.IOException
import java.util.*

/**
 * Created by cout970 on 30/05/2016.
 */

class ModelRenderer : AbstractRenderer() {

    lateinit var shader: ModelShader
    lateinit var rotationPointVao: IVAO
    var requestedUpdate = true

    init {
        try {
            Log.info("Loading model shader")
            val mainVertex = ShaderLoader.loadShader(Editor.resourceManager.getResource(ResourceLocation(Editor.PROJECT_NAME.toLowerCase(), "shader/modelVertex.glsl")))
            val mainFragment = ShaderLoader.loadShader(Editor.resourceManager.getResource(ResourceLocation(Editor.PROJECT_NAME.toLowerCase(), "shader/modelFragment.glsl")))
            shader = ModelShader(mainVertex.toString(), mainFragment.toString())
        } catch (e: IOException) {
            Log.printStackTrace(e)
        }
        updateProjection()
        rotationPointVao = createSphereVao()
    }

    override fun render() {
        if (requestedUpdate) {
            requestedUpdate = false
            update()
        }

        shader.start()
        shader.enableLight()
        shader.loadLightA(Editor.lightA)
        shader.loadLightB(Editor.lightB)
        shader.loadMaterial(TextureLoader.modelTexture!!)
        shader.loadTextureSize(TextureLoader.textureScale)
        //draw model
        bindTexture(TextureLoader.modelTexture!!.textures[0])
        drawModels()

        //draw rotation point of the selected part
        if(Editor.modelTree.selectedPart != null){
            val part = Editor.modelTree.selectedPart
            bindTexture(TextureLoader.rotationPointTexture)
            pushMatrix()
            getModel().matrix = part!!.getMatrix()
            if(part is Group){
                translate(part.rotationPoint)
            }else if(part is Cube){
                translate(part.rotationPoint)
            }
            loadMatrix(shader)
            shader.loadMaterial(TextureLoader.rotationPointMaterial)
            drawVAO(rotationPointVao)
            shader.loadMaterial(TextureLoader.modelTexture!!)
            popMatrix()
        }

        //draw selection box of the selected part
        if (Editor.modelTree.selectedPart is Cube) {
            val cube = Editor.modelTree.selectedPart as Cube
            bindTexture(TextureLoader.selectionTexture)
            shader.disableLight()
            SelectionRenderer.render(cube, shader)
        }

        shader.stop()
    }

    fun drawModels() {
        pushMatrix()
        renderGroup(Editor.modelTree.root)
        popMatrix()
        loadMatrix(shader)
    }

    private fun renderGroup(i: Group) {
        if (i.vao != null) {
            setMatrix(i.cacheMatrix)
            loadMatrix(shader)
            drawVAO(i.vao)
        }
        for (j in i.subParts) {
            if (j is Group) {
                renderGroup(j)
            }
        }
    }

    fun requestUpdate() {
        requestedUpdate = true
    }

    fun updateGroup(g: Group, tess: ITessellator) {
        if (g.vao != null) {
            g.vao!!.close()
            g.vao = null
        }

        val toRender = mutableListOf<AbstractTreeNode>()
        val subGroups = mutableListOf<Group>()

        for (i in g.subParts) {
            if (i is Cube) {
                toRender.add(i)
            } else if (i is Group) {
                subGroups.add(i)
            }
        }
        val matrix: Matrix4f
        if (g.parent != null) {
            matrix = g.parent!!.cacheMatrix
        } else {
            matrix = Matrix4f().identity()
        }
        g.cacheMatrix = g.getTransformationMatrix().mul(matrix)
        if (g.needUpdate) {
            if (!toRender.isEmpty()) {
                tess.begin(GL11.GL_QUADS)
                for (i in toRender) {
                    i.update()
                    val quads = ArrayList(i.getQuads())
                    for (j in quads) {
                        j.tessellate(tess)
                    }
                }
                tess.end()
                g.vao = tess.data
            }
        }
        for (i in subGroups) {
            i.cacheMatrix = i.getTransformationMatrix().mul(g.cacheMatrix)
            i.update()
            updateGroup(i, tess)
        }
    }

    fun update() {
        updateGroup(Editor.modelTree.root, Tessellator())
    }

    fun updateProjection() {
        getProjection().matrix = Editor.camera.createProjectionMatrix(Editor.window)
    }
}