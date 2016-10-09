package com.cout970.gl.util.vector;

/**
 * Created by cout970 on 26/04/2016.
 */
public class Vector3 extends AbstractVector3<Vector3> {

    public Vector3(Number x, Number y, Number z) {
        super(x.doubleValue(), y.doubleValue(), z.doubleValue());
    }

    public Vector3() {
        super(0, 0, 0);
    }

    public Vector3(Vector2 vect, float i) {
        super(vect.getX(), vect.getY(), i);
    }

    @Override
    public Vector3 copy() {
        return new Vector3(getX(), getY(), getZ());
    }

    @Override
    public Vector3 create(double x, double y, double z) {
        return new Vector3(x, y, z);
    }
}
