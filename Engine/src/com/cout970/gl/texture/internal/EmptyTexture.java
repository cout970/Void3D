package com.cout970.gl.texture.internal;

import com.cout970.gl.resource.IResourceLocation;
import com.cout970.gl.resource.internal.ResourceLocation;
import com.cout970.gl.texture.ITexture;
import com.cout970.gl.util.vector.Vector2;

/**
 * Created by cout970 on 02/05/2016.
 */
public class EmptyTexture implements ITexture {

    @Override
    public Vector2 getSize() {
        return new Vector2(1,1);
    }

    @Override
    public IResourceLocation getLocation() {
        return new ResourceLocation("internal", "empty");
    }

    @Override
    public String getName() {
        return "emptyTexture";
    }

    @Override
    public int getID() {
        return 0;
    }
}
