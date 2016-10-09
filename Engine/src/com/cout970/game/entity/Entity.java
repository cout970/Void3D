package com.cout970.game.entity;

import com.cout970.game.render.EntityRenderer;
import com.cout970.game.world.WorldMap;
import com.cout970.gl.model.IRenderableObject;
import com.cout970.gl.util.RotationVect;
import com.cout970.gl.util.vector.Vector3;

/**
 * Created by cout970 on 09/05/2016.
 */
public class Entity {

    protected WorldMap map;
    protected Vector3 pos;
    protected RotationVect rotation;
    protected Vector3 scale;
    protected RenderableEntity model;

    public Entity(WorldMap map) {
        this.map = map;
        pos = new Vector3();
        rotation = new RotationVect();
        scale = new Vector3(1,1,1);
    }

    public void update(){}

    public void render(EntityRenderer renderer){
        Vector3 pos = this.pos.copy();
        pos.mul(1/1024f);
        pos = WorldMap.moveOffset(pos, map.getOffset().mul(1/1024f));
        if (pos.lengthSquared() > 2 / 32f) { return; }
        renderer.addObjectToRender(model);
    }

    public Vector3 getPos() {
        return pos;
    }

    public void setPos(Vector3 pos) {
        this.pos = pos;
    }

    public RotationVect getRotation() {
        return rotation;
    }

    public void setRotation(RotationVect rotation) {
        this.rotation = rotation;
    }

    public Vector3 getScale() {
        return scale;
    }

    public void setScale(Vector3 scale) {
        this.scale = scale;
    }

    public IRenderableObject getModel() {
        return model;
    }

    public WorldMap getWorldMap() {
        return map;
    }

    public void kill(){
        map.removeEntity(this);
    }
}
