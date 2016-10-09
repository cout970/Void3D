package com.cout970.gl.raytrace;

import com.cout970.gl.util.vector.Vector3;

/**
 * Created by cout970 on 16/02/2016.
 */
public class Ray {

    private Vector3 start;
    private Vector3 end;

    public Ray(Vector3 start, Vector3 end) {
        this.start = start.copy();
        this.end = end.copy();
    }

    public Vector3 getStart() {
        return start.copy();
    }

    public void setStart(Vector3 start) {
        this.start = start;
    }

    public Vector3 getEnd() {
        return end.copy();
    }

    public void setEnd(Vector3 end) {
        this.end = end;
    }

    public Ray copy() {
        return new Ray(start, end);
    }

    @Override
    public String toString() {
        return "Ray{" +
                "start=" + start +
                ", end=" + end +
                '}';
    }
}
