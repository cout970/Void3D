package com.cout970.editor.renderer

import com.cout970.gl.light.BasicLight
import com.cout970.gl.matrix.IMatrixShader
import com.cout970.gl.model.IMaterial
import com.cout970.gl.shader.AbstractShaderProgram
import com.cout970.gl.shader.UniformVariable
import org.joml.Matrix4f

class ModelShader(mainVertex: String, mainFragment: String) : AbstractShaderProgram(mainVertex, mainFragment), IMatrixShader {

    lateinit var projectionMatrix: UniformVariable
    lateinit var viewMatrix: UniformVariable
    lateinit var transformationMatrix: UniformVariable
    lateinit var lightPositionA: UniformVariable
    lateinit var lightColorA: UniformVariable
    lateinit var lightPositionB: UniformVariable
    lateinit var lightColorB: UniformVariable
    lateinit var shineDamper: UniformVariable
    lateinit var reflectivity: UniformVariable
    lateinit var enableLight: UniformVariable
    lateinit var textureSize: UniformVariable

    override fun bindAttributes() {
        bindAttribute(0, "in_position")
        bindAttribute(1, "in_texture")
        bindAttribute(2, "in_normal")
    }

    override fun getUniformLocations() {
        viewMatrix = getUniformLocation("viewMatrix")
        projectionMatrix = getUniformLocation("projectionMatrix")
        transformationMatrix = getUniformLocation("transformationMatrix")
        lightPositionA = getUniformLocation("lightPositionA")
        lightColorA = getUniformLocation("lightColorA")
        lightPositionB = getUniformLocation("lightPositionB")
        lightColorB = getUniformLocation("lightColorB")
        shineDamper = getUniformLocation("shineDamper")
        reflectivity = getUniformLocation("reflectivity")
        enableLight = getUniformLocation("enableLight")
        textureSize = getUniformLocation("textureSize")
    }

    fun loadMaterial(material: IMaterial) {
        shineDamper.setFloat(material.shineDamper)
        reflectivity.setFloat(material.reflectivity)
    }

    fun loadLightA(light: BasicLight) {
        lightPositionA.setVector3(light.position)
        lightColorA.setVector3(light.color)
    }

    fun loadLightB(light: BasicLight) {
        lightPositionB.setVector3(light.position)
        lightColorB.setVector3(light.color)
    }

    fun enableLight() {
        enableLight.setBoolean(true)
    }

    fun disableLight() {
        enableLight.setBoolean(false)
    }

    override fun loadModelMatrix(matrix: Matrix4f) {
        transformationMatrix.setMatrix4(matrix)
    }

    override fun loadViewMatrix(matrix: Matrix4f) {
        viewMatrix.setMatrix4(matrix)
    }

    override fun loadProjectionMatrix(matrix: Matrix4f) {
        projectionMatrix.setMatrix4(matrix)
    }

    fun loadTextureSize(textureScale: Double) {
        textureSize.setFloat(textureScale.toFloat())
    }
}