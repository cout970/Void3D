package com.cout970.gl.input;

/**
 * Created by cout970 on 26/04/2016.
 */
public class CharEvent extends AbstractInputEvent {

    private int code;

    public CharEvent(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    @Override
    public String toString() {
        return "CharEvent{" +
                "code=" + code +
                '}';
    }
}
