package com.cout970.editor.modeltree

import com.cout970.editor.Editor
import com.cout970.editor.gui.WindowEdit
import com.cout970.editor.gui.WindowModelTree
import com.cout970.gl.util.vector.Vector3
import java.util.*
import java.util.function.Function
import java.util.function.Predicate
import javax.swing.tree.DefaultTreeModel

/**
 * Created by cout970 on 30/05/2016.
 */
class ModelTree {

    val root = Group()
    var selectedPart: AbstractTreeNode? = null
    var treeWrapper = DefaultTreeModel(root, true)
    var selectedGroup = root
    var clipboard: AbstractTreeNode? = null

    init {
        root.name_ = "Root"
    }

    fun addPart(p: AbstractTreeNode, group: Group) {
        group.subParts.add(p)
        p.parent = group
        selectPart(p)
        treeWrapper.insertNodeInto(p, group, group.subParts.size - 1)
        WindowModelTree.update(this)
        group.needUpdate = true
        Editor.modelRenderer.requestUpdate()
    }

    fun removePart(p: AbstractTreeNode, group: Group) {
        selectPart(null)
        treeWrapper.removeNodeFromParent(p)
        group.subParts.remove(p)
        WindowModelTree.update(this)
        group.needUpdate = true
        Editor.modelRenderer.requestUpdate()
    }

    fun iterate(func: Function<AbstractTreeNode, Unit>, filter: Predicate<AbstractTreeNode>) {
        val stack = LinkedList<AbstractTreeNode>()
        root.subParts.forEach { stack.push(it); }

        while (!stack.isEmpty()) {
            val a = stack.pop()
            if (filter.test(a)) {
                if (a is Group) {
                    a.subParts.forEach { stack.push(it) }
                } else {
                    func.apply(a)
                }
            }
        }
    }

    fun selectPart(model: AbstractTreeNode?) {
        selectedPart = model
        WindowModelTree.control_!!.select(model)
        if (model == null) {
            WindowEdit.unSelectCube()
        } else {
            if (model is Cube) {
                WindowEdit.selectCube(model)
            } else if (model is Group) {
                WindowEdit.selectGroup(model)
            }
        }
    }

    fun updateAll() {
        iterate(Function { it.update(); if (it is Group) it.needUpdate = true }, Predicate { true })
    }

    fun reset() {
        for (i in ArrayList(root.subParts)) {
            removePart(i, root)
        }
        root.name_ = "Root"
        root.vao = null
        root.parent = null
        root.position.set(Vector3())
        root.size.set(Vector3(1, 1, 1))
        root.rotation.set(Vector3())
        root.rotationPoint.set(Vector3())
        root.needUpdate = true
    }

    fun loadModel(tree: AbstractTreeNode) {
        reset()
        if (tree is Group) {
            root.position.set(tree.position)
            root.size.set(tree.size)
            root.rotation.set(tree.rotation)
            root.rotationPoint.set(tree.rotationPoint)
            for (i in tree.subParts) {
                addPart(i, root)
            }
        } else {
            addPart(tree, root)
        }
        root.needUpdate = true
        updateAll()
        Editor.modelRenderer.requestUpdate()
    }
}