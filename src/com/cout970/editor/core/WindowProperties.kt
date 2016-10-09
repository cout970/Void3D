package com.cout970.editor.core

import com.cout970.editor.Editor
import com.cout970.gl.util.vector.Vector2
import com.cout970.gl.util.vector.Vector3
import com.cout970.gl.window.IWindowProperties

/**
 * Created by cout970 on 30/05/2016.
 */
object WindowProperties : IWindowProperties {

    override fun getBackground(): Vector3? = Vector3(0.35, 0.35, 0.35)

    override fun getSize(): Vector2? = Vector2(800, 600)

    override fun getTitle(): String? = Editor.PROJECT_NAME
}