package com.cout970.editor.modeltree

import com.cout970.editor.Editor
import com.cout970.editor.renderer.util.Quad
import com.cout970.editor.util.IteratorToEnumeration
import com.cout970.gl.model.ModelVAO
import com.cout970.gl.util.vector.Vector3
import org.joml.Matrix4f
import java.util.*
import javax.swing.tree.TreeNode

/**
 * Created by cout970 on 06/06/2016.
 */
class Group : AbstractTreeNode() {

    var vao: ModelVAO? = null
    var cacheMatrix: Matrix4f = Matrix4f().identity()
    val subParts = mutableListOf<AbstractTreeNode>()
    var needUpdate = true

    var name_ = "Group"
    val size = Vector3(1, 1, 1)
    val position = Vector3()
    val rotationPoint = Vector3()
    val rotation = Vector3()

    override fun copy(): AbstractTreeNode {
        val group = Group()
        group.name_ = group.name_
        group.cacheMatrix = Matrix4f(group.cacheMatrix)
        group.size.set(size)
        group.position.set(position)
        group.rotationPoint.set(rotationPoint)
        group.rotation.set(rotation)
        group.parent = parent
        for(i in subParts){
            val cpy = i.copy()
            group.subParts.add(cpy)
            cpy.parent = group
        }
        return group
    }

    fun getTransformationMatrix(): Matrix4f {
        val matrix = Matrix4f().identity()

        matrix.translate(rotationPoint.xf, rotationPoint.yf, rotationPoint.zf)
        matrix.rotate(Math.toRadians(rotation.x).toFloat(), 1f, 0f, 0f)
        matrix.rotate(Math.toRadians(rotation.y).toFloat(), 0f, 1f, 0f)
        matrix.rotate(Math.toRadians(rotation.z).toFloat(), 0f, 0f, 1f)
        matrix.translate(-rotationPoint.xf, -rotationPoint.yf, -rotationPoint.zf)
        matrix.translate(position.xf, position.yf, position.zf)
        matrix.scale(size.xf, size.yf, size.zf)
        return matrix
    }

    override fun getQuads(): List<Quad> {
        val list = ArrayList<Quad>()
        subParts.forEach { list.addAll(it.getQuads()) }
        return list
    }

    override fun getName(): String = name_

    override fun children(): Enumeration<*>? {
        return IteratorToEnumeration(subParts.iterator())
    }

    override fun getChildAt(childIndex: Int): TreeNode? = subParts[childIndex]

    override fun getIndex(node: TreeNode?): Int {
        return subParts.indexOf(node)
    }

    override fun getAllowsChildren(): Boolean = true

    override fun isLeaf(): Boolean = false

    override fun getChildCount(): Int = subParts.size

    override fun update() {
        for (i in subParts) i.update()
    }

    override fun setUserObject(`object`: Any?) {
        if (this != Editor.modelTree.root) {
            name_ = `object`.toString()
        }
    }
}