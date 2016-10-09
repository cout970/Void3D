package com.cout970.editor.renderer

import com.cout970.editor.modeltree.Cube
import com.cout970.editor.renderer.util.CubeRenderer
import com.cout970.gl.matrix.IMatrixShader
import com.cout970.gl.matrix.MainMatrixHandler.*
import com.cout970.gl.util.vector.Vector3
import org.lwjgl.opengl.GL11.*

object SelectionRenderer {

    var cache = Vector3(-1, -1, -1)
    var list = -1

    fun line(a: Vector3, b: Vector3) {
        CubeRenderer(a.copy().add(Vector3(1, 1, 1).mul(-1 / 16f)),
                b.copy().add(Vector3(1, 1, 1).mul(1 / 16f))).render()
    }

    fun render(cube: Cube, shader: IMatrixShader) {
        pushMatrix()

        getModel().matrix = cube.getMatrix()

        translate(Vector3(cube.rotationPoint.x, cube.rotationPoint.y, cube.rotationPoint.z))
        val rot = cube.rotation.copy().toRadians()
        rotate(rot.zf, Vector3(0.0, 0.0, 1.0))
        rotate(rot.yf, Vector3(0.0, 1.0, 0.0))
        rotate(rot.xf, Vector3(1.0, 0.0, 0.0))
        translate(Vector3(-cube.rotationPoint.x, -cube.rotationPoint.y, -cube.rotationPoint.z))

        translate(Vector3(cube.position.x, cube.position.y, cube.position.z))
        loadMatrix(shader)
        cubeSelection(cube.size)
        popMatrix()
    }

    fun cubeSelection(size: Vector3) {

        if (size != cache) {
            cache.set(size)
            if (list != -1) {
                glDeleteLists(list, 1)
            }
            list = -1
        }
        if (list != -1) {
            glCallList(list)
            return
        }
        val start = Vector3()
        val vertex1 = Vector3(start.x, start.y, start.z)
        val vertex2 = Vector3(size.x, start.y, start.z)
        val vertex3 = Vector3(size.x, size.y, start.z)
        val vertex4 = Vector3(start.x, size.y, start.z)
        val vertex5 = Vector3(start.x, start.y, size.z)
        val vertex6 = Vector3(size.x, start.y, size.z)
        val vertex7 = Vector3(size.x, size.y, size.z)
        val vertex8 = Vector3(start.x, size.y, size.z)

        val down = arrayOf<Vector3>(vertex6.copy(), vertex5.copy(), vertex1.copy(), vertex2.copy())
        val up = arrayOf<Vector3>(vertex3.copy(), vertex4.copy(), vertex8.copy(), vertex7.copy())
        val north = arrayOf<Vector3>(vertex2.copy(), vertex1.copy(), vertex4.copy(), vertex3.copy())
        val south = arrayOf<Vector3>(vertex5.copy(), vertex6.copy(), vertex7.copy(), vertex8.copy())
        val west = arrayOf<Vector3>(vertex6.copy(), vertex2.copy(), vertex3.copy(), vertex7.copy())
        val east = arrayOf<Vector3>(vertex1.copy(), vertex5.copy(), vertex8.copy(), vertex4.copy())

        val quads = arrayOf(down, up, north, south, west, east)

        list = glGenLists(1)
        glNewList(list, GL_COMPILE)
        for (quad in quads) {
            for (i in 0..3) {
                line(quad[i], quad[(i + 1) % 4])
            }
        }
        glEndList()
    }
}