package com.cout970.editor.renderer.util

import com.cout970.gl.tesellator.ITessellable
import com.cout970.gl.tesellator.ITessellator
import com.cout970.gl.util.vector.Vector2
import com.cout970.gl.util.vector.Vector3
import org.joml.Matrix4f
import org.joml.Vector4f

/**
 * Created by cout970 on 30/05/2016.
 */


class Quad(
        val a: Vertex,
        val b: Vertex,
        val c: Vertex,
        val d: Vertex
) : ITessellable {

    override fun tessellate(tes: ITessellator?) {
        tes!!

        val scale = 1.0

        tes.glTexCoord2(a.uv.copy().mul(scale))
        tes.glNormal3(a.normal)
        tes.glVertex3(a.pos)
        tes.endVertex()

        tes.glTexCoord2(b.uv.copy().mul(scale))
        tes.glNormal3(b.normal)
        tes.glVertex3(b.pos)
        tes.endVertex()

        tes.glTexCoord2(c.uv.copy().mul(scale))
        tes.glNormal3(c.normal)
        tes.glVertex3(c.pos)
        tes.endVertex()

        tes.glTexCoord2(d.uv.copy().mul(scale))
        tes.glNormal3(d.normal)
        tes.glVertex3(d.pos)
        tes.endVertex()
    }

    fun copy(): Quad {
        return Quad(a, b, c, d)
    }

    fun scale(size: Vector3): Quad = Quad(a.scale(size), b.scale(size), c.scale(size), d.scale(size))

    fun translate(position: Vector3): Quad {
        return Quad(a.translate(position), b.translate(position), c.translate(position), d.translate(position))
    }

    fun rotate(rp: Vector3, rot: Vector3): Quad {
        return Quad(a.rotate(rp, rot), b.rotate(rp, rot), c.rotate(rp, rot), d.rotate(rp, rot))
    }

    fun getVertex(): List<Vertex> = listOf(a, b, c, d)

    fun flipUV(): Quad {
        return Quad(
                Vertex(a.pos.copy(), Vector2(b.uv.x, a.uv.y), a.normal.copy()),
                Vertex(b.pos.copy(), Vector2(a.uv.x, b.uv.y), b.normal.copy()),
                Vertex(c.pos.copy(), Vector2(d.uv.x, c.uv.y), c.normal.copy()),
                Vertex(d.pos.copy(), Vector2(c.uv.x, d.uv.y), d.normal.copy())
        )
    }

}

class Vertex(
        val pos: Vector3,
        val uv: Vector2,
        val normal: Vector3
) {
    fun transform(matrix: Matrix4f): Vertex {
        val h = Vector4f(pos.xf, pos.yf, pos.zf, 1f).mul(matrix)
        return Vertex(Vector3(h.x, h.y, h.z), uv, normal)
    }

    fun scale(size: Vector3): Vertex = Vertex(pos.copy().mul(size), uv, normal)

    fun translate(position: Vector3): Vertex = Vertex(pos.copy().add(position), uv, normal)

    fun rotate(rp: Vector3, rot: Vector3): Vertex {
        val p = pos.copy()
        p.sub(rp)
        p.rotateX(rot.x)
        p.rotateY(rot.y)
        p.rotateZ(rot.z)
        p.add(rp)
        return Vertex(p, uv, normal)
    }
}