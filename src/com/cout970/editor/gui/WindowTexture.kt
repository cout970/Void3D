package com.cout970.editor.gui

import com.cout970.editor.Editor
import javax.swing.JPanel

object WindowTexture : ExternalWindow(){
    private val window = CustomWindow()

    init{
        window.contentPane = root
        window.isUndecorated = true
        window.isResizable = false
        window.setProperties()
        window.pack()
        window.setLocation(Editor.window.screenSize().xi - 190, 24)
        window.postInit()
    }

    override fun onClose() {
        window.isVisible = false
    }

    override fun getLabel(): String? = "Texture"

    override fun getContentPanel(): JPanel? = ControlPanelTexture().root

    override fun getWindow(): CustomWindow = window
}