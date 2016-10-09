package com.cout970.gl.window;

import com.cout970.gl.camera.Camera;
import com.cout970.gl.matrix.MatrixHandler;
import com.cout970.gl.shader.TemplateShader;
import com.cout970.gl.util.IClosable;
import com.cout970.gl.util.vector.Vector2;
import com.cout970.gl.util.vector.Vector3;
import org.joml.Matrix4f;

import static org.lwjgl.opengl.GL11.*;

/**
 * Created by cout970 on 27/04/2016.
 */
public class ViewContext implements IClosable {

    private Window window;
    private Camera camera;
    private TemplateShader mainShader;
    private Matrix4f projectionMatrix;
    private MatrixHandler transformationMatrixHandler;
    private MatrixHandler viewMatrixHandler;

    private boolean cameraMotion = true;
    private boolean cameraRotation = true;

    public ViewContext(Window w, Camera camera) {
        this.camera = camera;
        this.window = w;
        transformationMatrixHandler = new MatrixHandler();
        viewMatrixHandler = new MatrixHandler();
        updateProjectionMatrix();
        updateTransformationMatrix();
    }

    public void preTick() {
        glClearColor(0.5444f, 0.62f, 0.69f, 1.0f);
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
        glClearDepth(1.0);
        glEnable(GL_DEPTH_TEST);
        glEnable(GL_CULL_FACE);

        glViewport(0, 0, window.getSize().getXi(), window.getSize().getYi());

        updateProjectionMatrix();
        updateViewMatrix();
        updateTransformationMatrix();
    }

    public void activateShader() {
        mainShader.start();
        mainShader.setSkyColor(new Vector3(0.5444f, 0.62f, 0.69f));
        mainShader.loadProjectionMatrix(projectionMatrix);
        mainShader.loadViewMatrix(viewMatrixHandler.getMatrix());
        mainShader.loadTransformationMatrix(transformationMatrixHandler.getMatrix());
        mainShader.enableLight();
    }

    public void desactivateShader(){
        mainShader.stop();
    }

    public void postTick() {

    }

    private void updateTransformationMatrix() {
        transformationMatrixHandler.setIdentity();
    }

    private void updateViewMatrix() {
        Matrix4f matrix = viewMatrixHandler.getMatrix();
        matrix.identity();
        if (cameraRotation) {
            Vector2 rotation = camera.getRotation();
            matrix.rotate((float) Math.toRadians(rotation.getX()), 1, 0, 0);
            matrix.rotate((float) Math.toRadians(rotation.getY()), 0, 1, 0);
        }
        if (cameraMotion) {
            Vector3 translation = camera.getPosition();
            matrix.translate(translation.getXf(), translation.getYf(), translation.getZf());
        }
    }

    public MatrixHandler getTransformationMatrixHandler() {
        return transformationMatrixHandler;
    }

    public MatrixHandler getViewMatrixHandler() {
        return viewMatrixHandler;
    }

    public void updateProjectionMatrix() {
        projectionMatrix = camera.createProjectionMatrix(window);
    }

    public void disableCameraMotion() {
        cameraMotion = false;
    }

    public void enableCameraMotion() {
        cameraMotion = true;
    }

    public void disableCameraRotation() {
        cameraRotation = false;
    }

    public void enableCameraRotation() {
        cameraRotation = true;
    }

    @Override
    public void close() {
        mainShader.close();
    }

    public Camera getCamera() {
        return camera;
    }

    public Matrix4f getProjectionMatrix() {
        return projectionMatrix;
    }

    public TemplateShader getActiveShader() {
        return mainShader;
    }

    public void setActiveShader(TemplateShader mainShader) {
        this.mainShader = mainShader;
    }
}
