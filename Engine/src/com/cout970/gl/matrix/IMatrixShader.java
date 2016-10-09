package com.cout970.gl.matrix;

import org.joml.Matrix4f;

/**
 * Created by cout970 on 04/06/2016.
 */
public interface IMatrixShader {

    void loadModelMatrix(Matrix4f matrix);

    void loadViewMatrix(Matrix4f matrix);

    void loadProjectionMatrix(Matrix4f matrix);
}
