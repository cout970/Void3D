package com.cout970.gl.input;

/**
 * Created by cout970 on 26/04/2016.
 */
public class ScrollEvent extends AbstractInputEvent {

    private double offsetX;
    private double offsetY;

    public ScrollEvent(double offsetX, double offsetY) {
        this.offsetX = offsetX;
        this.offsetY = offsetY;
    }

    public double getOffsetX() {
        return offsetX;
    }

    public double getOffsetY() {
        return offsetY;
    }

    @Override
    public String toString() {
        return "ScrollEvent{" +
                "offsetX=" + offsetX +
                ", offsetY=" + offsetY +
                '}';
    }
}
