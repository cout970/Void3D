package com.cout970.editor.renderer

import com.cout970.gl.matrix.IMatrixShader
import com.cout970.gl.shader.AbstractShaderProgram
import com.cout970.gl.shader.UniformVariable
import org.joml.Matrix4f

/**
 * Created by cout970 on 26/06/2016.
 */
class SpaceShader2(mainVertex: String, mainFragment: String) : AbstractShaderProgram(mainVertex, mainFragment), IMatrixShader {

    lateinit var modelMatrix: UniformVariable

    override fun bindAttributes() {
        bindAttribute(0, "in_position")
        bindAttribute(1, "in_texture")
    }

    override fun getUniformLocations() {
        modelMatrix = getUniformLocation("transformationMatrix")
    }

    override fun loadModelMatrix(matrix: Matrix4f?) {
        modelMatrix.setMatrix4(matrix)
    }

    override fun loadViewMatrix(matrix: Matrix4f?) {

    }

    override fun loadProjectionMatrix(matrix: Matrix4f?) {
    }
}