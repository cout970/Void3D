package com.cout970.editor.history

/**
 * Created by cout970 on 14/06/2016.
 */
class HistoryTable {

    val actionHistory = mutableListOf<IAction>()
    val undoHistory = mutableListOf<IAction>()
    var changes = false

    fun run(action: IAction) {
        changes = true
        actionHistory.add(action)
        if(!action.canBeIgnored()) {
            undoHistory.clear()
        }
        action.doAction()
    }

    fun undo() {
        val last = removeLastAction(actionHistory)
        if(last != null) {
            changes = true
            undoHistory.add(last)
            last.undoAction()
        }
    }

    fun redo() {
        val last = removeLastAction(undoHistory)
        if(last != null) {
            changes = true
            actionHistory.add(last)
            last.redoAction()
        }
    }

    fun removeLastAction(list: MutableList<IAction>): IAction? {
        if(list.isEmpty())return null
        val last = list.last()
        if(last.canBeIgnored()){
            list.removeAt(list.size - 1)
            return removeLastAction(list)
        }else{
            list.removeAt(list.size - 1)
            return last
        }
    }

    fun hasChangesFromLastSave(): Boolean = !actionHistory.isEmpty() && actionHistory.last() !is ActionSaveModel
}