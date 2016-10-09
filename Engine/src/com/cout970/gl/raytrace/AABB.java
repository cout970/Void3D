package com.cout970.gl.raytrace;


import com.cout970.gl.util.Direction;
import com.cout970.gl.util.vector.Vector3;

/**
 * Created by cout970 on 16/02/2016.
 */
public class AABB implements IRayObstacle {

    public static final double EPSILON = 1.0000000116860974E-7D;
    private Vector3 pos;
    private Vector3 size;

    public AABB(Vector3 pos, Vector3 size) {
        this.pos = pos;
        this.size = size;
    }

    public Vector3 getPos() {
        return pos.copy();
    }

    public void setPos(Vector3 pos) {
        this.pos = pos;
    }

    public Vector3 getSize() {
        return size.copy();
    }

    public void setSize(Vector3 size) {
        this.size = size;
    }

    @Override
    public RayTraceResult rayTrace(Ray ray) {
        return collisionRayTrace(new Vector3(), this, ray, this);
    }

    public Vector3 getStart() {
        return getPos();
    }

    public Vector3 getEnd() {
        return getPos().add(size);
    }

    public AABB copy() {
        return new AABB(pos.copy(), size.copy());
    }

    public double minX() {
        return getStart().getX();
    }

    public double minY() {
        return getStart().getY();
    }

    public double minZ() {
        return getStart().getZ();
    }

    public double maxX() {
        return getEnd().getX();
    }

    public double maxY() {
        return getEnd().getY();
    }

    public double maxZ() {
        return getEnd().getZ();
    }

    public static RayTraceResult collisionRayTrace(Vector3 pos, AABB box, Ray ray, IRayObstacle obstacle) {

        Vector3 start = ray.getStart();
        Vector3 end = ray.getEnd();
        start = start.copy().add(pos.copy().inverse());
        end = end.copy().add(pos.copy().inverse());

        Vector3 minX = getIntermediateWithXValue(start, end, box.minX());
        Vector3 maxX = getIntermediateWithXValue(start, end, box.maxX());
        Vector3 minY = getIntermediateWithYValue(start, end, box.minY());
        Vector3 maxY = getIntermediateWithYValue(start, end, box.maxY());
        Vector3 minZ = getIntermediateWithZValue(start, end, box.minZ());
        Vector3 maxZ = getIntermediateWithZValue(start, end, box.maxZ());

        if (!isVecInsideYZBounds(minX, box)) {
            minX = null;
        }

        if (!isVecInsideYZBounds(maxX, box)) {
            maxX = null;
        }

        if (!isVecInsideXZBounds(minY, box)) {
            minY = null;
        }

        if (!isVecInsideXZBounds(maxY, box)) {
            maxY = null;
        }

        if (!isVecInsideXYBounds(minZ, box)) {
            minZ = null;
        }

        if (!isVecInsideXYBounds(maxZ, box)) {
            maxZ = null;
        }

        Vector3 result = null;

        if (minX != null) {
            result = minX;
        }

        if (maxX != null && (result == null || start.distanceSquared(maxX) < start.distanceSquared(result))) {
            result = maxX;
        }

        if (minY != null && (result == null || start.distanceSquared(minY) < start.distanceSquared(result))) {
            result = minY;
        }

        if (maxY != null && (result == null || start.distanceSquared(maxY) < start.distanceSquared(result))) {
            result = maxY;
        }

        if (minZ != null && (result == null || start.distanceSquared(minZ) < start.distanceSquared(result))) {
            result = minZ;
        }

        if (maxZ != null && (result == null || start.distanceSquared(maxZ) < start.distanceSquared(result))) {
            result = maxZ;
        }

        if (result == null) {
            return null;
        } else {
            Direction side = null;

            if (result == minX) {
                side = Direction.WEST;
            }

            if (result == maxX) {
                side = Direction.EAST;
            }

            if (result == minY) {
                side = Direction.DOWN;
            }

            if (result == maxY) {
                side = Direction.UP;
            }

            if (result == minZ) {
                side = Direction.NORTH;
            }

            if (result == maxZ) {
                side = Direction.SOUTH;
            }

            return new RayTraceResult(ray, result.add(pos), obstacle);
        }
    }

    public static boolean isVecInsideYZBounds(Vector3 point, AABB box) {
        return point != null && (point.getY() >= box.minY() && point.getY() <= box.maxY() && point.getZ() >= box.minZ() && point.getZ() <= box.maxZ());
    }

    public static boolean isVecInsideXZBounds(Vector3 point, AABB box) {
        return point != null && (point.getX() >= box.minX() && point.getX() <= box.maxX() && point.getZ() >= box.minZ() && point.getZ() <= box.maxZ());
    }

    public static boolean isVecInsideXYBounds(Vector3 point, AABB box) {
        return point != null && (point.getX() >= box.minX() && point.getX() <= box.maxX() && point.getY() >= box.minY() && point.getY() <= box.maxY());
    }


    public static Vector3 getIntermediateWithXValue(Vector3 start, Vector3 end, double x) {

        Vector3 diff = end.copy().sub(start);

        if (diff.getX() * diff.getX() < EPSILON) {
            return null;
        } else {
            double d3 = (x - start.getX()) / diff.getX();
            return d3 >= 0.0D && d3 <= 1.0D ? start.copy().add(diff.mul(d3)) : null;
        }
    }

    public static Vector3 getIntermediateWithYValue(Vector3 start, Vector3 end, double y) {

        Vector3 diff = end.copy().sub(start);

        if (diff.getY() * diff.getY() < EPSILON) {
            return null;
        } else {
            double d3 = (y - start.getY()) / diff.getY();
            return d3 >= 0.0D && d3 <= 1.0D ? start.copy().add(diff.mul(d3)) : null;
        }
    }

    public static Vector3 getIntermediateWithZValue(Vector3 start, Vector3 end, double z) {
        Vector3 diff = end.copy().sub(start);

        if (diff.getZ() * diff.getZ() < EPSILON) {
            return null;
        } else {
            double d3 = (z - start.getZ()) / diff.getZ();
            return d3 >= 0.0D && d3 <= 1.0D ? start.copy().add(diff.mul(d3)) : null;
        }
    }

    public AABB translate(Vector3 dir) {
        return new AABB(getPos().add(dir), size);
    }

    public AABB scale(double val) {
        return new AABB(getPos(), getSize().mul(val));
    }

    public static boolean isInside(Vector3 punto, Vector3 inicioCubo, Vector3 finCubo) {
        return  punto.getX() > inicioCubo.getX() && punto.getX() < finCubo.getX() &&
                punto.getY() > inicioCubo.getY() && punto.getY() < finCubo.getY() &&
                punto.getZ() > inicioCubo.getZ() && punto.getZ() < finCubo.getZ();
    }
}
