package com.cout970.gl.util;

import com.cout970.gl.util.vector.Vector3;

public enum Direction {

    DOWN(0, -1, 0, Axis.Y, AxisDirection.NEGATIVE),
    UP(0, 1, 0, Axis.Y, AxisDirection.POSITIVE),
    NORTH(0, 0, -1, Axis.Z, AxisDirection.NEGATIVE),
    SOUTH(0, 0, 1, Axis.Z, AxisDirection.POSITIVE),
    WEST(-1, 0, 0, Axis.X, AxisDirection.NEGATIVE),
    EAST(1, 0, 0, Axis.X, AxisDirection.POSITIVE);

    public static final Direction[] VALID_DIRECTIONS = {DOWN, UP, NORTH, SOUTH, WEST, EAST};
    public static final Direction[] OPPOSITES = {UP, DOWN, SOUTH, NORTH, EAST, WEST};
    public static final Direction[] HORIZONTAL = {NORTH, SOUTH, WEST, EAST};
    public static final int[][] rotation = {
            {0, 1, 5, 4, 2, 3},
            {0, 1, 4, 5, 3, 2},
            {5, 4, 2, 3, 0, 1},
            {4, 5, 2, 3, 1, 0},
            {2, 3, 1, 0, 4, 5},
            {3, 2, 0, 1, 4, 5},
            {0, 1, 2, 3, 4, 5}};


    private final Vector3 offsets;
    private final Axis axis;
    private final AxisDirection axisDir;

    Direction(int x, int y, int z, Axis axis, AxisDirection axisDir) {
        offsets = new Vector3(x, y, z);
        this.axis = axis;
        this.axisDir = axisDir;
    }

    public int getOffsetX() {
        return offsets.getXi();
    }

    public int getOffsetY() {
        return offsets.getYi();
    }

    public int getOffsetZ() {
        return offsets.getZi();
    }

    public Axis getAxis() {
        return axis;
    }

    public Direction opposite() {
        return OPPOSITES[ordinal()];
    }

    public static Direction getDirection(int i) {
        return values()[i % VALID_DIRECTIONS.length];
    }

    public Vector3 toVector3() {
        return offsets.copy();
    }

    //anti-clockwise
    public Direction step(Direction axis) {
        return Direction.getDirection(rotation[axis.ordinal()][ordinal()]);
    }

    public Direction rotate(Axis axis, boolean clockwise) {
        return step(clockwise ? axis.getNegativeDir() : axis.getPositiveDir());
    }

    public boolean isPerpendicular(Direction dir) {
        return !isParallel(dir);
    }

    public boolean isParallel(Direction dir) {
        return dir.getAxis() == getAxis();
    }

    public boolean matches(Vector3 offset) {
        return offsets.equals(offset);
    }

    public AxisDirection getAxisDirection() {
        return axisDir;
    }

    public enum Axis {
        X(4),
        Y(0),
        Z(2);

        private int negative;

        Axis(int neg) {
            this.negative = neg;
        }

        public Direction getPositiveDir() {
            return getDirection(negative).opposite();
        }

        public Direction getNegativeDir() {
            return getDirection(negative);
        }

        public Direction getDirectionByAxisDirection(AxisDirection a) {
            return a == AxisDirection.POSITIVE ? getPositiveDir() : getNegativeDir();
        }

        public Direction[] getParallelDirections() {
            return new Direction[]{getNegativeDir(), getPositiveDir()};
        }

        public Direction[] getPerpendicularDirections() {
            Direction[] dirs = new Direction[4];
            int index = 0;
            for (Direction dir : Direction.values()) {
                if (dir.getAxis() != this) {
                    dirs[index] = dir;
                    index++;
                }
            }
            return dirs;
        }
    }

    public enum AxisDirection {
        POSITIVE(1),
        NEGATIVE(-1);

        private int direction;

        AxisDirection(int direction) {
            this.direction = direction;
        }

        public Direction getDirectionByAxis(Axis a) {
            return this == POSITIVE ? a.getPositiveDir() : a.getNegativeDir();
        }
    }
}
