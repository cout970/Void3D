package com.cout970.gl.resource.internal;

import com.cout970.gl.resource.IResourceLocation;

/**
 * Created by cout970 on 26/04/2016.
 */
public class ResourceLocation implements IResourceLocation {

    private String domain;
    private String path;

    public ResourceLocation(String domain, String path) {
        this.domain = domain;
        this.path = path;
    }

    @Override
    public String getDomain() {
        return domain;
    }

    @Override
    public String getPath() {
        return path;
    }

    @Override
    public String toString() {
        return "ResourceLocation{" +
                "domain='" + domain + '\'' +
                ", path='" + path + '\'' +
                '}';
    }
}
