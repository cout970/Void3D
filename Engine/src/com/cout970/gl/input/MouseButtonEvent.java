package com.cout970.gl.input;

/**
 * Created by cout970 on 26/04/2016.
 */
public class MouseButtonEvent extends AbstractInputEvent {

    private int button;
    private int action;
    private int mods;

    public MouseButtonEvent(int button, int action, int mods) {
        this.button = button;
        this.action = action;
        this.mods = mods;
    }

    public MouseButton getButton() {
        return MouseButton.fromID(button);
    }

    public int getAction() {
        return action;
    }

    public int getMods() {
        return mods;
    }

    @Override
    public String toString() {
        return "MouseButtonEvent{" +
                "button=" + button +
                ", action=" + action +
                ", mods=" + mods +
                '}';
    }

    public enum MouseButton {
        LEFT,
        RIGHT,
        MIDDLE;

        public static MouseButton fromID(int id) {
            switch (id) {
                case 0:
                    return LEFT;
                case 1:
                    return RIGHT;
                case 2:
                    return MIDDLE;
            }
            return null;
        }

        public int getID() {
            switch (this) {
                case LEFT:
                    return 0;
                case RIGHT:
                    return 1;
                case MIDDLE:
                    return 2;
            }
            return 0;
        }
    }
}
