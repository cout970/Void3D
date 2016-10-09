package com.cout970.gl.util;

import com.cout970.gl.util.vector.Vector2;

/**
 * Created by cout970 on 27/04/2016.
 */
public class Square {

    private Vector2 position;
    private Vector2 size;

    public Square(Vector2 position, Vector2 size) {
        this.position = position.copy();
        this.size = size.copy();
    }

    public Vector2 getStart(){
        return getPosition();
    }

    public Vector2 getEnd(){
        return getPosition().add(size);
    }

    public Vector2 getPosition() {
        return position.copy();
    }

    public void setPosition(Vector2 position) {
        this.position = position.copy();
    }

    public Vector2 getSize() {
        return size.copy();
    }

    public void setSize(Vector2 size) {
        this.size = size.copy();
    }

    public boolean hit(Vector2 vec) {
        return vec.getX() > position.getX() && vec.getX() < position.getX() + size.getX()
                && vec.getY() > position.getY() && vec.getY() < position.getY() + size.getY();
    }

    public Square copy() {
        return new Square(position, size);
    }
}
