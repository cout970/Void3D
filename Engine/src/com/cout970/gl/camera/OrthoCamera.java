package com.cout970.gl.camera;

import com.cout970.gl.window.Window;
import org.joml.Matrix4f;

/**
 * Created by cout970 on 01/05/2016.
 */
public class OrthoCamera extends Camera {

    private float left;
    private float right;
    private float bottom;
    private float top;
    private float zNear;
    private float zFar;

    public OrthoCamera(float left, float right, float bottom, float top, float zNear, float zFar) {
        this.left = left;
        this.right = right;
        this.bottom = bottom;
        this.top = top;
        this.zNear = zNear;
        this.zFar = zFar;
    }

    @Override
    public Matrix4f createProjectionMatrix(Window window) {
        Matrix4f matrix = new Matrix4f();
        matrix.setOrtho(left, right, bottom, top, zNear, zFar);
        return matrix;
    }
}
