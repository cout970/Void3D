package com.cout970.gl.texture;

import com.cout970.gl.resource.IResource;

/**
 * Created by cout970 on 26/04/2016.
 */
public interface ITextureLoader {

    ITexture loadTexture(IResource res);

    ITexture loadTextureCube(IResource[] res);
}
