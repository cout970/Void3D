package com.cout970.editor

import com.cout970.editor.util.Log
import javax.swing.JFrame
import javax.swing.UIManager
import javax.swing.UnsupportedLookAndFeelException

fun main(args: Array<String>) {
    Log.info("Starting program")
    Log.info("Setting the default look and feel")
    try {
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName())
    } catch (e: ClassNotFoundException) {
        Log.printStackTrace(e)
    } catch (e: InstantiationException) {
        Log.printStackTrace(e)
    } catch (e: IllegalAccessException) {
        Log.printStackTrace(e)
    } catch (e: UnsupportedLookAndFeelException) {
        Log.printStackTrace(e)
    }

    Log.info("Decorating Windows")
    JFrame.setDefaultLookAndFeelDecorated(true)

    try {
        Log.info("Starting Editor")
        Editor.start()
    } catch(e: Exception) {
        Log.info("Error with the editor")
        Log.printStackTrace(e)
    }
    Log.info("End of log")
    Log.writer.flush()
    Log.storeLog()
    System.exit(0)
}