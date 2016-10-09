package com.cout970.gl.camera;

import com.cout970.gl.util.RotationVect;
import com.cout970.gl.util.vector.Vector3;
import com.cout970.gl.window.Window;
import org.joml.Matrix4f;

/**
 * Created by cout970 on 27/04/2016.
 */
public abstract class Camera {

    private RotationVect rotation;
    private Vector3 position;

    public Camera() {
        rotation = new RotationVect();
        position = new Vector3();
    }

    public RotationVect getRotation() {
        return new RotationVect(rotation);
    }

    public void setRotation(RotationVect rotation) {
        this.rotation = new RotationVect(rotation);
    }

    public Vector3 getPosition() {
        return position.copy();
    }

    public void setPosition(Vector3 position) {
        this.position = position.copy();
    }

    public void translate(Vector3 vector3) {
        position.add(vector3);
    }

    public void rotatePitch(float i) {
        rotation.add(i, 0);
    }

    public void rotateYaw(float i) {
        rotation.add(0, i);
    }

    @Override
    public String toString() {
        return "Camera{" +
                "position=" + position +
                ", rotation=" + rotation +
                '}';
    }

    public abstract Matrix4f createProjectionMatrix(Window window);

    public Vector3 getLookDirection() {
        return new Vector3(0,0,1).rotate(Math.toRadians(rotation.getYaw()), new Vector3(0,1,0)).rotate(Math.toRadians(rotation.getPitch()), new Vector3(1,0,0));
    }

    public Vector3 getHorizontalDirection(){
        return new Vector3(0,0,1).rotate(Math.toRadians(rotation.getYaw()), new Vector3(0,1,0));
    }
}
