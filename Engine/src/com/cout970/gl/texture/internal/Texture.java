package com.cout970.gl.texture.internal;

import com.cout970.gl.resource.IResourceLocation;
import com.cout970.gl.texture.ITexture;
import com.cout970.gl.util.vector.Vector2;

/**
 * Created by cout970 on 26/04/2016.
 */
public class Texture implements ITexture {

    private int glID;
    private Vector2 size;
    private IResourceLocation location;

    public Texture(IResourceLocation loc, Vector2 size, int glID) {
        this.size = size;
        this.location = loc;
        this.glID = glID;
    }

    @Override
    public Vector2 getSize() {
        return size.copy();
    }

    @Override
    public IResourceLocation getLocation() {
        return location;
    }

    @Override
    public String getName() {
        return location.toString();
    }

    @Override
    public int getID() {
        return glID;
    }
}
