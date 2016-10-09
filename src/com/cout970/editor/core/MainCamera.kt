package com.cout970.editor.core

import com.cout970.editor.Editor
import com.cout970.editor.config.Config
import com.cout970.gl.camera.PerspectiveCamera
import com.cout970.gl.input.AbstractInputEvent
import com.cout970.gl.input.MouseButtonEvent
import com.cout970.gl.input.ScrollEvent
import com.cout970.gl.util.vector.Vector2
import com.cout970.gl.util.vector.Vector3

/**
 * Created by cout970 on 30/05/2016.
 */
class MainCamera() : PerspectiveCamera(Math.toRadians(60.0).toFloat(), 0.01f, 100000f) {

    var distance = 70.0

    init {
        rotatePitch(30f)
        rotateYaw(-45f)
        translate(Vector3(-8, -8, -8))
    }

    fun event(event: AbstractInputEvent?) {
        if (event is ScrollEvent) {
            distance = Math.max(0.0, distance - event.offsetY * 8)
        }
    }

    fun checkInput() {
        if (Editor.inputManager.isMouseButtonPressed(MouseButtonEvent.MouseButton.MIDDLE)) {

            val axisX = Vector2(Math.cos(Math.toRadians(rotation.yaw.toDouble())), Math.sin(Math.toRadians(rotation.yaw.toDouble())))
            var axisY = Vector2(Math.cos(Math.toRadians(rotation.yaw.toDouble() - 90)), Math.sin(Math.toRadians(rotation.yaw.toDouble() - 90)))
            axisY = axisY.mul(Math.sin(Math.toRadians(rotation.pitch.toDouble())))
            var a = Vector3(axisX.x, 0.0, axisX.y)
            var b = Vector3(axisY.x, Math.cos(Math.toRadians(rotation.pitch.toDouble())), axisY.y)
            a = a.normalize().mul(-Editor.inputManager.mouseDiff.x * Config.translationSpeedX * Editor.gameLoop.timer.delta)
            b = b.normalize().mul(Editor.inputManager.mouseDiff.y * Config.translationSpeedY * Editor.gameLoop.timer.delta)
            translate(a)
            translate(b)

        } else if (Editor.inputManager.isMouseButtonPressed(MouseButtonEvent.MouseButton.RIGHT)) {
            rotateYaw((-Editor.inputManager.mouseDiff.x * Config.rotationSpeedX * Editor.gameLoop.timer.delta).toFloat())
            rotatePitch((-Editor.inputManager.mouseDiff.y * Config.rotationSpeedY * Editor.gameLoop.timer.delta).toFloat())
        }
    }
}