package com.cout970.editor.gui

import com.cout970.editor.Editor
import com.cout970.editor.config.Config
import com.cout970.editor.export.MCMExportHandler
import com.cout970.editor.export.MCMImportHandler
import com.cout970.editor.export.ObjExportHandler
import com.cout970.editor.export.TechneImportHandler
import com.cout970.editor.history.*
import com.cout970.editor.modeltree.AbstractTreeNode
import com.cout970.editor.modeltree.Cube
import com.cout970.editor.modeltree.Group
import com.cout970.editor.texture.TextureExportUtil
import com.cout970.gl.input.MouseButtonEvent
import com.cout970.gl.raytrace.IRayObstacle
import com.cout970.gl.raytrace.ProjectionHelper
import com.cout970.gl.raytrace.RayTraceResult
import com.cout970.gl.util.vector.Vector2
import com.cout970.gl.util.vector.Vector3
import org.lwjgl.glfw.GLFW
import java.io.File
import java.util.function.Function
import java.util.function.Predicate
import javax.swing.JOptionPane

/**
 * Created by cout970 on 14/06/2016.
 */

// copy/paste
fun copy(){
    if(Editor.modelTree.selectedPart != null){
        Editor.modelTree.clipboard = Editor.modelTree.selectedPart!!.copy()
    }else{
        Editor.modelTree.clipboard = null
    }
}

fun paste(){
    if(Editor.modelTree.clipboard != null){
        Editor.historyTable.run(ActionPaste(Editor.modelTree.clipboard!!.copy()))
    }
}

fun cut(){
    if(Editor.modelTree.selectedPart != null){
        Editor.modelTree.clipboard = Editor.modelTree.selectedPart!!.copy()
        deletePart()
    }
}

// undo/redo

fun undo() {
    Editor.historyTable.undo()
}

fun redo() {
    Editor.historyTable.redo()
}

// menu buttons

fun newModel() {
    var result = true
    if(Editor.historyTable.hasChangesFromLastSave()){
        result = showResetWarning() == JOptionPane.OK_OPTION
    }
    if(result){
        Editor.modelTree.reset()
    }
}

fun importModel() {
    val file = showImportPopup()
    if (file != null) {
        var successful = false
        if (file.name.endsWith(".tcn")) {
            successful = TechneImportHandler.import(file, Editor.modelTree)
        } else if (file.name.endsWith(".mcm")) {
            successful = MCMImportHandler.import(file, Editor.modelTree)
        }
        if (!successful) {
            JOptionPane.showMessageDialog(null, "Error importing the model")
        } else {
            lastSaveLocation = file
            Editor.modelTree.updateAll()
            Editor.modelRenderer.requestUpdate()
        }
    }
}

var lastSaveLocation: File? = null

fun saveModel() {
    if (lastSaveLocation == null) {
        saveModelAs()
    } else {
        save(lastSaveLocation!!)
    }
}

fun saveModelAs() {
    val file = showExportPopup()
    if (file != null) {
        save(file)
    }
}

private fun save(file: File) {
    var successful = false
    if (file.name.endsWith(".obj")) {
        successful = ObjExportHandler.export(file, Editor.modelTree, false)
    } else if (file.name.endsWith(".mcm")) {
        successful = MCMExportHandler.export(file, Editor.modelTree, false)
    }
    if (!successful) {
        JOptionPane.showMessageDialog(null, "Error exporting the model")
    } else {
        lastSaveLocation = file
        Editor.historyTable.run(ActionSaveModel())
    }
}

fun openOptions() {
    JOptionPane.showMessageDialog(null, "This feature is not implemented yet!")
}

fun toggleMenuWindow() {
    WindowMenu.window.isVisible = !WindowMenu.window.isVisible
}

fun toggleEditorWindow() {
    WindowEdit.window.isVisible = !WindowEdit.window.isVisible
}

fun toggleModelTreeWindow() {
    WindowModelTree.window.isVisible = !WindowModelTree.window.isVisible
}

fun toggleTextureWindow() {
    WindowTexture.window.isVisible = !WindowTexture.window.isVisible
}

fun toggleGridsWindow() {
    WindowGrids.window.isVisible = !WindowGrids.window.isVisible
}

// Hot keys

fun deletePart() {
    if (Editor.modelTree.selectedPart is Cube) {
        Editor.historyTable.run(ActionRemoveCube(Editor.modelTree.selectedPart as Cube))
    }
}

fun takeScreenShot() {
    TextureExportUtil.takeScreenshot(File("./screenshots"), Editor.window)
}

fun toggleGridX() {
    Config.showGridX = !Config.showGridX
}

