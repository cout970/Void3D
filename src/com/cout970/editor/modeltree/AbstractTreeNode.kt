package com.cout970.editor.modeltree

import com.cout970.editor.renderer.util.Quad
import org.joml.Matrix4f
import javax.swing.tree.MutableTreeNode
import javax.swing.tree.TreeNode


/**
 * Created by cout970 on 06/06/2016.
 */
abstract class AbstractTreeNode : TreeNode, MutableTreeNode {

    var visible = true
    var parent: Group? = null

    abstract fun getQuads(): List<Quad>

    abstract fun copy(): AbstractTreeNode

    abstract fun getName(): String

    override fun toString(): String = getName()

    override fun getParent(): TreeNode? = parent

    abstract fun update()

    override fun insert(child: MutableTreeNode?, index: Int) {
    }

    override fun setParent(newParent: MutableTreeNode?) {
        parent = newParent as Group?
    }

    override fun remove(index: Int) {
    }

    override fun remove(node: MutableTreeNode?) {
    }

    override fun removeFromParent() {
    }

    fun getMatrix(): Matrix4f {
        var parent = parent
        var matrix: Matrix4f = Matrix4f().identity()

        while (parent != null) {
            matrix = parent.getTransformationMatrix().mul(matrix)
            parent = parent.parent
        }

        return matrix
    }
}