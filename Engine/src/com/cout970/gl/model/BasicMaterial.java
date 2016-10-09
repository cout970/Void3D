package com.cout970.gl.model;

import com.cout970.gl.texture.ITexture;

/**
 * Created by cout970 on 28/04/2016.
 */
public class BasicMaterial implements IMaterial {

    private static int lastID = 1;
    private ITexture texture;
    private float shineDamper;
    private float reflectivity;
    private int id;

    public BasicMaterial(ITexture texture) {
        this.texture = texture;
        shineDamper = 1;
        reflectivity = 0;
        id = getNextID();
    }

    public BasicMaterial(ITexture texture, float shineDamper, float reflectivity) {
        this.texture = texture;
        this.shineDamper = shineDamper;
        this.reflectivity = reflectivity;
        id = getNextID();
    }

    protected static int getNextID(){
        return lastID++;
    }

    @Override
    public float getShineDamper() {
        return shineDamper;
    }

    @Override
    public float getReflectivity() {
        return reflectivity;
    }

    @Override
    public ITexture[] getTextures() {
        return new ITexture[]{texture};
    }

    @Override
    public int getID() {
        return id;
    }
}
