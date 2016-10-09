package com.cout970.game.world;

import com.cout970.game.Game;
import com.cout970.game.entity.Entity;
import com.cout970.gl.light.BasicLight;
import com.cout970.gl.light.LightPoint;
import com.cout970.gl.raytrace.*;
import com.cout970.gl.util.Pair;
import com.cout970.gl.util.vector.Vector2;
import com.cout970.gl.util.vector.Vector3;
import org.lwjgl.glfw.GLFW;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**
 * Created by cout970 on 30/04/2016.
 */
public class WorldMap implements HeightSetter, HeightGetter, IRayObstacle {

    private static final float ROTATION_SPEED = 1f;
    private static final int SIZE = 512;
    private static final int SECTION_SIZE = 32;
    public static final int SECTION_AMOUNT = SIZE / SECTION_SIZE;
    private WorldSection[] secciones;
    private static Vector3 offset;
    private BasicLight light;
    private MapTexture worldTexture;
    private float skyRotation;
    private List<Entity> worldEntities;
    private List<Entity> toRemove;
    private float dayTime = 0.5f;

    public WorldMap(Game g) {
        offset = new Vector3();
        secciones = new WorldSection[SECTION_AMOUNT * SECTION_AMOUNT];
        light = new LightPoint(new Vector3(0, 5, 2), new Vector3(1, 1, 1));
        worldEntities = new LinkedList<>();
        toRemove = new ArrayList<>();
        initMap();
    }

    public static boolean init(Game g) {
        g.getMap().worldTexture = new MapTexture(g.getResourceManager(), g.getTextureLoader());
        return true;
    }

