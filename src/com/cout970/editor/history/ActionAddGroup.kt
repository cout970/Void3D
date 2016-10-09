package com.cout970.editor.history

import com.cout970.editor.Editor
import com.cout970.editor.modeltree.Group

/**
 * Created by cout970 on 14/06/2016.
 */
class ActionAddGroup(val group: Group, val parent: Group) :IAction {

    override fun doAction() {
        Editor.modelTree.addPart(group, parent)
    }

    override fun undoAction() {
        Editor.modelTree.removePart(group, parent)
    }
}