package com.cout970.editor.history

import com.cout970.editor.modeltree.Cube

/**
 * Created by cout970 on 14/06/2016.
 */
class ActionChangeCubeName(val cube: Cube, val name:String)  :IAction{

    val oldName = cube.name_

    override fun doAction() {
        cube.name_ = name
    }

    override fun undoAction() {
        cube.name_ = oldName
    }
}