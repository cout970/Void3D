package com.cout970.editor.renderer.util

import com.cout970.gl.model.IVAO
import com.cout970.gl.tesellator.ITessellator
import com.cout970.gl.tesellator.Tessellator
import com.cout970.gl.util.vector.Vector2
import com.cout970.gl.util.vector.Vector3
import org.lwjgl.opengl.GL11

/**
 * Created by cout970 on 26/06/2016.
 */
class CompassRose(tes: ITessellator) {

    val cube: IVAO
    val bottom: IVAO

    init {
        tes.begin(GL11.GL_QUADS)

        val vertex1 = Vector3(0, 0, 0).apply { mul(16.0) }
        val vertex2 = Vector3(1, 0, 0).apply { mul(16.0) }
        val vertex3 = Vector3(1, 1, 0).apply { mul(16.0) }
        val vertex4 = Vector3(0, 1, 0).apply { mul(16.0) }
        val vertex5 = Vector3(0, 0, 1).apply { mul(16.0) }
        val vertex6 = Vector3(1, 0, 1).apply { mul(16.0) }
        val vertex7 = Vector3(1, 1, 1).apply { mul(16.0) }
        val vertex8 = Vector3(0, 1, 1).apply { mul(16.0) }

        val down = arrayOf<Vector3>(vertex6.copy(), vertex5.copy(), vertex1.copy(), vertex2.copy())
        val up = arrayOf<Vector3>(vertex3.copy(), vertex4.copy(), vertex8.copy(), vertex7.copy())
        val north = arrayOf<Vector3>(vertex2.copy(), vertex1.copy(), vertex4.copy(), vertex3.copy())
        val south = arrayOf<Vector3>(vertex5.copy(), vertex6.copy(), vertex7.copy(), vertex8.copy())
        val west = arrayOf<Vector3>(vertex6.copy(), vertex2.copy(), vertex3.copy(), vertex7.copy())
        val east = arrayOf<Vector3>(vertex1.copy(), vertex5.copy(), vertex8.copy(), vertex4.copy())

        val part = 1.0 / 4.0

        val uvs_down = arrayOf(Vector2(0, 0), Vector2(1, 0), Vector2(1, 1), Vector2(0, 1)).apply { forEach { it.mul(part, part).add(0.0, part) } }
        val uvs_up = arrayOf(Vector2(0, 0), Vector2(1, 0), Vector2(1, 1), Vector2(0, 1)).apply { forEach { it.mul(part, part).add(part, part) } }
        val uvs_north = arrayOf(Vector2(0, 0), Vector2(1, 0), Vector2(1, 1), Vector2(0, 1)).apply { forEach { it.mul(part, part).add(0.0, 0.0) } }.flip()
        val uvs_south = arrayOf(Vector2(0, 0), Vector2(1, 0), Vector2(1, 1), Vector2(0, 1)).apply { forEach { it.mul(part, part).add(part, 0.0) } }.flip()
        val uvs_west = arrayOf(Vector2(0, 0), Vector2(1, 0), Vector2(1, 1), Vector2(0, 1)).apply { forEach { it.mul(part, part).add(part * 2, 0.0) } }.flip()
        val uvs_east = arrayOf(Vector2(0, 0), Vector2(1, 0), Vector2(1, 1), Vector2(0, 1)).apply { forEach { it.mul(part, part).add(part * 3, 0.0) } }.flip()

        var count = 0
        //y
        for (v in down) {
            val uv = uvs_down[count]
            count++
            tes.glNormal3f(0f, -1f, 0f)
            tes.glTexCoord2f(uv.xf, uv.yf)
            tes.glVertex3f(v.xf, v.yf, v.zf)
            tes.endVertex()
        }
        count = 0
        for (v in up) {
            val uv = uvs_up[count]
            count++
            tes.glNormal3f(0f, 1f, 0f)
            tes.glTexCoord2f(uv.xf, uv.yf)
            tes.glVertex3f(v.xf, v.yf, v.zf)
            tes.endVertex()
        }
        count = 0
        //z
        for (v in north) {
            val uv = uvs_north[count]
            count++
            tes.glNormal3f(0f, 0f, -1f)
            tes.glTexCoord2f(uv.xf, uv.yf)
            tes.glVertex3f(v.xf, v.yf, v.zf)
            tes.endVertex()
        }
        count = 0
        for (v in south) {
            val uv = uvs_south[count]
            count++
            tes.glNormal3f(0f, 0f, 1f)
            tes.glTexCoord2f(uv.xf, uv.yf)
            tes.glVertex3f(v.xf, v.yf, v.zf)
            tes.endVertex()
        }
        count = 0
        //x
        for (v in west) {
            val uv = uvs_west[count]
            count++
            tes.glNormal3f(-1f, 0f, 0f)
            tes.glTexCoord2f(uv.xf, uv.yf)
            tes.glVertex3f(v.xf, v.yf, v.zf)
            tes.endVertex()
        }
        count = 0
        for (v in east) {
            val uv = uvs_east[count]
            count++
            tes.glNormal3f(1f, 0f, 0f)
            tes.glTexCoord2f(uv.xf, uv.yf)
            tes.glVertex3f(v.xf, v.yf, v.zf)
            tes.endVertex()
        }
        tes.end()
        cube = tes.data

        val tes2 = Tessellator()
        tes2.begin(GL11.GL_QUADS)
        tes2.glNormal3f(0f, 1f, 0f).glTexCoord2f(1f, 0f).glVertex3f( 0f, 0f, 0f).endVertex()
        tes2.glNormal3f(0f, 1f, 0f).glTexCoord2f(0f, 0f).glVertex3f( 0f, 0f, 64f).endVertex()
        tes2.glNormal3f(0f, 1f, 0f).glTexCoord2f(0f, 1f).glVertex3f(64f, 0f, 64f).endVertex()
        tes2.glNormal3f(0f, 1f, 0f).glTexCoord2f(1f, 1f).glVertex3f(64f, 0f, 0f).endVertex()
        tes2.end()
        bottom = tes2.data
    }
}

inline fun <reified T> Array<T>.flip(): Array<T> = Array(this.size, { this[this.size - it - 1] })