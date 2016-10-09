package com.cout970.editor.gui

import javax.swing.JPanel

/**
 * Created by cout970 on 25/06/2016.
 */
object WindowGrids : ExternalWindow() {

    private val window = CustomWindow()
    var control_: ControlPanelGrids? = null

    init {
        window.contentPane = root
        window.isUndecorated = true
        window.isResizable = false
        window.setProperties()
        window.pack()
        window.setLocation(0, 423+24+65)
        window.postInit()
    }

    override fun onClose() {
        window.isVisible = false
    }

    override fun getLabel(): String? = "Grids"

    fun getControl(): ControlPanelGrids {
        if (control_ == null) {
            control_ = ControlPanelGrids()
        }
        return control_!!
    }

    override fun getContentPanel(): JPanel? = getControl().root

    override fun getWindow(): CustomWindow = window
}