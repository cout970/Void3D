package com.cout970.editor.history

import com.cout970.editor.Editor
import com.cout970.editor.modeltree.AbstractTreeNode
import com.cout970.editor.modeltree.Cube
import com.cout970.editor.modeltree.Group

/**
 * Created by cout970 on 05/07/2016.
 */
class ActionPaste(node: AbstractTreeNode) : IAction {

    val subActions = mutableListOf<IAction>()

    init {
        if (node is Cube) {
            subActions.add(ActionAddCube(node, node.parent!!))
        } else if (node is Group) {
            val parent = node.parent ?: Editor.modelTree.root
            subActions.add(ActionAddGroup(node, parent))
        }
    }

    override fun doAction() {
        subActions.forEach { it.doAction() }
    }

    override fun undoAction() {
        subActions.forEach { it.undoAction() }
    }
}