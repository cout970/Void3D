package com.cout970.editor.renderer

import com.cout970.editor.core.MainWindow
import com.cout970.gl.shader.AbstractShaderProgram
import com.cout970.gl.shader.UniformVariable

/**
 * Created by cout970 on 14/06/2016.
 */
class GuiShader(mainVertex: String, mainFragment: String) : AbstractShaderProgram(mainVertex, mainFragment) {

    lateinit var sizeX: UniformVariable
    lateinit var sizeY: UniformVariable

    override fun bindAttributes() {
        bindAttribute(0, "in_position")
        bindAttribute(1, "in_texture")
    }

    override fun getUniformLocations() {
        sizeX = getUniformLocation("sizex")
        sizeY = getUniformLocation("sizey")
    }

    fun setSize(window: MainWindow) {
        sizeX.setFloat(window.size.xf)
        sizeY.setFloat(window.size.yf)
    }
}