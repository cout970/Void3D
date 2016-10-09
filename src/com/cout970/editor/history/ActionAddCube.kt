package com.cout970.editor.history

import com.cout970.editor.Editor
import com.cout970.editor.modeltree.Cube
import com.cout970.editor.modeltree.Group

/**
 * Created by cout970 on 14/06/2016.
 */
class ActionAddCube(val cube : Cube, val group: Group) :IAction {

    override fun doAction() {
        Editor.modelTree.addPart(cube, group)
    }

    override fun undoAction() {
        Editor.modelTree.removePart(cube, group)
    }
}