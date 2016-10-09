package com.cout970.editor.modeltree

import com.cout970.editor.Editor
import com.cout970.editor.renderer.util.Quad
import com.cout970.editor.renderer.util.Vertex
import com.cout970.editor.util.transform
import com.cout970.gl.raytrace.IRayObstacle
import com.cout970.gl.raytrace.Ray
import com.cout970.gl.raytrace.RayTraceResult
import com.cout970.gl.raytrace.RayTraceUtil
import com.cout970.gl.util.Direction
import com.cout970.gl.util.vector.Vector2
import com.cout970.gl.util.vector.Vector3
import java.util.*
import javax.swing.tree.TreeNode

/**
 * Created by cout970 on 06/06/2016.
 */

class Cube : AbstractTreeNode(), IRayObstacle {

    companion object{
        var id = 0
    }

    val quadList = mutableListOf<Quad>()

    var name_ = ""
    val size = Vector3(1, 1, 1)
    val position = Vector3()
    val rotationPoint = Vector3()
    val rotation = Vector3()
    val textureOffset = Vector2()
    var flipUV = false

    init {
        name_ = "Cube$id"
        id++
        update()
    }

    override fun getQuads(): List<Quad> = quadList

    override fun copy(): AbstractTreeNode {
        val cube = Cube()

        cube.flipUV = flipUV
        cube.size.set(size)
        cube.position.set(position)
        cube.rotationPoint.set(rotationPoint)
        cube.rotation.set(rotation)
        cube.textureOffset.set(textureOffset)
        cube.parent = parent
        return cube
    }

    override fun update() {
        quadList.clear()

        val vertex1 = Vector3(0, 0, 0)
        val vertex2 = Vector3(1, 0, 0)
        val vertex3 = Vector3(1, 1, 0)
        val vertex4 = Vector3(0, 1, 0)
        val vertex5 = Vector3(0, 0, 1)
        val vertex6 = Vector3(1, 0, 1)
        val vertex7 = Vector3(1, 1, 1)
        val vertex8 = Vector3(0, 1, 1)

        val width = size.x
        val height = size.y
        val length = size.z

        val offsetX = textureOffset.x
        val offsetY = textureOffset.y

        val cube0 = mutableListOf<Quad>()

        cube0.add(createQuad(Direction.DOWN, vertex6.copy(), vertex5.copy(), vertex1.copy(), vertex2.copy(),
                offsetX + length + width,
                offsetY + length,
                offsetX + length + width + width,
                offsetY))

        cube0.add(createQuad(Direction.UP, vertex3.copy(), vertex4.copy(), vertex8.copy(), vertex7.copy(),
                offsetX + length,
                offsetY,
                offsetX + length + width,
                offsetY + length))

        cube0.add(createQuad(Direction.NORTH, vertex2.copy(), vertex1.copy(), vertex4.copy(), vertex3.copy(),
                offsetX + length,
                offsetY + length,
                offsetX + length + width,
                offsetY + length + height))

        cube0.add(createQuad(Direction.SOUTH, vertex5.copy(), vertex6.copy(), vertex7.copy(), vertex8.copy(),
                offsetX + length + width + length,
                offsetY + length,
                offsetX + length + width + length + width,
                offsetY + length + height))

        cube0.add(createQuad(Direction.WEST, vertex6.copy(), vertex2.copy(), vertex3.copy(), vertex7.copy(),
                offsetX + length + width,
                offsetY + length,
                offsetX + length + width + length,
                offsetY + length + height))

        cube0.add(createQuad(Direction.EAST, vertex1.copy(), vertex5.copy(), vertex8.copy(), vertex4.copy(),
                offsetX,
                offsetY + length,
                offsetX + length,
                offsetY + length + height))


        for (i in cube0) {
            val j = if (flipUV) i.flipUV() else i
            val t = j.scale(size).translate(position).rotate(rotationPoint.copy(), rotation.copy().toRadians())
            quadList.add(t)
        }
    }


    fun createQuad(dir: Direction, a: Vector3, b: Vector3, c: Vector3, d: Vector3,
                   u2: Double, v2: Double, u: Double, v: Double): Quad {

        return Quad(
                Vertex(a, Vector2(u2, v), dir.toVector3()),
                Vertex(b, Vector2(u, v), dir.toVector3()),
                Vertex(c, Vector2(u, v2), dir.toVector3()),
                Vertex(d, Vector2(u2, v2), dir.toVector3())
        )
    }

    override fun getName(): String = name_

    override fun children(): Enumeration<*>? = Collections.emptyEnumeration<Any>()

    override fun getChildAt(childIndex: Int): TreeNode? {
        throw UnsupportedOperationException()
    }

    override fun getIndex(node: TreeNode?): Int {
        throw UnsupportedOperationException()
    }

    override fun getAllowsChildren(): Boolean = false

    override fun isLeaf(): Boolean = true

    override fun getChildCount(): Int = 0

    override fun setUserObject(`object`: Any?) {
        name_ = `object`.toString()
        Editor.modelTree.selectPart(this)
    }

    override fun rayTrace(ray: Ray?): RayTraceResult? {
        ray!!

        var result: RayTraceResult? = null
        var min = 0.0
        val quads = transform(getQuads(), parent!!.cacheMatrix)
        for (i in quads) {
            var r: RayTraceResult?
            r = RayTraceUtil.rayTraceQuad(ray, this, i.a.pos, i.b.pos, i.c.pos, i.d.pos)
            if (r != null) {
                if (result != null) {
                    val distance = ray.start.distance(r.hit)
                    if (distance < min) {
                        min = distance
                        result = r
                    }
                } else {
                    min = ray.start.distance(r.hit)
                    result = r
                }
            }
        }

        return result
    }
}
