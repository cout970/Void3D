package com.cout970.editor.renderer

import com.cout970.editor.Editor
import com.cout970.editor.config.Config
import com.cout970.editor.modeltree.Cube
import com.cout970.editor.modeltree.Group
import com.cout970.editor.renderer.util.CompassRose
import com.cout970.editor.renderer.util.CubeRenderer
import com.cout970.editor.renderer.util.createArrowsModel
import com.cout970.editor.texture.TextureLoader
import com.cout970.editor.util.Log
import com.cout970.gl.debug.DebugRenderHelper
import com.cout970.gl.matrix.MainMatrixHandler.*
import com.cout970.gl.model.AbstractRenderer
import com.cout970.gl.model.IVAO
import com.cout970.gl.resource.internal.ResourceLocation
import com.cout970.gl.shader.loader.ShaderLoader
import com.cout970.gl.tesellator.Tessellator
import com.cout970.gl.util.vector.Vector3
import org.joml.Matrix4f
import org.joml.Vector3f
import org.lwjgl.opengl.GL11
import java.io.IOException

/**
 * Created by cout970 on 25/06/2016.
 */
class SpaceRenderer : AbstractRenderer() {

    lateinit var shader: SpaceShader
    lateinit var shader2: SpaceShader2
    lateinit var baseCube: IVAO
    lateinit var compasRose: IVAO
    lateinit var compasRoseBottom: IVAO
    lateinit var gridX: IVAO
    lateinit var gridY: IVAO
    lateinit var gridZ: IVAO
    lateinit var arrowsModel: IVAO

    init {
        try {
            Log.info("Loading space shader")
            var mainVertex = ShaderLoader.loadShader(Editor.resourceManager.getResource(ResourceLocation(Editor.PROJECT_NAME.toLowerCase(), "shader/spaceVertex.glsl")))
            var mainFragment = ShaderLoader.loadShader(Editor.resourceManager.getResource(ResourceLocation(Editor.PROJECT_NAME.toLowerCase(), "shader/spaceFragment.glsl")))
            shader = SpaceShader(mainVertex.toString(), mainFragment.toString())
            Log.info("Loading space shader 2")
            mainVertex = ShaderLoader.loadShader(Editor.resourceManager.getResource(ResourceLocation(Editor.PROJECT_NAME.toLowerCase(), "shader/spaceVertex2.glsl")))
            mainFragment = ShaderLoader.loadShader(Editor.resourceManager.getResource(ResourceLocation(Editor.PROJECT_NAME.toLowerCase(), "shader/spaceFragment2.glsl")))
            shader2 = SpaceShader2(mainVertex.toString(), mainFragment.toString())
        } catch (e: IOException) {
            Log.printStackTrace(e)
        }
        baseCube = CubeRenderer(Vector3(0, -16, 0), Vector3(16, 0, 16)).createVao(Tessellator())
        val compass = CompassRose(Tessellator())
        compasRose = compass.cube
        compasRoseBottom = compass.bottom
        generateGrids()
        arrowsModel = createArrowsModel()
    }

    override fun render() {
        shader.start()
        shader.enableLight()
        shader.loadLightA(Editor.lightA)
        shader.loadLightB(Editor.lightB)
        shader.loadMaterial(TextureLoader.modelTexture!!)

        if (Config.showGridX || Config.showGridY || Config.showGridZ) {
            shader.loadColor(Config.gridColor)
            shader.enableColor()
            GL11.glDisable(GL11.GL_CULL_FACE)
            if (Config.showGridX) {
                pushMatrix()
                translate(Vector3(Config.gridXPos, 0, 0))
                loadMatrix(shader)
                drawVAO(gridX)
                popMatrix()
            }
            if (Config.showGridY) {
                pushMatrix()
                translate(Vector3(0, Config.gridYPos, 0))
                loadMatrix(shader)
                drawVAO(gridY)
                popMatrix()
            }
            if (Config.showGridZ) {
                pushMatrix()
                translate(Vector3(0, 0, Config.gridZPos))
                loadMatrix(shader)
                drawVAO(gridZ)
                popMatrix()
            }
            GL11.glEnable(GL11.GL_CULL_FACE)
            shader.disableColor()
        }
        loadMatrix(shader)
        if (Config.showBaseBlock) {
            bindTexture(TextureLoader.baseCubeTexture)
            drawVAO(baseCube)
        }

        if (Config.showDebugLines) {
            unbindTextures(1)
            DebugRenderHelper.drawDebugLines()
        }

        //draw rotation point of the selected part
        if(Editor.modelTree.selectedPart != null){
            val part = Editor.modelTree.selectedPart
            pushMatrix()
            getModel().matrix = part!!.getMatrix()
            if(part is Group){
                translate(part.rotationPoint)
                rotate(part.rotation.copy().toRadians().xf, Vector3(1,0,0))
                rotate(part.rotation.copy().toRadians().yf, Vector3(0,1,0))
                rotate(part.rotation.copy().toRadians().zf, Vector3(0,0,1))
            }else if(part is Cube){
                translate(part.rotationPoint)
                rotate(part.rotation.copy().toRadians().xf, Vector3(1,0,0))
                rotate(part.rotation.copy().toRadians().yf, Vector3(0,1,0))
                rotate(part.rotation.copy().toRadians().zf, Vector3(0,0,1))
            }
            loadMatrix(shader)
            bindTexture(TextureLoader.arrowsTexture)
            drawVAO(arrowsModel)
            popMatrix()
        }

        shader.stop()
        shader2.start()
        val matrix = Matrix4f().identity()
        val x = Editor.window.size.xf
        val y = Editor.window.size.yf
        matrix.setOrtho(-x / 2, x / 2, -y / 2, y / 2, 0.001f, 10000f)
        matrix.translate(x / 2 - 70, -y / 2 + 50, -500f)
        matrix.scale(2f, 2f, 2f)
        matrix.rotate(Math.toRadians(30.0).toFloat(), Vector3f(1f, 0f, 0f))
        //rotate from center
        matrix.rotate(Math.toRadians(Editor.camera.rotation.yaw.toDouble()).toFloat(),
                Vector3f(0f, 1f, 0f))
        //move to center
        matrix.translate(-8f, 0f, -8f)
        shader2.loadModelMatrix(matrix)
        bindTexture(TextureLoader.compassRoseTexture)
        drawVAO(compasRose)

        matrix.translate(8f, 0f, 8f)
        matrix.rotate(Math.toRadians(-90.0).toFloat(),
                Vector3f(0f, 1f, 0f))
        matrix.translate(-32f, 0f, -32f)
        shader2.loadModelMatrix(matrix)
        bindTexture(TextureLoader.compassRoseBottomTexture)
        drawVAO(compasRoseBottom)
        shader2.stop()
    }