    private void initMap() {
        for (int i = 0; i < SECTION_AMOUNT; i++) {
            for (int j = 0; j < SECTION_AMOUNT; j++) {
                secciones[i + j * SECTION_AMOUNT] = new WorldSection(this, new Vector2(i, j));
            }
        }
        int brush = 8;
        int sizeBrush = (SIZE / brush);
        float[] salt = new float[sizeBrush * sizeBrush];
        float[] aux = new float[SIZE * SIZE];

        SimplexNoise noise = new SimplexNoise(2, 1, new Random().nextInt());

        for (int i = 0; i < sizeBrush; i++) {
            for (int j = 0; j < sizeBrush; j++) {
                float height = (float) noise.getNoise(i, j);
                salt[i + j * sizeBrush] = clamp(0f, 0.99f, height);
            }
        }

        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                aux[i + j * SIZE] = salt[i / brush + (j / brush) * sizeBrush];
            }
        }

        for (int i = 0; i < 1; i++) {
            smooth((x, y) -> aux[x + y * SIZE], this);
        }
        for (int i = 0; i < 50; i++) {
            smooth(this, this);
        }
    }

    private void smooth(HeightGetter orig, HeightSetter dest) {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                float jp = orig.getHeight(mod(SIZE, i + 1), j);
                float jm = orig.getHeight(mod(SIZE, i - 1), j);
                float ip = orig.getHeight(i, mod(SIZE, j + 1));
                float im = orig.getHeight(i, mod(SIZE, j - 1));

                float pp = orig.getHeight(mod(SIZE, i + 1), mod(SIZE, j + 1));
                float mm = orig.getHeight(mod(SIZE, i - 1), mod(SIZE, j - 1));
                float mp = orig.getHeight(mod(SIZE, i - 1), mod(SIZE, j + 1));
                float pm = orig.getHeight(mod(SIZE, i + 1), mod(SIZE, j - 1));

                dest.setHeight(i, j, clamp(0.01f, 0.99f, (orig.getHeight(i, j) + (jp + jm + ip + im + pp + pm + mp + mm) * 1 / 8f) * 0.5f));
            }
        }
    }

    private float clamp(float min, float max, float value) {
        return Math.min(max, Math.max(min, value));
    }

    @Override
    public float getHeight(int x, int y) {
        x = mod(SIZE, x);
        y = mod(SIZE, y);
        int sx = mod(SECTION_SIZE, x);
        int sy = mod(SECTION_SIZE, y);
        int px = mod(SECTION_AMOUNT, x / SECTION_SIZE);
        int py = mod(SECTION_AMOUNT, y / SECTION_SIZE);
        return secciones[px + py * SECTION_AMOUNT].getHeight(sx, sy);
    }

    @Override
    public void setHeight(int x, int y, float val) {
        x = mod(SIZE, x);
        y = mod(SIZE, y);
        int sx = mod(SECTION_SIZE, x);
        int sy = mod(SECTION_SIZE, y);
        int px = mod(SECTION_AMOUNT, x / SECTION_SIZE);
        int py = mod(SECTION_AMOUNT, y / SECTION_SIZE);
        secciones[px + py * SECTION_AMOUNT].setHeight(sx, sy, val);
    }

    private int mod(int base, int value) {
        int res = value % base;
        if (res < 0) {
            res = res + base;
        }
        return res;
    }

    public void suavizar(int x, int y, int brush) {
        for (int i = -brush / 2; i < brush / 2 + 1; i++) {
            for (int j = -brush / 2; j < brush / 2 + 1; j++) {
                int a = i + x;
                int b = j + y;
                float jp = getHeight(a + 1, b);
                float jm = getHeight(a - 1, b);
                float ip = getHeight(a, b + 1);
                float im = getHeight(a, b - 1);

                float pp = getHeight(a + 1, b + 1);
                float mm = getHeight(a - 1, b - 1);
                float mp = getHeight(a - 1, b + 1);
                float pm = getHeight(a + 1, b - 1);

                float height = (getHeight(a, b) + (jp + jm + ip + im + pp + pm + mp + mm) * 1 / 8f) * 0.5f;
                setHeight(x + i, y + j, height);
            }
        }
    }

    public void mover(int x, int y, int brush, float amount) {
        for (int i = -brush / 2; i < brush / 2 + 1; i++) {
            for (int j = -brush / 2; j < brush / 2 + 1; j++) {
                float height = getHeight(x + i, y + j);
                setHeight(x + i, y + j, clamp(0f, 0.99f, height + amount));
            }
        }
    }

    public void alisar(int x, int y, int brush) {
        float media = 0;
        int count = 0;
        for (int i = -brush / 2; i < brush / 2 + 1; i++) {
            for (int j = -brush / 2; j < brush / 2 + 1; j++) {
                media += getHeight(x + i, y + j);
                count++;
            }
        }
        media /= count;
        for (int i = -brush / 2; i < brush / 2 + 1; i++) {
            for (int j = -brush / 2; j < brush / 2 + 1; j++) {
                float height = getHeight(x + i, y + j);
                setHeight(x + i, y + j, (height + media) * 0.5f);
            }
        }
    }

    public int getSize() {
        return SIZE;
    }

    public float maxHeight() {
        return 1;
    }

    public WorldSection getSection(int x, int y) {
        int px = mod(SECTION_AMOUNT, x);
        int py = mod(SECTION_AMOUNT, y);
        return secciones[px + py * SECTION_AMOUNT];
    }

    public void setDirty(boolean dirty) {
        for (WorldSection sec : secciones) {
            sec.setDirty(dirty);
        }
    }

    @Override
    public RayTraceResult rayTrace(Ray ray) {
        LinkedList<RayTraceResult> list = new LinkedList<>();
        LinkedList<WorldSection> sectionList = new LinkedList<>();

        for (Pair<AABB, WorldSection> p : getBoundingBoxes()) {
            RayTraceResult r = p.getFirst().rayTrace(ray);
            if (r != null) {
                sectionList.add(p.getSecond());
            }
        }
        if (sectionList.isEmpty()) {
            return null;
        }

        for (WorldSection section : sectionList) {
            for (int i = 0; i < SECTION_SIZE; i++) {
                for (int k = 0; k < SECTION_SIZE; k++) {

                    Vector3 tranlation = WorldMap.moveOffset(WorldMap.toRenderScale(
                            new Vector3(section.getPosition().getX(), 0, section.getPosition().getY()).mul(WorldSection.SECTION_SIZE)), WorldMap.toRenderScale(getOffset()));

                    //world pos
                    int x = (section.getPosition().getXi()) * SECTION_SIZE + i;
                    int z = (section.getPosition().getYi()) * SECTION_SIZE + k;
                    //heights
                    float a = getHeight(x, z);
                    float b = getHeight(x + 1, z);
                    float c = getHeight(x, z + 1);
                    float d = getHeight(x + 1, z + 1);

                    RayTraceResult r;

                    Vector3 va = new Vector3(i, c, k + 1);
                    Vector3 vb = new Vector3(i + 1, b, k);
                    Vector3 vc = new Vector3(i, a, k);

                    r = RayTraceUtil.rayTraceTriangle(ray, this, toSphere(WorldMap.toRenderScale(va).add(tranlation)),
                            toSphere(WorldMap.toRenderScale(vb).add(tranlation)), toSphere(WorldMap.toRenderScale(vc).add(tranlation)));

                    if (r != null) {
                        r.setExtraData(new Vector2(x, z));
                        list.add(r);
                    }

                    va = new Vector3(i, c, k + 1);
                    vb = new Vector3(i + 1, d, k + 1);
                    vc = new Vector3(i + 1, b, k);

                    r = RayTraceUtil.rayTraceTriangle(ray, this, toSphere(WorldMap.toRenderScale(va).add(tranlation)),
                            toSphere(WorldMap.toRenderScale(vb).add(tranlation)), toSphere(WorldMap.toRenderScale(vc).add(tranlation)));
                    if (r != null) {
                        r.setExtraData(new Vector2(x, z));
                        list.add(r);
                    }
                }
            }
        }

        if (!list.isEmpty()) {
            list.sort((o1, o2) -> o1.getHit().distanceSquared(ray.getStart()) > o2.getHit().distanceSquared(ray.getStart()) ? 1 : -1);
            return list.getFirst();
        }
        return null;
    }

    public Vector3 toSphere(Vector3 v) {
        return v;
//        return new Vector3(v.getX(), v.getY()-(v.getX()*v.getX()+v.getZ()*v.getZ())*1.5f, v.getZ());
    }

    public WorldSection[] getSections() {
        return secciones;
    }

    public List<Pair<AABB, WorldSection>> getBoundingBoxes() {
        LinkedList<Pair<AABB, WorldSection>> list = new LinkedList<>();

        for (int i = -WorldMap.SECTION_AMOUNT / 2; i < WorldMap.SECTION_AMOUNT / 2; i++) {
            for (int j = -WorldMap.SECTION_AMOUNT / 2; j < WorldMap.SECTION_AMOUNT / 2; j++) {
                WorldSection sec = getSection(i, j);
                list.add(new Pair<AABB, WorldSection>(
                        toRenderScale(sec.getBoundingBox().translate(moveOffset(toRenderScale(new Vector3(i, 0, j)
                                .mul(SECTION_SIZE)), toRenderScale(getOffset())))), sec));
            }
        }
        return list;
    }

    public Vector3 getOffset() {
        return offset.copy();
    }

    public void setOffset(Vector3 offset) {
        WorldMap.offset = offset;
    }

    public static Vector3 moveOffset(Vector3 pos, Vector3 offset) {
        Vector2 res = moveOffset(new Vector2(pos.getX(), pos.getZ()), new Vector2(offset.getX(), offset.getZ()));
        return new Vector3(res.getX(), pos.getY(), res.getY());
    }

    public static Vector2 moveOffset(Vector2 pos, Vector2 offset) {
        float maxOffset = (WorldMap.SECTION_AMOUNT / 2) / 32f;
        Vector2 newPos = pos.copy().add(offset);
        newPos.setX(mod(newPos.getX() + maxOffset, maxOffset * 2) - maxOffset);
        newPos.setY(mod(newPos.getY() + maxOffset, maxOffset * 2) - maxOffset);
        return newPos;
    }

    public static double mod(double val, double base) {
        double aux = val % base;
        if (aux < 0) {
            aux += base;
        }
        return aux;
    }

    public static Vector3 toRenderScale(Vector3 vertex) {
        return vertex.mul(1 / 1024f).mul(1, 20, 1);
    }

    public static AABB toRenderScale(AABB box) {
        return box.scale(1 / 1024f);
    }

    public BasicLight getLight() {
        return light;
    }

    public MapTexture getWorldTexture() {
        return worldTexture;
    }

    public void update(Game g) {
        skyRotation = (float) (ROTATION_SPEED * GLFW.glfwGetTime());
        double angle = Math.toRadians((skyRotation * dayTime+90) % 180);
        double sin = Math.sin(angle);
        double cos = Math.cos(angle);
        light.setPosition(new Vector3(10 / 32f, 5 * sin, 5 * cos));
        worldEntities.removeAll(toRemove);
        toRemove.clear();
    }

    public float getSkyRotation() {
        return skyRotation;
    }

    public List<Entity> getWorldEntities() {
        return worldEntities;
    }

    public void removeEntity(Entity e){
        toRemove.add(e);
    }

    public void setDayTime(float dayTime) {
        this.dayTime = dayTime;
    }

    public float getDayTime() {
        return dayTime;
    }

    public float getHeight(float x, float y) {
        float answer;
        if (x <= (1 - y)) {
            answer = barryCentric(
                    new Vector3(x + 0, getHeight((int) x, (int) y), y + 0),
                    new Vector3(x + 1, getHeight((int) x + 1, (int) y), y + 0),
                    new Vector3(x + 0, getHeight((int) x, (int) y + 1), y + 1), new Vector2(x, y));
        } else {
            answer = barryCentric(
                    new Vector3(x + 1, getHeight((int) x + 1, (int) y), y + 0),
                    new Vector3(x + 1, getHeight((int) x + 1, (int) y + 1), y + 1),
                    new Vector3(x + 0, getHeight((int) x, (int) y + 1), y + 1), new Vector2(x, y));
        }
        return answer;
    }

    public static float barryCentric(Vector3 p1, Vector3 p2, Vector3 p3, Vector2 pos) {
        float det = (p2.getZf() - p3.getZf()) * (p1.getXf() - p3.getXf()) + (p3.getXf() - p2.getXf()) * (p1.getZf() - p3.getZf());
        float l1 = ((p2.getZf() - p3.getZf()) * (pos.getXf() - p3.getXf()) + (p3.getXf() - p2.getXf()) * (pos.getYf() - p3.getZf())) / det;
        float l2 = ((p3.getZf() - p1.getZf()) * (pos.getXf() - p3.getXf()) + (p1.getXf() - p3.getXf()) * (pos.getYf() - p3.getZf())) / det;
        float l3 = 1.0f - l1 - l2;
        return l1 * p1.getYf() + l2 * p2.getYf() + l3 * p3.getYf();
    }

}
