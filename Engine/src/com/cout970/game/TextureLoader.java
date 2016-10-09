package com.cout970.game;

import com.cout970.gl.resource.IResource;
import com.cout970.gl.texture.internal.AbstractTextureLoader;

/**
 * Created by cout970 on 01/05/2016.
 */
public class TextureLoader extends AbstractTextureLoader {

    private boolean useLinear;

    @Override
    protected boolean useLinear(IResource res) {
        return useLinear;
    }

    public boolean isUseLinear() {
        return useLinear;
    }

    public void setUseLinear(boolean useLinear) {
        this.useLinear = useLinear;
    }

    @Override
    public void close() {

    }
}
