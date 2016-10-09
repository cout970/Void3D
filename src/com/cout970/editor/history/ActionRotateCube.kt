package com.cout970.editor.history

import com.cout970.editor.Editor
import com.cout970.editor.modeltree.Cube
import com.cout970.gl.util.vector.Vector3

/**
 * Created by cout970 on 14/06/2016.
 */
class ActionRotateCube(val cube: Cube, val rot:Vector3) : IAction {

    val oldRotation = cube.rotation.copy()

    override fun doAction() {
        cube.rotation.set(rot)
        cube.update()
        cube.parent!!.needUpdate = true
        Editor.modelRenderer.requestUpdate()
    }

    override fun undoAction() {
        cube.rotation.set(oldRotation)
        cube.update()
        cube.parent!!.needUpdate = true
        Editor.modelRenderer.requestUpdate()
    }
}