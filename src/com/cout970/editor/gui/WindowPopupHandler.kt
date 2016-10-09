package com.cout970.editor.gui

import com.cout970.editor.Editor
import com.cout970.editor.util.FocusManager
import java.io.File
import javax.swing.JFileChooser
import javax.swing.JFrame
import javax.swing.JOptionPane
import javax.swing.filechooser.FileFilter

/**
 * Created by cout970 on 13/06/2016.
 */

val frame: JFrame? = MainFrame

// Model Deletion warning
fun showResetWarning() = JOptionPane.showConfirmDialog(JFrame().apply { isAlwaysOnTop = true; toFront() }, "Any unsaved changes will be lost\nAre you sure you want to continue?")

// Import/Export
var lastOpenedFolder = File("./")

fun showImportPopup(): File? {
    FocusManager.hideMainWindow()
    Editor.window.hide()
    var file: File? = null

    val result = !Editor.historyTable.hasChangesFromLastSave() || showResetWarning() == JOptionPane.OK_OPTION
    if (result) {
        val chooser = JFileChooser(lastOpenedFolder)
        frame?.isAlwaysOnTop = true
        val option = chooser.showOpenDialog(frame)
        if (option == JFileChooser.APPROVE_OPTION) {
            lastOpenedFolder = chooser.currentDirectory
            file = chooser.selectedFile
        }
    }
    Editor.window.show()
    FocusManager.showMainWindow()
    return file
}

fun showExportPopup(): File? {
    Editor.window.hide()
    var file: File? = null

    val chooser = JFileChooser(lastOpenedFolder)
    chooser.fileFilter = object : Filter(1) {
        override fun accept(f: File): Boolean {
            return f.name.endsWith(".obj") || f.isDirectory
        }

        override fun getDescription(): String {
            return "OBJ model format .obj"
        }
    }
    chooser.fileFilter = object : Filter(2) {
        override fun accept(f: File): Boolean {
            return f.name.endsWith(".mcm") || f.isDirectory
        }

        override fun getDescription(): String {
            return "MineCraft Model format .mcm"
        }
    }

    frame?.isAlwaysOnTop = true
    val option = chooser.showSaveDialog(frame)
    if (option == JFileChooser.APPROVE_OPTION) {
        val f = chooser.selectedFile
        lastOpenedFolder = chooser.currentDirectory
        when ((chooser.fileFilter as Filter).id) {
            1 -> {//obj
                if (!f.name.endsWith(".obj")) {
                    file = File(f.absolutePath + ".obj")
                } else
                    file = f
            }
            2 -> {//mcm
                if (!f.name.endsWith(".mcm")) {
                    file = File(f.absolutePath + ".mcm")
                } else
                    file = f
            }
        }
    }
    Editor.window.show()
    return file
}

// Texture import/export

fun showTextureLoadPopup(): File? {
    Editor.window.hide()
    var file: File? = null

    val chooser = JFileChooser(lastOpenedFolder)
    chooser.fileFilter = object : Filter(0) {
        override fun accept(f: File): Boolean {
            return f.name.endsWith(".png") || f.isDirectory
        }

        override fun getDescription(): String {
            return "PNG Texture"
        }
    }
    frame?.isAlwaysOnTop = true
    val option = chooser.showOpenDialog(frame)
    if (option == JFileChooser.APPROVE_OPTION) {
        lastOpenedFolder = chooser.currentDirectory
        file = chooser.selectedFile
    }
    Editor.window.show()
    return file
}

fun showTextureExportPopup(): File? {
    Editor.window.hide()
    val chooser = JFileChooser(lastOpenedFolder)
    var file: File? = null
    frame?.isAlwaysOnTop = true
    val option = chooser.showSaveDialog(frame)
    if (option == JFileChooser.APPROVE_OPTION) {
        lastOpenedFolder = chooser.currentDirectory
        file = chooser.selectedFile
        file.parentFile.mkdirs()
        if (!file.name.endsWith(".png")) {
            file = File(file.parent, file.name + ".png")
        }
    }
    Editor.window.show()
    return file
}

private abstract class Filter(val id: Int) : FileFilter()