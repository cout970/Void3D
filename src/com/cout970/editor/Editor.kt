package com.cout970.editor

import com.cout970.editor.core.*
import com.cout970.editor.gui.*
import com.cout970.editor.history.HistoryTable
import com.cout970.editor.modeltree.ModelTree
import com.cout970.editor.renderer.GuiRenderer
import com.cout970.editor.renderer.ModelRenderer
import com.cout970.editor.renderer.SkyboxRenderer
import com.cout970.editor.renderer.SpaceRenderer
import com.cout970.editor.texture.TextureLoader
import com.cout970.editor.util.Log
import com.cout970.gl.light.BasicLight
import com.cout970.gl.util.vector.Vector3

/**
 * Created by cout970 on 30/05/2016.
 */

object Editor {

    const val PROJECT_NAME = "Void3D"

    //core
    val window = MainWindow()
    val gameLoop = GameLoop()
    val inputManager = InputManager()
    val resourceManager = ResourceManager()
    val textureManager = TextureManager()
    val modelTree = ModelTree()
    val historyTable = HistoryTable()
    //render
    var camera = MainCamera()
    val modelRenderer = ModelRenderer()
    val spaceRenderer = SpaceRenderer()
    val guiRenderer = GuiRenderer()
    val skyboxRenderer = SkyboxRenderer()
    val lightA = BasicLight(Vector3(500, 1000, 750), Vector3(1, 1, 1))
    val lightB = BasicLight(Vector3(-500, -1000, -750), Vector3(1, 1, 1))

    fun start() {

        Log.info("Creating Menu Window")
        WindowMenu
        Log.info("Creating Cube/Group Edit Window")
        WindowEdit
        Log.info("Creating Texture Window")
        WindowTexture
        Log.info("Creating Model Tree Window")
        WindowModelTree
        Log.info("Creating Grids Window")
        WindowGrids
        window.enableListeners = true
        window.bindSubWindows()

        TextureLoader.loadTextures()

        Log.info("Starting game loop")
        gameLoop.run()
    }
}