package com.cout970.editor.history

import com.cout970.editor.Editor
import com.cout970.editor.modeltree.Cube
import com.cout970.gl.util.vector.Vector2

/**
 * Created by cout970 on 14/06/2016.
 */
class ActionChangeCubeTextureOffset(val cube: Cube, val offset: Vector2) :IAction{

    val oldOffset = cube.textureOffset.copy()

    override fun doAction() {
        cube.textureOffset.set(offset)
        cube.update()
        cube.parent!!.needUpdate = true
        Editor.modelRenderer.requestUpdate()
    }

    override fun undoAction() {
        cube.textureOffset.set(oldOffset)
        cube.update()
        cube.parent!!.needUpdate = true
        Editor.modelRenderer.requestUpdate()
    }
}