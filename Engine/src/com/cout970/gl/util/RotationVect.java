package com.cout970.gl.util;

import com.cout970.gl.util.vector.Vector2;
import com.cout970.gl.util.vector.Vector3;

/**
 * Created by cout970 on 11/01/2016.
 */
public class RotationVect extends Vector2 {

    public RotationVect(float pitch, float yaw) {
        super(pitch, yaw);
    }

    public RotationVect() {
        super(0, 0);
    }

    public RotationVect(RotationVect rotation) {
        this(rotation.getPitch(), rotation.getYaw());
    }

    public RotationVect(Vector2 rotation) {
        this(rotation.getXf(), rotation.getYf());
    }

    public float getPitch() {
        return getXf();
    }

    public float getYaw() {
        return getYf();
    }

    public void setPitch(float pitch) {
        setX(pitch);
    }

    public void setYaw(float yaw) {
        setY(yaw);
    }

    public float getYawWrappedTo180() {
        return wrapTo180(getYaw());
    }

    public static float wrapTo180(float angle) {
        angle %= 360.0F;

        if (angle >= 180.0F) {
            angle -= 360.0F;
        }

        if (angle < -180.0F) {
            angle += 360.0F;
        }

        return angle;
    }

    public Direction toHorizontalAxis() {
        float yaw = getYawWrappedTo180();
        if ((yaw < 45 && yaw >= 0) || (yaw > -45 && yaw <= 0)) {
            return Direction.SOUTH;
        } else if (yaw >= 45 && yaw < 135) {
            return Direction.WEST;
        } else if (yaw <= -45 && yaw > -135) {
            return Direction.EAST;
        } else if ((yaw >= 135 && yaw <= 180) || (yaw <= -135 && yaw >= -180)) {
            return Direction.NORTH;
        }
        throw new IllegalStateException("Invalid yaw: " + yaw);
    }

    public Vector3 getLookVector() {
        double cos0 = Math.cos(-getYaw() * 0.017453292F - Math.PI);
        double sin0 = Math.sin(-getYaw() * 0.017453292F - Math.PI);
        double cos1 = -Math.cos(-getPitch() * 0.017453292F);
        double sin1 = Math.sin(-getPitch() * 0.017453292F);
        return new Vector3(sin0 * cos1, sin1, cos0 * cos1);
    }
}
