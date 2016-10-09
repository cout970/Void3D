package com.cout970.editor.renderer

import com.cout970.gl.matrix.IMatrixShader
import com.cout970.gl.shader.AbstractShaderProgram
import com.cout970.gl.shader.UniformVariable
import org.joml.Matrix4f

/**
 * Created by cout970 on 14/06/2016.
 */
class SkyboxShader(mainVertex: String, mainFragment: String) : AbstractShaderProgram(mainVertex, mainFragment), IMatrixShader {

    private var projectionMatrix: UniformVariable? = null
    private var viewMatrix: UniformVariable? = null

    override fun bindAttributes() {
        bindAttribute(0, "in_position")
    }

    override fun getUniformLocations() {
        viewMatrix = getUniformLocation("view")
        projectionMatrix = getUniformLocation("projection")
    }

    override fun loadViewMatrix(matrix: Matrix4f) {
        val newMatrix = Matrix4f(matrix)
        newMatrix.m30 = 0f
        newMatrix.m31 = 0f
        newMatrix.m32 = 0f
        viewMatrix?.setMatrix4(newMatrix)
    }

    override fun loadProjectionMatrix(matrix: Matrix4f) {
        projectionMatrix?.setMatrix4(matrix)
    }

    override fun loadModelMatrix(matrix: Matrix4f?) {

    }
}