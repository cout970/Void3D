package com.cout970.editor.texture

import com.cout970.editor.Editor
import com.cout970.editor.util.Log
import com.cout970.gl.model.BasicMaterial
import com.cout970.gl.model.IMaterial
import com.cout970.gl.resource.internal.ResourceLocation
import com.cout970.gl.texture.ITexture
import com.cout970.gl.util.Timer

/**
 * Created by cout970 on 27/06/2016.
 */
object TextureLoader {

    //resources
    var modelTexture: IMaterial? = null
    var textureScale = 128.0
    var selectionTexture: ITexture? = null
    lateinit var baseCubeTexture: ITexture
    lateinit var compassRoseTexture: ITexture
    lateinit var compassRoseBottomTexture: ITexture
    lateinit var centerTexture: ITexture
    lateinit var rotationPointTexture: ITexture
    lateinit var arrowsTexture: ITexture
    lateinit var rotationPointMaterial: IMaterial

    var textureUpdateCounter = 0.0

    fun loadTextures() {
        Log.info("Loading texture for the cube in the base")
        baseCubeTexture = loadTexture("base_cube.png")

        Log.info("Loading textures for the compass rose")
        compassRoseTexture = loadTexture("compass_rose.png")

        Log.info("Loading textures for the compass rose bottom")
        compassRoseBottomTexture = loadTexture("compass_rose_bottom.png")

        Log.info("Loading textures for the gui")
        centerTexture = loadTexture("center.png")

        Log.info("Loading selection texture")
        selectionTexture = loadTexture("selection.png")

        Log.info("Loading rotation point texture")
        rotationPointTexture = loadTexture("rotation_point.png")
        rotationPointMaterial = BasicMaterial(rotationPointTexture, 10f, 1f)

        Log.info("Loading arrows texture")
        arrowsTexture = loadTexture("arrows.png")

        Editor.textureManager.updateModelTexture()
    }

    fun loadTexture(name: String): ITexture = Editor.textureManager.loadTexture(Editor.resourceManager.getResource(ResourceLocation(Editor.PROJECT_NAME.toLowerCase(), name)))

    fun tickTextures(timer: Timer) {
        textureUpdateCounter += timer.delta
        if (textureUpdateCounter > 1) {
            textureUpdateCounter = 0.0
            Editor.textureManager.updateModelTexture()
        }
    }
}