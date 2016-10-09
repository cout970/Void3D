package com.cout970.editor.history

import com.cout970.editor.Editor
import com.cout970.editor.modeltree.Group
import com.cout970.gl.util.vector.Vector3

/**
 * Created by cout970 on 14/06/2016.
 */
class ActionResizeGroup(val group: Group, val size: Vector3) :IAction {

    val oldSize = group.size.copy()

    override fun doAction() {
        group.size.set(size)
        group.update()
        group.needUpdate = true
        Editor.modelRenderer.requestUpdate()
    }

    override fun undoAction() {
        group.size.set(oldSize)
        group.update()
        group.needUpdate = true
        Editor.modelRenderer.requestUpdate()
    }
}