package com.cout970.gl.model;

import com.cout970.gl.texture.ITexture;
import com.cout970.gl.util.IIdentifiable;

/**
 * Created by cout970 on 09/05/2016.
 */
public interface IMaterial extends IIdentifiable {

    float getShineDamper();

    float getReflectivity();

    ITexture[] getTextures();
}
