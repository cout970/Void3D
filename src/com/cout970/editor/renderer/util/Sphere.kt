package com.cout970.editor.renderer.util

import com.cout970.gl.model.IVAO
import com.cout970.gl.tesellator.ITessellator
import com.cout970.gl.tesellator.Tessellator
import com.cout970.gl.util.vector.Vector3
import org.lwjgl.opengl.GL11.GL_QUADS
import java.util.*

/**
 * Created by cout970 on 01/07/2016.
 */

fun createSphereVao(): IVAO {
    val tes = Tessellator()

    tes.begin(GL_QUADS)
    tessellateSphere(tes)
    tes.end()
    return tes.data
}

fun tessellateSphere(tes: ITessellator) {

    val radius = 0.5
    val faces = LinkedList<Face>()

    val increment = 0.1
    val vertex = Array(4, { Vector3() })

    var j = -Math.PI
    while (j < Math.PI) {
        var i = -Math.PI / 2
        while (i < Math.PI / 2) {

            vertex[3] = Vector3(
                    radius * Math.cos(i) * Math.cos(j),
                    radius * Math.cos(i) * Math.sin(j),
                    radius * Math.sin(i))
            vertex[2] = Vector3(
                    radius * Math.cos(i + increment) * Math.cos(j),
                    radius * Math.cos(i + increment) * Math.sin(j),
                    radius * Math.sin(i + increment))
            vertex[1] = Vector3(
                    radius * Math.cos(i + increment) * Math.cos(j + increment),
                    radius * Math.cos(i + increment) * Math.sin(j + increment),
                    radius * Math.sin(i + increment))
            vertex[0] = Vector3(
                    radius * Math.cos(i) * Math.cos(j + increment),
                    radius * Math.cos(i) * Math.sin(j + increment),
                    radius * Math.sin(i))

            faces.add(Face(vertex[0], vertex[1], vertex[2], vertex[3]))
            i += increment
        }
        j += increment
    }

    for (cara in faces) {
        var v = cara.a
        val n = cara.normal
        tes.glNormal3f(n.xf, n.yf, n.zf)
        tes.glVertex3f(v.xf, v.yf, v.zf).endVertex()
        v = cara.b
        tes.glNormal3f(n.xf, n.yf, n.zf)
        tes.glVertex3f(v.xf, v.yf, v.zf).endVertex()
        v = cara.c
        tes.glNormal3f(n.xf, n.yf, n.zf)
        tes.glVertex3f(v.xf, v.yf, v.zf).endVertex()
        v = cara.d
        tes.glNormal3f(n.xf, n.yf, n.zf)
        tes.glVertex3f(v.xf, v.yf, v.zf).endVertex()
    }
}

private class Face(val a: Vector3, val b: Vector3, val c: Vector3, val d: Vector3) {

    val normal: Vector3
        get() = a.copy().sub(b).cross(c.copy().sub(b))
}