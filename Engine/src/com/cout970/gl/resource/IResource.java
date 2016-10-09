package com.cout970.gl.resource;

import com.cout970.gl.util.IName;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by cout970 on 26/04/2016.
 */
public interface IResource extends IName {

    IResourceLocation getLocation();

    InputStream getInputStream() throws IOException;

    long getLastModifiedTime();
}
