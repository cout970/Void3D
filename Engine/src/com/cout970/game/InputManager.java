package com.cout970.game;

import com.cout970.game.entity.EntityHouse;
import com.cout970.gl.camera.Camera;
import com.cout970.gl.input.AbstractInputEvent;
import com.cout970.gl.input.AbstractInputManager;
import com.cout970.gl.input.KeyboardEvent;
import com.cout970.gl.input.MouseButtonEvent;
import com.cout970.gl.raytrace.RayTraceResult;
import com.cout970.gl.util.vector.Vector2;
import com.cout970.gl.util.vector.Vector3;

import static org.lwjgl.glfw.GLFW.*;

/**
 * Created by cout970 on 01/05/2016.
 */
public class InputManager extends AbstractInputManager {

    private Game game;
    private Camera camera;
    private int brushSize = 8;

    public InputManager(Game game) {
        super(game.getWindow());
        this.camera = game.getCamera();
        this.game = game;
    }

    @Override
    public void onEvent(AbstractInputEvent event) {
        if (event instanceof MouseButtonEvent) {
            MouseButtonEvent e = (MouseButtonEvent) event;
            if (e.getAction() != GLFW_PRESS) { return; }
            if (e.getButton() == MouseButtonEvent.MouseButton.LEFT) {
                RayTraceResult r;
                r = game.getMap().rayTrace(game.getMouseRay());
                if (r != null) {
                    if (r.getExtraData() instanceof Vector2) {
                        Vector2 pos = (Vector2) r.getExtraData();
                        if (isKeyPressed(GLFW_KEY_H)) {
                            func(pos.getXi(), pos.getYi(), 8, 0f);
                            game.getMap().getWorldEntities().add(new EntityHouse(game.getMap(), new Vector3(pos.getX(), 0, pos.getY())));
                        } else {
                            func(pos.getXi(), pos.getYi(), brushSize, 0.1f);
                        }
                        game.getWorldRenderer().change();
                    }
                }
            } else if (e.getButton() == MouseButtonEvent.MouseButton.RIGHT) {
                RayTraceResult r;
                r = game.getMap().rayTrace(game.getMouseRay());
                if (r != null) {
                    if (r.getExtraData() instanceof Vector2) {
                        Vector2 pos = (Vector2) r.getExtraData();
                        func(pos.getXi(), pos.getYi(), brushSize, -0.1f);
                        game.getWorldRenderer().change();
                    }
                }
            } else {
                RayTraceResult r;
                r = game.getMap().rayTrace(game.getMouseRay());
                if (r != null) {
                    if (r.getExtraData() instanceof Vector2) {
                        Vector2 pos = (Vector2) r.getExtraData();
                        func2(pos.getXi(), pos.getYi(), brushSize);
                        game.getWorldRenderer().change();
                    }
                }
            }
        } else if (event instanceof KeyboardEvent) {
            KeyboardEvent e = (KeyboardEvent) event;
            if (e.getKeycode() == GLFW_KEY_P) {
                brushSize++;
            } else if (e.getKeycode() == GLFW_KEY_O) {
                brushSize--;
                if (brushSize < 1) { brushSize = 1; }
            } else if (e.getKeycode() == GLFW_KEY_0) {
                game.getMap().setDayTime(game.getMap().getDayTime() * 2);
            } else if (e.getKeycode() == GLFW_KEY_9) {
                game.getMap().setDayTime(game.getMap().getDayTime() / 2);
            }
        }
    }

    public void tick(float delta) {
        if (isKeyPressed(GLFW_KEY_X)) {
            Vector3 dir = camera.getHorizontalDirection().mul(-0.1 * delta);
            camera.translate(dir);
        }
        if (isKeyPressed(GLFW_KEY_Z)) {
            Vector3 dir = camera.getHorizontalDirection().mul(0.1 * delta);
            camera.translate(dir);
        }
        if (isKeyPressed(GLFW_KEY_C)) {
            camera.translate(new Vector3(-0.1 * delta, 0, 0));
        }
        if (isKeyPressed(GLFW_KEY_V)) {
            camera.translate(new Vector3(0.1 * delta, 0, 0));

        }
        if (isKeyPressed(GLFW_KEY_SPACE)) {
            camera.translate(new Vector3(0, -0.025 * delta, 0));
        }
        if (isKeyPressed(GLFW_KEY_LEFT_SHIFT)) {
            camera.translate(new Vector3(0, 0.025 * delta, 0));
        }

        if (isKeyPressed(GLFW_KEY_E)) {
            camera.translate(new Vector3(0, -0.025 * delta, 0));
        }
        if (isKeyPressed(GLFW_KEY_Q)) {
            camera.translate(new Vector3(0, 0.025 * delta, 0));
        }

        if (isKeyPressed(GLFW_KEY_S)) {
            Vector3 dir = camera.getHorizontalDirection().inverse();
            game.getWorldRenderer().addOffset(dir);
        }
        if (isKeyPressed(GLFW_KEY_W)) {
            Vector3 dir = camera.getHorizontalDirection();
            game.getWorldRenderer().addOffset(dir);
        }

        if (isKeyPressed(GLFW_KEY_D)) {
            Vector3 dir = camera.getHorizontalDirection().rotate(Math.toRadians(90), new Vector3(0, 1, 0));
            game.getWorldRenderer().addOffset(dir);
        }
        if (isKeyPressed(GLFW_KEY_A)) {
            Vector3 dir = camera.getHorizontalDirection().rotate(Math.toRadians(-90), new Vector3(0, 1, 0));
            game.getWorldRenderer().addOffset(dir);
        }

        float speed = delta * 2;

        if (isKeyPressed(GLFW_KEY_UP)) {
            camera.rotatePitch(50 * speed);
        }
        if (isKeyPressed(GLFW_KEY_DOWN)) {
            camera.rotatePitch(-50 * speed);
        }
        if (isKeyPressed(GLFW_KEY_LEFT)) {
            camera.rotateYaw(-50 * speed);
        }
        if (isKeyPressed(GLFW_KEY_RIGHT)) {
            camera.rotateYaw(50 * speed);
        }

        if (isKeyPressed(GLFW_KEY_C)) {
            func(8 * 32, 8 * 32, 8, 0.05f);
            game.getWorldRenderer().change();
        }
        if (isKeyPressed(GLFW_KEY_V)) {
            func(8 * 32, 8 * 32, 8, -0.05f);
            game.getWorldRenderer().change();
        }
    }

    private void func(int x, int y, int brush, float amount) {
        game.getMap().mover(x, y, brush, amount);
        game.getMap().alisar(x, y, brush);
        game.getMap().suavizar(x, y, brush + 8);
        game.getMap().mover(x, y, brush, amount / 4);
        game.getMap().alisar(x, y, brush);
        for (int i = 0; i < 5; i++) {
            game.getMap().alisar(x, y, brush);
            game.getMap().suavizar(x, y, brush + 8);
        }
    }

    private void func2(int x, int y, int brush) {
        for (int i = 0; i < 10; i++) {
            game.getMap().suavizar(x, y, brush + 16);
        }
    }
}
