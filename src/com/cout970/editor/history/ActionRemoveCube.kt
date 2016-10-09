package com.cout970.editor.history

import com.cout970.editor.Editor
import com.cout970.editor.modeltree.Cube

/**
 * Created by cout970 on 14/06/2016.
 */
class ActionRemoveCube(val cube: Cube) :IAction {

    val parent = cube.parent

    override fun doAction() {
        if(parent == null)
            throw IllegalStateException("cube.parent cannot be null: $cube")
        Editor.modelTree.removePart(cube, parent)
    }

    override fun undoAction() {
        Editor.modelTree.addPart(cube, parent!!)
    }

    override fun redoAction() {
        Editor.modelTree.removePart(cube, parent!!)
    }
}