fun toggleGridY() {
    Config.showGridY = !Config.showGridY
}

fun toggleGridZ() {
    Config.showGridZ = !Config.showGridZ
}

fun toggleSkybox() {
    Config.showSkybox = !Config.showSkybox
}

fun toggleBaseBlock() {
    Config.showBaseBlock = !Config.showBaseBlock
}

fun toggleDebugLines() {
    Config.showDebugLines = !Config.showDebugLines
}

// Editor Window

// Cube
fun newCube() {
    Editor.historyTable.run(ActionAddCube(Cube(), Editor.modelTree.selectedGroup))
}

fun resizeCube(model: Cube, size: Vector3) {
    Editor.historyTable.run(ActionResizeCube(model, size.copy()))
}

fun moveCube(model: Cube, pos: Vector3) {
    Editor.historyTable.run(ActionMoveCube(model, pos.copy()))
}

fun rotateCube(model: Cube, rotation: Vector3) {
    Editor.historyTable.run(ActionRotateCube(model, rotation.copy()))
}

fun moveRotationPointCube(model: Cube, point: Vector3) {
    Editor.historyTable.run(ActionMoveCubeRotationPoint(model, point.copy()))
}

fun changeTextureOffsetCube(model: Cube, point: Vector2) {
    Editor.historyTable.run(ActionChangeCubeTextureOffset(model, point.copy()))
}

fun changeNameCube(cube: Cube, name: String) {
    Editor.historyTable.run(ActionChangeCubeName(cube, name))
}

fun flipUVCube(cube: Cube, flip: Boolean) {
    Editor.historyTable.run(ActionFlipCubeUV(cube, flip))
}

fun deleteCube(cube: Cube) {
    Editor.historyTable.run(ActionRemoveCube(cube))
}

// Group

fun newGroup() {
    Editor.historyTable.run(ActionAddGroup(Group(), Editor.modelTree.selectedGroup))
}

fun resizeGroup(model: Group, size: Vector3) {
    Editor.historyTable.run(ActionResizeGroup(model, size.copy()))
}

fun moveGroup(model: Group, pos: Vector3) {
    Editor.historyTable.run(ActionMoveGroup(model, pos.copy()))
}

fun rotateGroup(model: Group, rotation: Vector3) {
    Editor.historyTable.run(ActionRotateGroup(model, rotation.copy()))
}

fun moveRotationPointGroup(model: Group, point: Vector3) {
    Editor.historyTable.run(ActionMoveGroupRotationPoint(model, point.copy()))
}

fun changeNameGroup(cube: Group, name: String) {
    Editor.historyTable.run(ActionChangeGroupName(cube, name))
}

fun deleteGroup(group: Group) {
    Editor.historyTable.run(ActionRemoveGroup(group))
}

// Mouse click
var lastClick = 0.0

fun onMouseClick(event: MouseButtonEvent) {
    if (event.button == MouseButtonEvent.MouseButton.LEFT && event.action == GLFW.GLFW_PRESS) {
        selectCube()
    } else if ((event.button == MouseButtonEvent.MouseButton.MIDDLE || event.button == MouseButtonEvent.MouseButton.RIGHT) && event.action == GLFW.GLFW_PRESS) {
        val time = Editor.gameLoop.timer.secTime
        if (time - lastClick < 0.25) {
            jumpToCube()
        }
        lastClick = time
    }
}

fun selectCube() {
    val ray = ProjectionHelper.getRay(Editor.inputManager.mousePosition)
    var result: AbstractTreeNode? = null
    var hit: RayTraceResult? = null
    var bestDist = -1.0
    Editor.modelTree.iterate(Function {
        val r = (it as IRayObstacle).rayTrace(ray)
        if (r != null) {
            val d = ray.start.distance(r.hit)
            if (bestDist < 0 || d < bestDist) {
                bestDist = d
                hit = r
                result = it
            }
        }
    }, Predicate { it is IRayObstacle })

    Editor.modelTree.selectPart(result)
}

fun jumpToCube() {
    val ray = ProjectionHelper.getRay(Editor.inputManager.mousePosition)
    var result: AbstractTreeNode? = null
    var hit: RayTraceResult? = null
    var bestDist = -1.0
    Editor.modelTree.iterate(Function {
        val r = (it as IRayObstacle).rayTrace(ray)
        if (r != null) {
            val d = ray.start.distance(r.hit)
            if (bestDist < 0 || d < bestDist) {
                bestDist = d
                hit = r
                result = it
            }
        }
    }, Predicate { it is IRayObstacle })
    if (hit != null) {
        Editor.camera.position = hit!!.hit.inverse()
    }
}