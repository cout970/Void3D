package com.cout970.gl.matrix;

import com.cout970.gl.util.vector.Vector3;
import org.joml.Matrix4f;

/**
 * Created by cout970 on 04/06/2016.
 */
public class MainMatrixHandler {

    public static final int MODEL_MATRIX = 0;
    public static final int VIEW_MATRIX = 1;
    public static final int PROJECTION_MATRIX = 2;

    private static MatrixHandler model = new MatrixHandler();//or transformation matrix
    private static MatrixHandler view = new MatrixHandler();
    private static MatrixHandler projection = new MatrixHandler();
    private static MatrixHandler current = model;

    private static boolean changed = true;

    private MainMatrixHandler() {}

    public static void matrixMode(int mode) {
        switch (mode) {
            case MODEL_MATRIX:
                current = model;
                break;
            case VIEW_MATRIX:
                current = view;
                break;
            case PROJECTION_MATRIX:
                current = projection;
                break;
            default:
                throw new IllegalArgumentException("Invalid Matrix mode: " + mode);
        }
    }

    public static void pushMatrix() {
        current.pushMatrix();
    }

    public static void popMatrix() {
        current.popMatrix();
        changed = true;
    }

    public static void translate(Vector3 vec) {
        current.translate(vec);
        changed = true;
    }

    public static void rotate(float angle, Vector3 vec) {
        current.rotate(angle, vec);
        changed = true;
    }

    public static void scale(Vector3 vec) {
        current.scale(vec);
        changed = true;
    }

    public static void setIdentity() {
        current.setIdentity();
        changed = true;
    }

    public static Matrix4f getMatrix() {
        return current.getMatrix();
    }

    public static void setMatrix(Matrix4f currentMatrix) {
        current.setMatrix(currentMatrix);
        changed = true;
    }

    public static MatrixHandler getModel() {
        return model;
    }

    public static MatrixHandler getView() {
        return view;
    }

    public static MatrixHandler getProjection() {
        return projection;
    }

    public static void loadChangedMatrix(IMatrixShader shader) {
        if (changed) {
            loadMatrix(shader);
            changed = false;
        }
    }

    public static void loadMatrix(IMatrixShader shader) {
        shader.loadModelMatrix(model.getMatrix());
        shader.loadViewMatrix(view.getMatrix());
        shader.loadProjectionMatrix(projection.getMatrix());
    }
}
