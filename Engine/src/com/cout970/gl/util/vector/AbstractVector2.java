package com.cout970.gl.util.vector;

/**
 * Created by cout970 on 26/04/2016.
 */
public abstract class AbstractVector2<T extends AbstractVector2>{

    private double x;
    private double y;

    public AbstractVector2(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public float getXf() {
        return (float) x;
    }

    public float getYf() {
        return (float) y;
    }

    public int getXi() {
        return (int) x;
    }

    public int getYi() {
        return (int) y;
    }

    public T setX(double x) {
        this.x = x;
        return (T) this;
    }

    public T setY(double y) {
        this.y = y;
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

    public T setX(int x) {
        this.x = x;
        return (T) this;
    }

    public T setY(int y) {
        this.y = y;
        return (T) this;
    }

    public T set(double x, double y){
        this.x = x;
        this.y = y;
        return (T) this;
    }

    public T set(float x, float y){
        this.x = x;
        this.y = y;
        return (T) this;
    }

    public T set(int x, int y){
        this.x = x;
        this.y = y;
        return (T) this;
    }

    public T set(T other){
        this.x = other.getX();
        this.y = other.getY();
        return (T) this;
    }

    public T add(double x, double y){
        this.x += x;
        this.y += y;
        return (T) this;
    }

    public T add(float x, float y){
        this.x += x;
        this.y += y;
        return (T) this;
    }

    public T add(int x, int y){
        this.x += x;
        this.y += y;
        return (T) this;
    }

    public T add(T other){
        this.x += other.getX();
        this.y += other.getY();
        return (T) this;
    }

    public T sub(double x, double y){
        this.x -= x;
        this.y -= y;
        return (T) this;
    }

    public T sub(float x, float y){
        this.x -= x;
        this.y -= y;
        return (T) this;
    }

    public T sub(int x, int y){
        this.x -= x;
        this.y -= y;
        return (T) this;
    }

    public T sub(T other){
        this.x -= other.getX();
        this.y -= other.getY();
        return (T) this;
    }

    public T mul(double x, double y){
        this.x *= x;
        this.y *= y;
        return (T) this;
    }

    public T mul(float x, float y){
        this.x *= x;
        this.y *= y;
        return (T) this;
    }

    public T mul(int x, int y){
        this.x *= x;
        this.y *= y;
        return (T) this;
    }

    public T mul(T other){
        this.x *= other.getX();
        this.y *= other.getY();
        return (T) this;
    }

    public T mul(double a){
        this.x *= a;
        this.y *= a;
        return (T) this;
    }

    public T mul(float a){
        this.x *= a;
        this.y *= a;
        return (T) this;
    }

    public T mul(int a){
        this.x *= a;
        this.y *= a;
        return (T) this;
    }

    public T div(double x, double y){
        this.x /= x;
        this.y /= y;
        return (T) this;
    }

    public T div(float x, float y){
        this.x /= x;
        this.y /= y;
        return (T) this;
    }

    public T div(int x, int y){
        this.x /= x;
        this.y /= y;
        return (T) this;
    }

    public T div(T other){
        this.x /= other.getX();
        this.y /= other.getY();
        return (T) this;
    }

    public T div(double a){
        this.x /= a;
        this.y /= a;
        return (T) this;
    }

    public T div(float a){
        this.x /= a;
        this.y /= a;
        return (T) this;
    }

    public T div(int a){
        this.x /= a;
        this.y /= a;
        return (T) this;
    }

    public double length() {
        return Math.sqrt(this.x * this.x + this.y * this.y);
    }

    public double lengthSquared() {
        return this.x * this.x + this.y * this.y;
    }

    public double distance(T v) {
        return Math.sqrt((v.getX() - this.x) * (v.getX() - this.x) + (v.getY() - this.y) * (v.getY() - this.y));
    }

    public T normalize() {
        double length = Math.sqrt((this.x * this.x + this.y * this.y));
        this.x /= length;
        this.y /= length;
        return (T) this;
    }

    public T interpolate(AbstractVector2 other, double val){
        AbstractVector2 vec = create(1d,1d);
        return (T) this;
    }

    protected abstract T create(double x, double y);

    public abstract T copy();

    @Override
    public boolean equals(Object o) {
        if (this == o) { return true; }
        if (!(o instanceof AbstractVector2)) { return false; }

        AbstractVector2<?> that = (AbstractVector2<?>) o;

        if (Double.compare(that.x, x) != 0) { return false; }
        return Double.compare(that.y, y) == 0;

    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        temp = Double.doubleToLongBits(x);
        result = (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(y);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }

    @Override
    public String toString() {
        return "AbstractVector2{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }

    public T toRadians(){
        x = Math.toRadians(x);
        y = Math.toRadians(y);
        return (T) this;
    }
}
