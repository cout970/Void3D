package com.cout970.editor.gui

import com.cout970.editor.Editor
import com.cout970.editor.modeltree.ModelTree
import javax.swing.JPanel

object WindowModelTree : ExternalWindow() {
    private val window = CustomWindow()
    var control_: ControlPanelModelTree? = null

    init {
        window.contentPane = root
        window.isUndecorated = true
        window.isResizable = false
        window.setProperties()
        window.pack()
        window.setLocation(Editor.window.screenSize().xi - 190, 303 + 24)
        window.postInit()
    }

    override fun onClose() {
        window.isVisible = false
    }

    override fun getLabel(): String? = "Model Tree"

    override public fun getContentPanel(): JPanel? {
        if (control_ == null) {
            control_ = ControlPanelModelTree()
        }
        return control_!!.root
    }

    override fun getWindow(): CustomWindow = window

    fun update(modelTree: ModelTree) {
        control_!!.update(modelTree)
        window.repaint()
    }
}