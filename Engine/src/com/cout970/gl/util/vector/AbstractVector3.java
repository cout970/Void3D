package com.cout970.gl.util.vector;

/**
 * Created by cout970 on 26/04/2016.
 */
public abstract class AbstractVector3<T extends AbstractVector3> {

    private double x;
    private double y;
    private double z;

    public AbstractVector3(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getZ() {
        return z;
    }

    public float getXf() {
        return (float) x;
    }

    public float getYf() {
        return (float) y;
    }

    public float getZf() {
        return (float) z;
    }

    public int getXi() {
        return (int) x;
    }

    public int getYi() {
        return (int) y;
    }

    public int getZi() {
        return (int) z;
    }

    public T setX(double x) {
        this.x = x;
        return (T) this;
    }

    public T setY(double y) {
        this.y = y;
        return (T) this;
    }

    public T setZ(double z) {
        this.z = z;
        return (T) this;
    }

    public T setX(float x) {
        this.x = x;
        return (T) this;
    }

    public T setY(float y) {
        this.y = y;
        return (T) this;
    }

    public T setZ(float z) {
        this.z = z;
        return (T) this;
    }

    public T setX(int x) {
        this.x = x;
        return (T) this;
    }

    public T setY(int y) {
        this.y = y;
        return (T) this;
    }

    public T setZ(int z) {
        this.z = z;
        return (T) this;
    }

    public T set(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
        return (T) this;
    }

    public T set(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
        return (T) this;
    }

    public T set(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
        return (T) this;
    }

    public T set(T other) {
        this.x = other.getX();
        this.y = other.getY();
        this.z = other.getZ();
        return (T) this;
    }

    public T add(double x, double y, double z) {
        this.x += x;
        this.y += y;
        this.z += z;
        return (T) this;
    }

    public T add(float x, float y, float z) {
        this.x += x;
        this.y += y;
        this.z += z;
        return (T) this;
    }

    public T add(int x, int y, int z) {
        this.x += x;
        this.y += y;
        this.z += z;
        return (T) this;
    }

    public T add(T other) {
        this.x += other.getX();
        this.y += other.getY();
        this.z += other.getZ();
        return (T) this;
    }

    public T sub(double x, double y, double z) {
        this.x -= x;
        this.y -= y;
        this.z -= z;
        return (T) this;
    }

    public T sub(float x, float y, float z) {
        this.x -= x;
        this.y -= y;
        this.z -= z;
        return (T) this;
    }

    public T sub(int x, int y, int z) {
        this.x -= x;
        this.y -= y;
        this.z -= z;
        return (T) this;
    }

    public T sub(T other) {
        this.x -= other.getX();
        this.y -= other.getY();
        this.z -= other.getZ();
        return (T) this;
    }

    public T mul(double x, double y, double z) {
        this.x *= x;
        this.y *= y;
        this.z *= z;
        return (T) this;
    }

    public T mul(float x, float y, float z) {
        this.x *= x;
        this.y *= y;
        this.z *= z;
        return (T) this;
    }

    public T mul(int x, int y, int z) {
        this.x *= x;
        this.y *= y;
        this.z *= z;
        return (T) this;
    }

    public T mul(T other) {
        this.x *= other.getX();
        this.y *= other.getY();
        this.z *= other.getZ();
        return (T) this;
    }

    public T mul(double a) {
        this.x *= a;
        this.y *= a;
        this.z *= a;
        return (T) this;
    }

    public T mul(float a) {
        this.x *= a;
        this.y *= a;
        this.z *= a;
        return (T) this;
    }

    public T mul(int a) {
        this.x *= a;
        this.y *= a;
        this.z *= a;
        return (T) this;
    }

    public T div(double x, double y, double z) {
        this.x /= x;
        this.y /= y;
        this.z /= z;
        return (T) this;
    }

    public T div(float x, float y, float z) {
        this.x /= x;
        this.y /= y;
        this.z /= z;
        return (T) this;
    }

    public T div(int x, int y, int z) {
        this.x /= x;
        this.y /= y;
        this.z /= z;
        return (T) this;
    }

    public T div(T other) {
        this.x /= other.getX();
        this.y /= other.getY();
        this.z /= other.getZ();
        return (T) this;
    }

    public T div(double a) {
        this.x /= a;
        this.y /= a;
        this.z /= a;
        return (T) this;
    }

    public T div(float a) {
        this.x /= a;
        this.y /= a;
        this.z /= a;
        return (T) this;
    }

    public T div(int a) {
        this.x /= a;
        this.y /= a;
        this.z /= a;
        return (T) this;
    }

    public double length() {
        return Math.sqrt((this.x * this.x + this.y * this.y + this.z * this.z));
    }

    public double lengthSquared() {
        return this.x * this.x + this.y * this.y + this.z * this.z;
    }

    public double distance(T v) {
        return Math.sqrt(((v.getX() - this.x) * (v.getX() - this.x) + (v.getY() - this.y) * (v.getY() - this.y) + (v.getZ() - this.z) * (v.getZ() - this.z)));
    }

    public T normalize() {
        double length = Math.sqrt((this.x * this.x + this.y * this.y + this.z * this.z));
        this.x /= length;
        this.y /= length;
        this.z /= length;
        return (T) this;
    }

    public double dot(AbstractVector3 vec) {
        return vec.x * x + vec.y * y + vec.z * z;
    }

    public T rotate(double angle, AbstractVector3 axis) {
        double cos = Math.cos(angle);
        double sin = Math.sin(angle);
        //@formatter:off
        AbstractVector3[] rotationMatrix = {
                create(cos + axis.x * axis.x * (1 - cos), axis.y * axis.x * (1 - cos) + axis.z * sin, axis.z * axis.x * (1 - cos) - axis.y * sin),
                create(axis.x * axis.y * (1 - cos) - axis.z * sin, cos + axis.y * axis.y * (1 - cos), axis.z * axis.y * (1 - cos) + axis.z * sin),
                create(axis.x * axis.z * (1 - cos) + axis.y * sin, axis.y * axis.z * (1 - cos), cos + axis.z * axis.z * (1 - cos))
        };

        //@formatter:on
        double i, j, k;
        i = dot(rotationMatrix[0]);
        j = dot(rotationMatrix[1]);
        k = dot(rotationMatrix[2]);

        set(i, j, k);
        return (T) this;
    }

    public T inverse() {
        this.x = -x;
        this.y = -y;
        this.z = -z;
        return (T) this;
    }

    public abstract T copy();

    public abstract T create(double x, double y, double z);


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AbstractVector3)) {
            return false;
        }

        AbstractVector3<?> that = (AbstractVector3<?>) o;

        if (Double.compare(that.x, x) != 0) {
            return false;
        }
        if (Double.compare(that.y, y) != 0) {
            return false;
        }
        return Double.compare(that.z, z) == 0;

    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        temp = Double.doubleToLongBits(x);
        result = (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(y);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(z);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }

    @Override
    public String toString() {
        return "AbstractVector3{" +
                "x=" + x +
                ", y=" + y +
                ", z=" + z +
                '}';
    }

    public T cross(AbstractVector3 vec) {
        return create((y * vec.z) - (z * vec.y), (z * vec.x) - (x * vec.z), (x * vec.y) - (y * vec.x));
    }

    public T rotateX(double angle) {
        AbstractVector3[] rotationMatrix = {create(1, 0, 0), create(0, Math.cos(angle),
                -Math.sin(angle)), create(0, Math.sin(angle), Math.cos(angle))};

        double i, j, k;
        i = dot(rotationMatrix[0]);
        j = dot(rotationMatrix[1]);
        k = dot(rotationMatrix[2]);
        set(i, j, k);
        return (T) this;
    }

    public T rotateY(double angle) {
        AbstractVector3[] rotationMatrix = {create(Math.cos(angle), 0, Math.sin(angle)), create(0, 1,
                0), create(-Math.sin(angle), 0, Math.cos(angle))};

        double i, j, k;
        i = dot(rotationMatrix[0]);
        j = dot(rotationMatrix[1]);
        k = dot(rotationMatrix[2]);
        set(i, j, k);
        return (T) this;
    }

    public T rotateZ(double angle) {
        AbstractVector3[] rotationMatrix = {create(Math.cos(angle), -Math.sin(angle),
                0), create(Math.sin(angle), Math.cos(angle), 0), create(0, 0, 1)};

        double i, j, k;
        i = dot(rotationMatrix[0]);
        j = dot(rotationMatrix[1]);
        k = dot(rotationMatrix[2]);
        set(i, j, k);
        return (T) this;
    }


    public double distanceSquared(T v) {
        return ((v.getX() - this.x) * (v.getX() - this.x) + (v.getY() - this.y) * (v.getY() - this.y) + (v.getZ() - this.z) * (v.getZ() - this.z));
    }

    public T toRadians() {
        x = Math.toRadians(x);
        y = Math.toRadians(y);
        z = Math.toRadians(z);
        return (T) this;
    }
}
