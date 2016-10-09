package com.cout970.gl.light;

import com.cout970.gl.util.vector.Vector3;

/**
 * Created by cout970 on 30/04/2016.
 */
public class BasicLight {

    private Vector3 position;
    private Vector3 color;

    public BasicLight(Vector3 position, Vector3 color) {
        this.position = position;
        this.color = color;
    }

    public Vector3 getColor() {
        return color;
    }

    public void setColor(Vector3 color) {
        this.color = color;
    }

    public Vector3 getPosition() {
        return position;
    }

    public void setPosition(Vector3 position) {
        this.position = position;
    }
}
