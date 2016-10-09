package com.cout970.editor.gui

import javax.swing.JPanel

/**
 * Created by cout970 on 01/07/2016.
 */
object WindowMenu : ExternalWindow() {

    private val window = CustomWindow()
    var control_: ControlPanelMenu? = null

    init {
        window.contentPane = root
        window.isUndecorated = true
        window.isResizable = false
        window.setProperties()
        window.pack()
        window.setLocation(0, 24)
        window.postInit()
    }

    override fun onClose() {
        window.isVisible = false
    }

    override fun getLabel(): String? = "Menu"

    fun getControl(): ControlPanelMenu {
        if (control_ == null) {
            control_ = ControlPanelMenu()
        }
        return control_!!
    }

    override fun getContentPanel(): JPanel? = getControl().root

    override fun getWindow(): CustomWindow = window
}