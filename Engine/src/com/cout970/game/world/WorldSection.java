package com.cout970.game.world;

import com.cout970.gl.model.ModelVAO;
import com.cout970.gl.raytrace.AABB;
import com.cout970.gl.util.Direction;
import com.cout970.gl.util.vector.Vector2;
import com.cout970.gl.util.vector.Vector3;

/**
 * Created by cout970 on 01/05/2016.
 */
public class WorldSection {

    public static final int SECTION_SIZE = 32;
    private WorldMap parent;
    private Vector2 position;
    private float[] mapa;
    private boolean dirty;
    private ModelVAO model;
    private AABB boundingBox;

    public WorldSection(WorldMap parent, Vector2 position) {
        this.position = position;
        this.parent = parent;
        mapa = new float[SECTION_SIZE * SECTION_SIZE];
        dirty = true;
        boundingBox = new AABB(
                new Vector3(0, -0.05, 0),
                new Vector3(SECTION_SIZE, SECTION_SIZE*2+8, SECTION_SIZE));
    }

    public Vector2 getPosition() {
        return position;
    }

    public float[] getMapa() {
        return mapa;
    }

    public float getHeight(int x, int y) {
        if (x >= SECTION_SIZE || x < 0) {
            return 0;
        }
        if (y >= SECTION_SIZE || y < 0) {
            return 0;
        }
        return mapa[x + y * SECTION_SIZE];
    }

    public void setHeight(int x, int y, float val) {
        if (x >= SECTION_SIZE || x < 0) {
            return;
        }
        if (y >= SECTION_SIZE || y < 0) {
            return;
        }
        dirty = true;
        moveToOthers();
        mapa[x + y * SECTION_SIZE] = val;
    }

    private void moveToOthers() {
        for (Direction d : Direction.HORIZONTAL) {
            int x = position.getXi() +d.getOffsetX();
            int z = position.getYi() +d.getOffsetZ();
            parent.getSection(x,z).setDirty(true);
        }
    }

    public void setModel(ModelVAO model) {
        this.model = model;
    }

    public ModelVAO getModel() {
        return model;
    }

    public boolean isDirty() {
        return dirty;
    }

    public void setDirty(boolean dirty) {
        this.dirty = dirty;
    }

    public AABB getBoundingBox() {
        return boundingBox;
    }
}
