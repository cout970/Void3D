package com.cout970.gl.camera;

import com.cout970.gl.window.Window;
import org.joml.Matrix4f;

/**
 * Created by cout970 on 01/05/2016.
 */
public class PerspectiveCamera extends Camera {

    private float fov;
    private float nearPlane;
    private float farPlane;

    public PerspectiveCamera(float fov, float nearPlane, float farPlane) {
        this.fov = fov;
        this.nearPlane = nearPlane;
        this.farPlane = farPlane;
    }

    public float getFov() {
        return fov;
    }

    public float getNearPlane() {
        return nearPlane;
    }

    public float getFarPlane() {
        return farPlane;
    }

    @Override
    public Matrix4f createProjectionMatrix(Window window) {
        Matrix4f matrix = new Matrix4f();
        matrix.setPerspective(getFov(), window.getSize().getXf() / window.getSize().getYf(),
                getNearPlane(), getFarPlane());
        return matrix;
    }
}
