package com.cout970.editor.history

import com.cout970.editor.Editor
import com.cout970.editor.modeltree.Cube
import com.cout970.gl.util.vector.Vector3

/**
 * Created by cout970 on 14/06/2016.
 */
class ActionResizeCube(val cube: Cube, val size: Vector3) :IAction {

    val oldSize = cube.size.copy()

    override fun doAction() {
        cube.size.set(size)
        cube.update()
        cube.parent!!.needUpdate = true
        Editor.modelRenderer.requestUpdate()
    }

    override fun undoAction() {
        cube.size.set(oldSize)
        cube.update()
        cube.parent!!.needUpdate = true
        Editor.modelRenderer.requestUpdate()
    }
}