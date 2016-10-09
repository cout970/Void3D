package com.cout970.gl.texture;

import com.cout970.gl.util.IIdentifiable;
import com.cout970.gl.util.ILocable;
import com.cout970.gl.util.IName;
import com.cout970.gl.util.vector.Vector2;

/**
 * Created by cout970 on 26/04/2016.
 */
public interface ITexture extends IName, ILocable, IIdentifiable {

    Vector2 getSize();
}
