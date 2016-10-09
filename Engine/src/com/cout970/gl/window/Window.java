package com.cout970.gl.window;

import com.cout970.gl.util.IClosable;
import com.cout970.gl.util.ITickeable;
import com.cout970.gl.util.vector.Vector2;
import com.cout970.gl.util.vector.Vector3;
import org.lwjgl.BufferUtils;
import org.lwjgl.glfw.GLFWFramebufferSizeCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;

import java.nio.IntBuffer;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryUtil.NULL;

/**
 * Created by cout970 on 26/04/2016.
 */
public abstract class Window implements IClosable, ITickeable {

    private static boolean initGL = false;
    private long id;
    private Vector2 size;
    private FrameBufferSizeCallback resizeListener;
    private IWindowProperties properties;

    public Window(IWindowProperties prop) {
        this.properties = prop;
        init();
    }

    public void init(){
        if (initGL) {
            create();
        } else {
            //inicia el gestor de ventanas
            if (!glfwInit()) {
                throw new IllegalStateException("Unable to initialize GLFW");
            }
            create();
            initOpenGL();
        }
    }

    private void initOpenGL() {
        //inicia openGL
        GL.createCapabilities();
        //obtiene el tamano del buffer de la ventana
        IntBuffer width = BufferUtils.createIntBuffer(1), height = BufferUtils.createIntBuffer(1);
        glfwGetFramebufferSize(id, width, height);

        size.set(width.get(0), height.get(0));

//        glEnable(GL13.GL_MULTISAMPLE);
        initGL = true;
    }

    public long getID() {
        return id;
    }

    public Vector2 getSize() {
        return size;
    }

    public void enableVSync() {
        //selecciona la ventana
        glfwMakeContextCurrent(id);
        //vsync
        glfwSwapInterval(1);
    }

    public void disableVSync() {
        //selecciona la ventana
        glfwMakeContextCurrent(id);
        //vsync
        glfwSwapInterval(0);
    }

    public void create() {

        //cargar propiedades por defecto
        glfwDefaultWindowHints();
        glfwWindowHint(GLFW_VISIBLE, GL_FALSE);
        glfwWindowHint(GLFW_RESIZABLE, GL_TRUE);
        glfwWindowHint(GLFW_DECORATED, GL_TRUE);
//        glfwWindowHint(GLFW_SAMPLES, 8);

        size = properties.getSize().copy();
        //crea la ventana
        id = glfwCreateWindow(size.getXi(), size.getYi(), properties.getTitle(), NULL, NULL);
        if (id == NULL) {
            throw new RuntimeException("Failed to create the GLFW window");
        }

        //obtiene el tamano de la pantalla
        GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());
        glfwSetWindowPos(id, (vidmode.width() - size.getXi()) / 2,
                (vidmode.height() - size.getYi()) / 2);

        //activa el gestor redimension de la ventana
        glfwSetFramebufferSizeCallback(id, resizeListener = new FrameBufferSizeCallback());

        //selecciona la ventana
        glfwMakeContextCurrent(id);
        //vsync
        glfwSwapInterval(1);

        //mostrar la ventana
        glfwShowWindow(id);
    }

    public void hide() {
        glfwHideWindow(id);
    }

    public void show() {
        glfwShowWindow(id);
    }

    public void setTitle(String title) {
        glfwSetWindowTitle(id, title);
    }

    @Override
    public void close() {
        glfwTerminate();
        resizeListener.close();
    }

    @Override
    public void tick() {
        Vector3 v = properties.getBackground();
        glClearColor(v.getXf(), v.getYf(), v.getZf(), 1.0f);
        glViewport(0, 0, getSize().getXi(), getSize().getYi());
    }

    public void iconify() {
        glfwIconifyWindow(id);
    }

    public void maximize() {
        glfwMaximizeWindow(id);
    }

    public void focus() {
        glfwFocusWindow(id);
    }

    public void restore() {
        glfwRestoreWindow(id);
    }

    public boolean isFocused() {
        return glfwGetWindowAttrib(id, GLFW_FOCUSED) == GLFW_TRUE;
    }

    public void loseFocus() {
        glfwHideWindow(id);
        glfwShowWindow(id);
    }

    private class FrameBufferSizeCallback extends GLFWFramebufferSizeCallback {

        @Override
        public void invoke(long window, int x, int y) {
            if (x != 0 || y != 0) {
                size = new Vector2(x, y);
            }
            onChange(x, y);
        }
    }

    protected abstract void onChange(int x, int y);
}