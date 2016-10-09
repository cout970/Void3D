package com.cout970.editor.history

/**
 * Created by cout970 on 01/07/2016.
 */
class ActionSaveModel : IAction {

    override fun doAction() {}

    override fun undoAction() {}

    override fun canBeIgnored(): Boolean = true
}