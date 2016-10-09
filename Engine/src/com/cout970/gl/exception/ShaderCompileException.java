package com.cout970.gl.exception;

/**
 * Created by cout970 on 28/04/2016.
 */
public class ShaderCompileException extends RuntimeException {

    public ShaderCompileException(String s, String s1) {
        super(s + s1);
    }
}
