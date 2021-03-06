package com.cout970.editor.history

import com.cout970.editor.Editor
import com.cout970.editor.modeltree.Group
import com.cout970.gl.util.vector.Vector3

/**
 * Created by cout970 on 14/06/2016.
 */
class ActionMoveGroup(val group: Group, val pos: Vector3) :IAction {

    val oldPos = group.position.copy()

    override fun doAction() {
        group.position.set(pos)
        group.update()
        group.needUpdate = true
        Editor.modelRenderer.requestUpdate()
    }

    override fun undoAction() {
        group.position.set(oldPos)
        group.update()
        group.needUpdate = true
        Editor.modelRenderer.requestUpdate()
    }
}