    private fun generateGrids() {
        val tes = Tessellator()
        val lineSize = 0.0625f
        val min = -64f
        val max = 64f + 16

        //gridX
        tes.begin(GL11.GL_QUADS)
        for (i in min.toInt()..max.toInt()) {
            val size = if (i % 16 == 0) lineSize * 2 else lineSize
            tes.glVertex3f(0f, min, i.toFloat()).glNormal3f(1f, 0f, 0f).endVertex()
            tes.glVertex3f(0f, min, i.toFloat() + size).glNormal3f(1f, 0f, 0f).endVertex()
            tes.glVertex3f(0f, max, i.toFloat() + size).glNormal3f(1f, 0f, 0f).endVertex()
            tes.glVertex3f(0f, max, i.toFloat()).glNormal3f(1f, 0f, 0f).endVertex()
        }
        for (i in min.toInt()..max.toInt()) {
            val size = if (i % 16 == 0) lineSize * 2 else lineSize
            tes.glVertex3f(0f, i.toFloat(), min).glNormal3f(1f, 0f, 0f).endVertex()
            tes.glVertex3f(0f, i.toFloat() + size, min).glNormal3f(1f, 0f, 0f).endVertex()
            tes.glVertex3f(0f, i.toFloat() + size, max).glNormal3f(1f, 0f, 0f).endVertex()
            tes.glVertex3f(0f, i.toFloat(), max).glNormal3f(1f, 0f, 0f).endVertex()
        }
        tes.end()
        gridX = tes.data

        //gridY
        tes.begin(GL11.GL_QUADS)
        for (i in min.toInt()..max.toInt()) {
            val size = if (i % 16 == 0) lineSize * 2 else lineSize
            tes.glVertex3f(min, 0f, i.toFloat()).glNormal3f(0f, 1f, 0f).endVertex()
            tes.glVertex3f(min, 0f, i.toFloat() + size).glNormal3f(0f, 1f, 0f).endVertex()
            tes.glVertex3f(max, 0f, i.toFloat() + size).glNormal3f(0f, 1f, 0f).endVertex()
            tes.glVertex3f(max, 0f, i.toFloat()).glNormal3f(0f, 1f, 0f).endVertex()
        }
        for (i in min.toInt()..max.toInt()) {
            val size = if (i % 16 == 0) lineSize * 2 else lineSize
            tes.glVertex3f(i.toFloat(), 0f, min).glNormal3f(0f, 1f, 0f).endVertex()
            tes.glVertex3f(i.toFloat() + size, 0f, min).glNormal3f(0f, 1f, 0f).endVertex()
            tes.glVertex3f(i.toFloat() + size, 0f, max).glNormal3f(0f, 1f, 0f).endVertex()
            tes.glVertex3f(i.toFloat(), 0f, max).glNormal3f(0f, 1f, 0f).endVertex()
        }
        tes.end()
        gridY = tes.data

        //gridZ
        tes.begin(GL11.GL_QUADS)
        for (i in min.toInt()..max.toInt()) {
            val size = if (i % 16 == 0) lineSize * 2 else lineSize
            tes.glVertex3f(min, i.toFloat(), 0f).glNormal3f(0f, 0f, 1f).endVertex()
            tes.glVertex3f(min, i.toFloat() + size, 0f).glNormal3f(0f, 0f, 1f).endVertex()
            tes.glVertex3f(max, i.toFloat() + size, 0f).glNormal3f(0f, 0f, 1f).endVertex()
            tes.glVertex3f(max, i.toFloat(), 0f).glNormal3f(0f, 0f, 1f).endVertex()
        }
        for (i in min.toInt()..max.toInt()) {
            val size = if (i % 16 == 0) lineSize * 2 else lineSize
            tes.glVertex3f(i.toFloat(), min, 0f).glNormal3f(0f, 0f, 1f).endVertex()
            tes.glVertex3f(i.toFloat() + size, min, 0f).glNormal3f(0f, 0f, 1f).endVertex()
            tes.glVertex3f(i.toFloat() + size, max, 0f).glNormal3f(0f, 0f, 1f).endVertex()
            tes.glVertex3f(i.toFloat(), max, 0f).glNormal3f(0f, 0f, 1f).endVertex()
        }
        tes.end()
        gridZ = tes.data
    }
}