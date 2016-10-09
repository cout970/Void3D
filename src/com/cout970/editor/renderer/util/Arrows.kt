package com.cout970.editor.renderer.util

import com.cout970.gl.model.IVAO
import com.cout970.gl.tesellator.Tessellator
import com.cout970.gl.util.vector.Vector3
import org.lwjgl.opengl.GL11

/**
 * Created by cout970 on 01/07/2016.
 */

fun createArrowsModel(): IVAO {
    val tes = Tessellator()

    tes.begin(GL11.GL_QUADS)
    tessellateArrowsModel(tes)
    tes.end()
    return tes.data
}

fun tessellateArrowsModel(tes: Tessellator) {
    val size = 0.07
    tes.glTexCoord2f(0f, 0f)
    CubeRenderer(Vector3(-size, -size, -size), Vector3(size + 2.5, size, size), false).tessellate(tes)
    tes.glTexCoord2f(1.001f / 4f, 0f)
    CubeRenderer(Vector3(-size, -size, -size), Vector3(size, size + 2.5, size), false).tessellate(tes)
    tes.glTexCoord2f(2.0001f / 4f, 0f)
    CubeRenderer(Vector3(-size, -size, -size), Vector3(size, size, size + 2.5), false).tessellate(tes)
}
