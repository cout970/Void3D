package com.cout970.gl.input;

/**
 * Created by cout970 on 26/04/2016.
 */
public class KeyboardEvent extends AbstractInputEvent {

    private int keycode;
    private int scancode;
    private int action;
    private int mods;

    public KeyboardEvent(int keycode, int scancode, int action, int mods) {
        this.keycode = keycode;
        this.scancode = scancode;
        this.action = action;
        this.mods = mods;
    }

    public int getKeycode() {
        return keycode;
    }

    public int getScancode() {
        return scancode;
    }

    public int getAction() {
        return action;
    }

    public int getMods() {
        return mods;
    }

    @Override
    public String toString() {
        return "KeyboardEvent{" +
                "keycode=" + keycode +
                ", scancode=" + scancode +
                ", action=" + action +
                ", mods=" + mods +
                '}';
    }
}
