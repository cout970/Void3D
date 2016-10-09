package com.cout970.gl.util;

import org.lwjgl.glfw.GLFW;

import static org.lwjgl.glfw.GLFW.glfwGetTime;

public class Timer {

    private double lastSecond;
    private int fpsCount;
    private int lastFpsCount;
    private double delta;
    private double time;

    public Timer() {
        lastSecond = GLFW.glfwGetTime();
    }

    public void loopTick() {
        delta = glfwGetTime() - time;
        time = glfwGetTime();

        fpsCount++;
        if (time - lastSecond >= 1) {
            lastFpsCount = fpsCount;
            fpsCount = 0;
            lastSecond = time;
        }
    }

    public int getFPS() {
        return lastFpsCount;
    }

    public double getDelta() {
        return delta;
    }

    public double getSecTime() {
        return glfwGetTime();
    }

    public double getMiliTime() {
        return glfwGetTime()/1000;
    }

    public double getNanoTime() {
        return glfwGetTime()/(1000*1000*1000);
    }
}
