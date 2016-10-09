package com.cout970.gl.raytrace;

import com.cout970.gl.util.Direction;
import com.cout970.gl.util.vector.Vector3;

/**
 * Created by cout970 on 16/02/2016.
 */
public class OBB implements IRayObstacle {

    private Vector3 pos;
    private Vector3 size;
    private Vector3 rotation;
    private Vector3 rotationPoint;

    public OBB(Vector3 pos, Vector3 size, Vector3 rotation, Vector3 rotationPoint) {
        this.pos = pos.copy();
        this.size = size.copy();
        this.rotation = rotation.copy();
        this.rotationPoint = rotationPoint.copy();
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

    public Vector3 getRotation() {
        return rotation.copy();
    }

    public void setRotation(Vector3 rotation) {
        this.rotation = rotation;
    }

    public Vector3 getRotationPoint() {
        return rotationPoint;
    }

    public void setRotationPoint(Vector3 rotationPoint) {
        this.rotationPoint = rotationPoint;
    }

    @Override
    public RayTraceResult rayTrace(Ray ray) {
        RayTraceResult r;
        for (Direction d : Direction.values()) {
            Vector3 str = new Vector3();
            Vector3 end = size.copy();
            Vector3[] pattern = null;
            if (d == Direction.DOWN) {
                pattern = new Vector3[]{new Vector3(0, 0, 0), new Vector3(1, 0, 0), new Vector3(1, 0, 1),
                        new Vector3(0, 0, 1)};
            } else if (d == Direction.UP) {
                pattern = new Vector3[]{new Vector3(0, 1, 0), new Vector3(1, 1, 0), new Vector3(1, 1, 1),
                        new Vector3(0, 1, 1)};
            } else if (d == Direction.NORTH) {
                pattern = new Vector3[]{new Vector3(0, 0, 0), new Vector3(1, 0, 0), new Vector3(1, 1, 0),
                        new Vector3(0, 1, 0)};
            } else if (d == Direction.SOUTH) {
                pattern = new Vector3[]{new Vector3(0, 0, 1), new Vector3(1, 0, 1), new Vector3(1, 1, 1),
                        new Vector3(0, 1, 1)};
            } else if (d == Direction.WEST) {
                pattern = new Vector3[]{new Vector3(0, 0, 0), new Vector3(0, 1, 0), new Vector3(0, 1, 1),
                        new Vector3(0, 0, 1)};
            } else {
                pattern = new Vector3[]{new Vector3(1, 0, 0), new Vector3(1, 1, 0), new Vector3(1, 1, 1),
                        new Vector3(1, 0, 1)};
            }
            r = RayTraceUtil.rayTraceQuad(ray, this,
                    new Vector3(str.getX() + end.getX() * pattern[0].getX(), str.getY() + end.getY() * pattern[0].getY(),
                            str.getZ() + end.getZ() * pattern[0].getZ())
                            .sub(rotationPoint).rotateX(rotation.getX()).rotateY(rotation.getY()).rotateZ(rotation.getZ())
                            .add(rotationPoint).add(pos),
                    new Vector3(str.getX() + end.getX() * pattern[1].getX(), str.getY() + end.getY() * pattern[1].getY(),
                            str.getZ() + end.getZ() * pattern[1].getZ())
                            .sub(rotationPoint).rotateX(rotation.getX()).rotateY(rotation.getY()).rotateZ(rotation.getZ())
                            .add(rotationPoint).add(pos),
                    new Vector3(str.getX() + end.getX() * pattern[2].getX(), str.getY() + end.getY() * pattern[2].getY(),
                            str.getZ() + end.getZ() * pattern[2].getZ())
                            .sub(rotationPoint).rotateX(rotation.getX()).rotateY(rotation.getY()).rotateZ(rotation.getZ())
                            .add(rotationPoint).add(pos),
                    new Vector3(str.getX() + end.getX() * pattern[3].getX(), str.getY() + end.getY() * pattern[3].getY(),
                            str.getZ() + end.getZ() * pattern[3].getZ())
                            .sub(rotationPoint).rotateX(rotation.getX()).rotateY(rotation.getY()).rotateZ(rotation.getZ())
                            .add(rotationPoint).add(pos));

            if (r != null) {
                r.setObject(this);
                return r;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return "OBB{" +
                "pos=" + pos +
                ", size=" + size +
                ", rotation=" + rotation +
                ", rotationPoint=" + rotationPoint +
                '}';
    }
}
