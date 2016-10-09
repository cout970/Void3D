package com.cout970.editor.history

import com.cout970.editor.Editor
import com.cout970.editor.modeltree.Cube

/**
 * Created by cout970 on 14/06/2016.
 */
class ActionFlipCubeUV(val cube: Cube, val flip: Boolean) : IAction {

    val oldFlip = cube.flipUV

    override fun doAction() {
        cube.flipUV = flip
        cube.update()
        cube.parent!!.needUpdate = true
        Editor.modelRenderer.requestUpdate()
    }

    override fun undoAction() {
        cube.flipUV = oldFlip
        cube.update()
        cube.parent!!.needUpdate = true
        Editor.modelRenderer.requestUpdate()
    }
}