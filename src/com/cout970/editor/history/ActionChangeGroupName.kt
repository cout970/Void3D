package com.cout970.editor.history

import com.cout970.editor.modeltree.Group

/**
 * Created by cout970 on 14/06/2016.
 */
class ActionChangeGroupName(val group: Group, val name:String)  :IAction{

    val oldName = group.name_

    override fun doAction() {
        group.name_ = name
    }

    override fun undoAction() {
        group.name_ = oldName
    }
}