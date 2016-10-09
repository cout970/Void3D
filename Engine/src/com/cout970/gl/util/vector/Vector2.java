package com.cout970.gl.util.vector;

/**
 * Created by cout970 on 26/04/2016.
 */
public class Vector2 extends AbstractVector2<Vector2> {

    public Vector2(Number x, Number y) {
        super(x.doubleValue(), y.doubleValue());
    }

    @Override
    protected Vector2 create(double x, double y) {
        return new Vector2(x, y);
    }

    public Vector2() {
        super(0, 0);
    }

    @Override
    public Vector2 copy() {
        return new Vector2(getX(), getY());
    }
}
