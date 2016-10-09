package com.cout970.editor.gui

import com.cout970.editor.modeltree.Cube
import com.cout970.editor.modeltree.Group
import javax.swing.JPanel

object WindowEdit : ExternalWindow() {
    private val window = CustomWindow()
    var control_: ControlPanelCube? = null

    init {
        window.contentPane = root
        window.isUndecorated = true
        window.isResizable = false
        window.setProperties()
        window.pack()
        window.setLocation(0, 24 + 65)
        window.postInit()
    }

    override fun onClose() {
        window.isVisible = false
    }

    override fun getLabel(): String? = "Group/Cube"

    fun getControl(): ControlPanelCube {
        if (control_ == null) {
            control_ = ControlPanelCube()
        }
        return control_!!
    }

    override fun getContentPanel(): JPanel? = getControl().root

    override fun getWindow(): CustomWindow = window

    fun unSelectCube() {
        getControl().unselectCube()
    }

    fun selectCube(model: Cube) {
        getControl().selectCube(model)
    }

    fun selectGroup(model: Group) {
        getControl().selectGroup(model)
    }
}