package com.cout970.gl.resource.internal;

import com.cout970.gl.resource.IResource;
import com.cout970.gl.resource.IResourceLocation;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

/**
 * Created by cout970 on 26/04/2016.
 */
public abstract class AbstractResourceFile implements IResource {

    private File file;
    private IResourceLocation loc;

    public AbstractResourceFile(IResourceLocation loc) {
        this.loc = loc;
        this.file = toFile(loc);
    }

    protected abstract File toFile(IResourceLocation loc);

    @Override
    public IResourceLocation getLocation() {
        return loc;
    }

    @Override
    public InputStream getInputStream() throws FileNotFoundException {
        return new FileInputStream(file);
    }

    @Override
    public String getName() {
        return file.getName();
    }

    @Override
    public long getLastModifiedTime(){
        return file.lastModified();
    }
}
