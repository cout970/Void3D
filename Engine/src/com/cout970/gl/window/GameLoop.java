package com.cout970.gl.window;

import com.cout970.gl.util.ITickeable;
import com.cout970.gl.util.Timer;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;

/**
 * Created by cout970 on 26/04/2016.
 */
public abstract class GameLoop implements ITickeable {

    private boolean run;
    protected Window window;
    private Timer timer;

    public GameLoop(Window window) {
        this.window = window;
        timer = new Timer();
    }

    public void run() {
        run = true;

        while (isRunning()) {
            window.tick();
            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
            glClearDepth(1.0);
            glEnable(GL_DEPTH_TEST);
            glEnable(GL_CULL_FACE);

            timer.loopTick();

            tick();

            //actualizacion de frame
            glFlush();
            glfwSwapBuffers(window.getID());
            //gestion de eventos como el teclado
            glfwPollEvents();
            if (glfwWindowShouldClose(window.getID())) { stop(); }
        }
    }

    public void stop() {
        run = false;
    }

    public Window getWindow() {
        return window;
    }

    public Timer getTimer() {
        return timer;
    }

    public boolean isRunning() {
        return run;
    }
}
