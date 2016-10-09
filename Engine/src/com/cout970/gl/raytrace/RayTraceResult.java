package com.cout970.gl.raytrace;

import com.cout970.gl.util.vector.Vector3;

/**
 * Created by cout970 on 16/02/2016.
 */
public class RayTraceResult {

    private Ray ray;
    private Vector3 hit;
    private IRayObstacle object;
    private Object extraData;

    public RayTraceResult(Ray ray, Vector3 hit, IRayObstacle object) {
        this.ray = ray;
        this.hit = hit;
        this.object = object;
    }

    public RayTraceResult(Ray ray, Vector3 hit, IRayObstacle object, Object extraData) {
        this.ray = ray;
        this.hit = hit;
        this.object = object;
        this.extraData = extraData;
    }

    public Ray getRay() {
        return ray.copy();
    }

    public void setRay(Ray ray) {
        this.ray = ray;
    }

    public Vector3 getHit() {
        return hit.copy();
    }

    public void setHit(Vector3 hit) {
        this.hit = hit;
    }

    public IRayObstacle getObject() {
        return object;
    }

    public void setObject(IRayObstacle object) {
        this.object = object;
    }

    public Object getExtraData() {
        return extraData;
    }

    public void setExtraData(Object extraData) {
        this.extraData = extraData;
    }

    @Override
    public String toString() {
        return "RayTraceResult{" +
                "ray=" + ray +
                ", hit=" + hit +
                ", object=" + object +
                ", extraData=" + extraData +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RayTraceResult)) return false;

        RayTraceResult that = (RayTraceResult) o;

        if (ray != null ? !ray.equals(that.ray) : that.ray != null) return false;
        if (hit != null ? !hit.equals(that.hit) : that.hit != null) return false;
        if (object != null ? !object.equals(that.object) : that.object != null) return false;
        return extraData != null ? extraData.equals(that.extraData) : that.extraData == null;

    }

    @Override
    public int hashCode() {
        int result = ray != null ? ray.hashCode() : 0;
        result = 31 * result + (hit != null ? hit.hashCode() : 0);
        result = 31 * result + (object != null ? object.hashCode() : 0);
        result = 31 * result + (extraData != null ? extraData.hashCode() : 0);
        return result;
    }
}
