package com.cout970.gl.texture.internal;

import com.cout970.gl.util.vector.Vector2;

import java.nio.ByteBuffer;

/**
 * Created by cout970 on 05/05/2016.
 */
public class TextureData {

    private int id;
    private Vector2 size;
    private ByteBuffer data;
    private int componenets;

    public TextureData(int id, Vector2 size, ByteBuffer data, int componenets) {
        this.id = id;
        this.size = size;
        this.data = data;
        this.componenets = componenets;
    }

    public int getId() {
        return id;
    }

    public Vector2 getSize() {
        return size;
    }

    public ByteBuffer getData() {
        return data;
    }

    public int getComponents() {
        return componenets;
    }
}
