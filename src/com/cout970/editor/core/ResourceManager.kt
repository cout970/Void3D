package com.cout970.editor.core

import com.cout970.editor.util.Log
import com.cout970.gl.resource.IResource
import com.cout970.gl.resource.IResourceLocation
import com.cout970.gl.resource.internal.AbstractResourceFile
import com.cout970.gl.resource.internal.AbstractResourceManager
import java.io.File
import java.io.InputStream

/**
 * Created by cout970 on 30/05/2016.
 */

class ResourceManager : AbstractResourceManager() {

    val EXTERNAL_RESOURCE = "external"

    override fun getResource(loc: IResourceLocation): IResource {
        if (loc.domain == EXTERNAL_RESOURCE) {
            return object : AbstractResourceFile(loc) {
                override fun toFile(loc: IResourceLocation): File {
                    return File(loc.path)
                }
            }
        }
        return InternalResource(loc.domain, loc)
    }

    private class InternalResource(val name_: String, val res: IResourceLocation) : IResource {

        override fun getLastModifiedTime(): Long = -1

        override fun getLocation(): IResourceLocation? = res

        override fun getInputStream(): InputStream? {
            val stream =  Thread.currentThread().contextClassLoader.getResourceAsStream("assets/${res.domain}/${res.path}")
            if(stream == null){
                Log.error("Error trying to load resource at: $location")
            }
            return stream
        }

        override fun getName(): String? = name_
    }
}

