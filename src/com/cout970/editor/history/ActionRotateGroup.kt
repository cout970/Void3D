package com.cout970.editor.history

import com.cout970.editor.Editor
import com.cout970.editor.modeltree.Group
import com.cout970.gl.util.vector.Vector3

/**
 * Created by cout970 on 14/06/2016.
 */
class ActionRotateGroup(val group: Group, val rot: Vector3) : IAction {

    val oldRotation = group.rotation.copy()

    override fun doAction() {
        group.rotation.set(rot)
        group.update()
        group.needUpdate = true
        Editor.modelRenderer.requestUpdate()
    }

    override fun undoAction() {
        group.rotation.set(oldRotation)
        group.update()
        group.needUpdate = true
        Editor.modelRenderer.requestUpdate()
    }
}