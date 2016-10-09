package com.cout970.editor.history

/**
 * Created by cout970 on 14/06/2016.
 */
interface IAction {

    fun doAction()

    fun undoAction()

    fun redoAction() = doAction()

    fun canBeIgnored() = false
}