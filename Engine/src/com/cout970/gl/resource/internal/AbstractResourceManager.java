package com.cout970.gl.resource.internal;

import com.cout970.gl.resource.IResource;
import com.cout970.gl.resource.IResourceLocation;
import com.cout970.gl.resource.IResourceManager;
import com.cout970.gl.util.IClosable;

import java.io.File;

/**
 * Created by cout970 on 28/04/2016.
 */
public class AbstractResourceManager implements IResourceManager, IClosable {

    @Override
    public IResource getResource(IResourceLocation loc) {
        return new AbstractResourceFile(loc) {
            @Override
            protected File toFile(IResourceLocation loc) {
                return new File("./res/"+loc.getPath());
            }
        };
    }

    @Override
    public void close() {}
}
