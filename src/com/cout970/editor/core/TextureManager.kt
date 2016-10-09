package com.cout970.editor.core

import com.cout970.editor.Editor
import com.cout970.editor.gui.WindowTexture
import com.cout970.editor.texture.TextureLoader
import com.cout970.editor.util.Log
import com.cout970.gl.model.BasicMaterial
import com.cout970.gl.resource.IResource
import com.cout970.gl.resource.internal.ResourceLocation
import com.cout970.gl.texture.internal.AbstractTextureLoader
import java.awt.Image
import javax.imageio.ImageIO

/**
 * Created by cout970 on 30/05/2016.
 */
class TextureManager : AbstractTextureLoader() {

    var linear = false
    var modelTextureLocation = ResourceLocation(Editor.PROJECT_NAME.toLowerCase(), "empty.png")
    var lastModified = 0L
    var modelTextureImage: Image? = null
    private var refreshMainTexture = true

    override fun useLinear(res: IResource?): Boolean = linear

    override fun close() {
    }

    fun updateModelTexture() {
        try {
            val resource = Editor.resourceManager.getResource(modelTextureLocation)
            if (resource.lastModifiedTime != lastModified) {
                Log.info("Updating model texture")
                lastModified = resource.lastModifiedTime
                val tex = Editor.textureManager.loadTexture(resource)
                if (refreshMainTexture) {
                    Log.info("Updating texture scale from ${TextureLoader.textureScale} to ${tex.size.x}")
                    TextureLoader.textureScale = tex.size.x
                }
                TextureLoader.modelTexture = BasicMaterial(tex)
                val input = resource.inputStream
                modelTextureImage = ImageIO.read(input)
                input.close()
                WindowTexture.window.repaint()
                if (refreshMainTexture) {
                    refreshMainTexture = false
                    Editor.modelTree.updateAll()
                    Editor.modelRenderer.requestUpdate()
                }
            }
        } catch(e: Exception) {
            Log.printStackTrace(e)
        }
    }

    fun refreshMainTexture() {
        refreshMainTexture = true
    }
}