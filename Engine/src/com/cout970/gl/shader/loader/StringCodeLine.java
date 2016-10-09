package com.cout970.gl.shader.loader;

/**
 * Created by cout970 on 05/05/2016.
 */
public class StringCodeLine implements ICodeLine {

    private boolean readed;
    private String line;

    public StringCodeLine(String line) {
        this.line = line;
        readed = false;
    }

    @Override
    public String nextLine() {
        readed = true;
        return line;
    }

    @Override
    public boolean hasNextLine() {
        return !readed;
    }

    @Override
    public void reset() {
        readed = false;
    }
}
