package com.cout970.editor.history

import com.cout970.editor.Editor
import com.cout970.editor.modeltree.Cube
import com.cout970.editor.modeltree.Group

/**
 * Created by cout970 on 15/06/2016.
 */
class ActionRemoveGroup(val group: Group) :IAction {

    val subActions = mutableListOf<IAction>()

    init{
        for(i in group.subParts){
            if(i is Cube){
                subActions.add(ActionRemoveCube(i))
            }else if(i is Group){
                subActions.add(ActionRemoveGroup(i))
            }
        }
    }

    override fun doAction() {
        for(i in subActions){
            i.doAction()
        }
        Editor.modelTree.removePart(group, group.parent!!)
    }

    override fun undoAction() {
        Editor.modelTree.addPart(group, group.parent!!)
        for(i in subActions){
            i.undoAction()
        }
    }
}