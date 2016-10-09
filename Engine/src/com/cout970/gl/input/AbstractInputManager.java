package com.cout970.gl.input;


import com.cout970.gl.util.IClosable;
import com.cout970.gl.util.vector.Vector2;
import com.cout970.gl.window.Window;
import org.lwjgl.BufferUtils;
import org.lwjgl.glfw.GLFWCharCallback;
import org.lwjgl.glfw.GLFWKeyCallback;
import org.lwjgl.glfw.GLFWMouseButtonCallback;
import org.lwjgl.glfw.GLFWScrollCallback;

import java.nio.DoubleBuffer;

import static org.lwjgl.glfw.GLFW.*;

/**
 * Created by cout970 on 26/04/2016.
 */
public abstract class AbstractInputManager implements IClosable {

    private Window window;
    private GLFWKeyCallback keyCallback;
    private GLFWMouseButtonCallback mouseButtonCallback;
    private GLFWScrollCallback scrollCallback;
    private GLFWCharCallback charCallback;

    public AbstractInputManager(Window w){
        this.window = w;
        glfwSetKeyCallback(w.getID(), keyCallback = new GLFWKeyCallback() {
            @Override
            public void invoke(long window, int keycode, int scancode, int action, int mods) {
                onEvent(new KeyboardEvent(keycode, scancode, action, mods));
            }
        });

        glfwSetMouseButtonCallback(w.getID(), mouseButtonCallback = new GLFWMouseButtonCallback() {
            @Override
            public void invoke(long window, int button, int action, int mods) {
                onEvent(new MouseButtonEvent(button, action, mods));
            }
        });

        glfwSetScrollCallback(w.getID(), scrollCallback = new GLFWScrollCallback() {
            @Override
            public void invoke(long window, double offsetX, double offsetY) {
                onEvent(new ScrollEvent(offsetX, offsetY));
            }
        });

        glfwSetCharCallback(w.getID(), charCallback = new GLFWCharCallback() {
            @Override
            public void invoke(long window, int code) {
                onEvent(new CharEvent(code));
            }
        });
    }

    public boolean isMouseButtonPressed(MouseButtonEvent.MouseButton button){
        return glfwGetMouseButton(window.getID(), button.getID()) == GLFW_PRESS;
    }

    public boolean isKeyPressed(int key){
        return glfwGetKey(window.getID(), key) == GLFW_PRESS;
    }

    public Vector2 getMousePosition(){
        DoubleBuffer x = BufferUtils.createDoubleBuffer(1);
        DoubleBuffer y = BufferUtils.createDoubleBuffer(1);
        glfwGetCursorPos(window.getID(), x, y);
        return new Vector2(x.get(), y.get());
    }

    @Override
    public void close(){
        release();
    }

    public void release(){
        keyCallback.close();
        mouseButtonCallback.close();
        scrollCallback.close();
        charCallback.close();
    }

    public abstract void onEvent(AbstractInputEvent event);
}
