package com.cout970.editor.renderer.util

import com.cout970.gl.model.IVAO
import com.cout970.gl.tesellator.ITessellable
import com.cout970.gl.tesellator.ITessellator
import com.cout970.gl.util.vector.Vector2
import com.cout970.gl.util.vector.Vector3
import org.lwjgl.opengl.GL11.*

class CubeRenderer(var start: Vector3?, var end: Vector3?, val useTexture: Boolean = true) : ITessellable {

    fun createVao(tes: ITessellator): IVAO {
        tes.begin(GL_QUADS)
        tessellate(tes)
        tes.end()
        return tes.data
    }

    override fun tessellate(tes: ITessellator) {

        val vertex1 = Vector3(start!!.x, start!!.y, start!!.z)
        val vertex2 = Vector3(end!!.x, start!!.y, start!!.z)
        val vertex3 = Vector3(end!!.x, end!!.y, start!!.z)
        val vertex4 = Vector3(start!!.x, end!!.y, start!!.z)
        val vertex5 = Vector3(start!!.x, start!!.y, end!!.z)
        val vertex6 = Vector3(end!!.x, start!!.y, end!!.z)
        val vertex7 = Vector3(end!!.x, end!!.y, end!!.z)
        val vertex8 = Vector3(start!!.x, end!!.y, end!!.z)

        val down = arrayOf<Vector3>(vertex6.copy(), vertex5.copy(), vertex1.copy(), vertex2.copy())
        val up = arrayOf<Vector3>(vertex3.copy(), vertex4.copy(), vertex8.copy(), vertex7.copy())
        val north = arrayOf<Vector3>(vertex2.copy(), vertex1.copy(), vertex4.copy(), vertex3.copy())
        val south = arrayOf<Vector3>(vertex5.copy(), vertex6.copy(), vertex7.copy(), vertex8.copy())
        val west = arrayOf<Vector3>(vertex6.copy(), vertex2.copy(), vertex3.copy(), vertex7.copy())
        val east = arrayOf<Vector3>(vertex1.copy(), vertex5.copy(), vertex8.copy(), vertex4.copy())
        val uvs = arrayOf(Vector2(0, 0), Vector2(1, 0), Vector2(1, 1), Vector2(0, 1))

        var count = 0
        //y
        for (v in down) {
            val uv = uvs[count]
            tes.glNormal3f(0f, -1f, 0f)
            if(useTexture)
            tes.glTexCoord2f(uv.xf, uv.yf)
            count++
            tes.glVertex3f(v.xf, v.yf, v.zf)
            tes.endVertex()
        }
        count = 0
        for (v in up) {
            val uv = uvs[count]
            tes.glNormal3f(0f, 1f, 0f)
            if(useTexture)
            tes.glTexCoord2f(uv.xf, uv.yf)
            count++
            tes.glVertex3f(v.xf, v.yf, v.zf)
            tes.endVertex()
        }
        count = 0
        //z
        for (v in north) {
            val uv = uvs[count]
            tes.glNormal3f(0f, 0f, -1f)
            if(useTexture)
            tes.glTexCoord2f(uv.xf, uv.yf)
            count++
            tes.glVertex3f(v.xf, v.yf, v.zf)
            tes.endVertex()
        }
        count = 0
        for (v in south) {
            val uv = uvs[count]
            tes.glNormal3f(0f, 0f, 1f)
            if(useTexture)
            tes.glTexCoord2f(uv.xf, uv.yf)
            count++
            tes.glVertex3f(v.xf, v.yf, v.zf)
            tes.endVertex()
        }
        count = 0
        //x
        for (v in west) {
            val uv = uvs[count]
            tes.glNormal3f(-1f, 0f, 0f)
            if(useTexture)
            tes.glTexCoord2f(uv.xf, uv.yf)
            count++
            tes.glVertex3f(v.xf, v.yf, v.zf)
            tes.endVertex()
        }
        count = 0
        for (v in east) {
            val uv = uvs[count]
            tes.glNormal3f(1f, 0f, 0f)
            if(useTexture)
            tes.glTexCoord2f(uv.xf, uv.yf)
            count++
            tes.glVertex3f(v.xf, v.yf, v.zf)
            tes.endVertex()
        }
    }

    fun render() {
        val vertex1 = Vector3(start!!.x, start!!.y, start!!.z)
        val vertex2 = Vector3(end!!.x, start!!.y, start!!.z)
        val vertex3 = Vector3(end!!.x, end!!.y, start!!.z)
        val vertex4 = Vector3(start!!.x, end!!.y, start!!.z)
        val vertex5 = Vector3(start!!.x, start!!.y, end!!.z)
        val vertex6 = Vector3(end!!.x, start!!.y, end!!.z)
        val vertex7 = Vector3(end!!.x, end!!.y, end!!.z)
        val vertex8 = Vector3(start!!.x, end!!.y, end!!.z)

        val down = arrayOf<Vector3>(vertex6.copy(), vertex5.copy(), vertex1.copy(), vertex2.copy())
        val up = arrayOf<Vector3>(vertex3.copy(), vertex4.copy(), vertex8.copy(), vertex7.copy())
        val north = arrayOf<Vector3>(vertex2.copy(), vertex1.copy(), vertex4.copy(), vertex3.copy())
        val south = arrayOf<Vector3>(vertex5.copy(), vertex6.copy(), vertex7.copy(), vertex8.copy())
        val west = arrayOf<Vector3>(vertex6.copy(), vertex2.copy(), vertex3.copy(), vertex7.copy())
        val east = arrayOf<Vector3>(vertex1.copy(), vertex5.copy(), vertex8.copy(), vertex4.copy())
        val uvs = arrayOf(Vector2(0, 0), Vector2(1, 0), Vector2(1, 1), Vector2(0, 1))

        var count = 0
        glBegin(GL_QUADS)
        glNormal3f(0f, 1f, 0f)
        //y
        for (v in down) {
            val uv = uvs[count]
            glTexCoord2d(uv.x, uv.y)
            count++
            glVertex3d(v.x, v.y, v.z)
        }
        count = 0
        for (v in up) {
            val uv = uvs[count]
            glTexCoord2d(uv.x, uv.y)
            count++
            glVertex3d(v.x, v.y, v.z)
        }
        count = 0
        //z
        for (v in north) {
            val uv = uvs[count]
            glTexCoord2d(uv.x, uv.y)
            count++
            glVertex3d(v.x, v.y, v.z)
        }
        count = 0
        for (v in south) {
            val uv = uvs[count]
            glTexCoord2d(uv.x, uv.y)
            count++
            glVertex3d(v.x, v.y, v.z)
        }
        count = 0
        //x
        for (v in west) {
            val uv = uvs[count]
            glTexCoord2d(uv.x, uv.y)
            count++
            glVertex3d(v.x, v.y, v.z)
        }
        count = 0
        for (v in east) {
            val uv = uvs[count]
            glTexCoord2d(uv.x, uv.y)
            count++
            glVertex3d(v.x, v.y, v.z)
        }
        glEnd()
    }
}