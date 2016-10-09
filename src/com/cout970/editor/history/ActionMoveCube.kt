package com.cout970.editor.history

import com.cout970.editor.Editor
import com.cout970.editor.modeltree.Cube
import com.cout970.gl.util.vector.Vector3

/**
 * Created by cout970 on 14/06/2016.
 */
class ActionMoveCube(val cube: Cube, val pos: Vector3) :IAction {

    val oldPos = cube.position.copy()

    override fun doAction() {
        cube.position.set(pos)
        cube.update()
        cube.parent!!.needUpdate = true
        Editor.modelRenderer.requestUpdate()
    }

    override fun undoAction() {
        cube.position.set(oldPos)
        cube.update()
        cube.parent!!.needUpdate = true
        Editor.modelRenderer.requestUpdate()
    }
}