package com.cout970.editor.gui

import com.cout970.editor.Editor
import com.cout970.editor.util.FocusManager
import java.awt.Component
import java.awt.Container
import java.awt.event.FocusEvent
import java.awt.event.FocusListener
import java.awt.event.WindowEvent
import javax.swing.JDialog
import javax.swing.JFrame
import javax.swing.JTextField

/**
 * Created by cout970 on 08/06/2016.
 */
class CustomWindow : JDialog(MainFrame) {

    fun setProperties() {
        isFocusable = false
        isModal = false
    }

    fun postInit() {
        isVisible = true
        focusableWindowState = false
        for (i in components) {
            apply(i)
        }
    }

    fun apply(c: Component) {
        if (c is JTextField) {
            focusableWindowState = true
        }
        c.addFocusListener(object : FocusListener {
            override fun focusLost(e: FocusEvent?) {
                FocusManager.onComponentChangeFocus(c, false)
            }

            override fun focusGained(e: FocusEvent?) {
                if (e!!.oppositeComponent != null || Editor.window.isFocused) {
                    FocusManager.onComponentChangeFocus(c, true)
                }
            }
        })
        if (c is Container) {
            for (i in c.components) {
                apply(i)
            }
        }
    }

    override fun processWindowEvent(e: WindowEvent) {
        if (e.id == WindowEvent.WINDOW_CLOSING) {
            isVisible = false
        } else {
            super.processWindowEvent(e)
        }
    }
}

object MainFrame : JFrame("Main Frame") {

    init {
        isFocusable = false
        isFocusableWindow
    }
}