package com.cout970.game;

import com.cout970.game.entity.Entity;
import com.cout970.game.entity.EntityTree;
import com.cout970.game.render.EntityRenderer;
import com.cout970.game.render.SkyboxRenderer;
import com.cout970.game.render.WorldRenderer;
import com.cout970.game.world.WorldMap;
import com.cout970.game.world.WorldSection;
import com.cout970.gl.camera.Camera;
import com.cout970.gl.camera.PerspectiveCamera;
import com.cout970.gl.debug.DebugRenderHelper;
import com.cout970.gl.matrix.MainMatrixHandler;
import com.cout970.gl.raytrace.AABB;
import com.cout970.gl.raytrace.ProjectionHelper;
import com.cout970.gl.raytrace.Ray;
import com.cout970.gl.raytrace.RayTraceResult;
import com.cout970.gl.util.IClosable;
import com.cout970.gl.util.Pair;
import com.cout970.gl.util.vector.Vector2;
import com.cout970.gl.util.vector.Vector3;
import com.cout970.gl.window.GameLoop;
import com.cout970.gl.window.Window;
import org.joml.Matrix4f;

import java.util.LinkedList;
import java.util.Random;

/**
 * Created by cout970 on 01/05/2016.
 */
public class Game extends GameLoop implements IClosable {

    private InputManager inputManager;
    private ResourceManager resourceManager;
    private TextureLoader textureLoader;

    private WorldRenderer worldRenderer;
    private EntityRenderer entityRenderer;
    private SkyboxRenderer skyboxRenderer;

    private Camera camera;

    private WorldMap map;

    private Ray mouseRay;

    public Game(Window w) {
        super(w);
        camera = new PerspectiveCamera((float) Math.toRadians(60), 0.001f, 10000f);
        inputManager = new InputManager(this);
        resourceManager = new ResourceManager();
        textureLoader = new TextureLoader();

        w.disableVSync();

        map = new WorldMap(this);
        init();

        worldRenderer = new WorldRenderer(this);
        entityRenderer = new EntityRenderer(this);
        skyboxRenderer = new SkyboxRenderer(this);
    }

    private void init() {
        Loader.init(this);
        int max = WorldMap.SECTION_AMOUNT * WorldSection.SECTION_SIZE;

        for (int i = 0; i < 3000; i++) {
            Random r = new Random();
            int x = r.nextInt(max);
            int y = r.nextInt(max);
            float height = map.getHeight(x, y);

            if (height < 0.6 && height > 0.15) {
                map.getWorldEntities().add(new EntityTree(map, new Vector3(x, 0, y)));
            }
        }
        map.getWorldEntities().add(new EntityTree(map, new Vector3(0, 0, 0)));
        camera.rotatePitch(50);
        camera.translate(new Vector3(0, -1 / 32f, 0));
        MainMatrixHandler.getProjection().setMatrix(camera.createProjectionMatrix(window));
    }

    @Override
    public void tick() {
        // entrada del usuario
        inputManager.tick((float) getTimer().getDelta());
        mouseRay = ProjectionHelper.getRay(inputManager.getMousePosition());
        createViewMatrix();

        //map
        map.update(this);
        worldRenderer.render();

        //entities
        map.getWorldEntities().forEach(Entity::update);
        map.getWorldEntities().forEach(e -> e.render(entityRenderer));
        entityRenderer.render();

        //skybox
        skyboxRenderer.render();

        //DEBUG
        window.setTitle(getTimer().getFPS() + "");

    }

    private void createViewMatrix() {
        Matrix4f matrix = MainMatrixHandler.getView().getMatrix();
        matrix.identity();
        Vector2 rotation = camera.getRotation();
        matrix.rotate((float) Math.toRadians(rotation.getX()), 1, 0, 0);
        matrix.rotate((float) Math.toRadians(rotation.getY()), 0, 1, 0);

        //        if (cameraMotion) {
        Vector3 translation = camera.getPosition();
        matrix.translate(translation.getXf(), translation.getYf(), translation.getZf());
        //        }
    }

    private void debug() {

        DebugRenderHelper.drawRay(mouseRay);

        LinkedList<WorldSection> sectionList = new LinkedList<>();

        for (Pair<AABB, WorldSection> p : map.getBoundingBoxes()) {
            RayTraceResult r = p.getFirst().rayTrace(mouseRay);
            if (r != null) {
                DebugRenderHelper.drawAABB(p.getFirst());
            }
        }
        DebugRenderHelper.drawDebugLines();
    }

    @Override
    public void close() {
        window.close();
        inputManager.close();
        resourceManager.close();
        textureLoader.close();
    }

    public InputManager getInputManager() {
        return inputManager;
    }

    public ResourceManager getResourceManager() {
        return resourceManager;
    }

    public TextureLoader getTextureLoader() {
        return textureLoader;
    }

    public WorldMap getMap() {
        return map;
    }

    public Ray getMouseRay() {
        return mouseRay;
    }

    public WorldRenderer getWorldRenderer() {
        return worldRenderer;
    }

    public EntityRenderer getEntityRenderer() {
        return entityRenderer;
    }

    public SkyboxRenderer getSkyboxRenderer() {
        return skyboxRenderer;
    }

    public Camera getCamera() {
        return camera;
    }
}
