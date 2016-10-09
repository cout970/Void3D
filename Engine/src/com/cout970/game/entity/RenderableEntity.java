package com.cout970.game.entity;

import com.cout970.game.world.WorldMap;
import com.cout970.gl.model.IMaterial;
import com.cout970.gl.model.IRenderableObject;
import com.cout970.gl.model.IVAO;
import com.cout970.gl.util.RotationVect;
import com.cout970.gl.util.vector.Vector3;
import org.joml.Matrix4f;

/**
 * Created by cout970 on 10/05/2016.
 */
public class RenderableEntity implements IRenderableObject {

    private Entity entity;
    private IVAO vao;
    private IMaterial material;

    public RenderableEntity(Entity entity, IVAO vao, IMaterial material) {
        this.entity = entity;
        this.vao = vao;
        this.material = material;
    }

    @Override
    public Matrix4f getTransformationMatrix() {
        Matrix4f matrix = new Matrix4f();
        matrix.identity();
        Vector3 pos = entity.getPos().copy();
        pos.mul(1/1024f);
        pos = WorldMap.moveOffset(pos, entity.getWorldMap().getOffset().mul(1/1024f));
        matrix.translate(pos.getXf(), pos.getYf(), pos.getZf());
        RotationVect rot = entity.getRotation();
        matrix.rotate(rot.getPitch(), 1,0,0);
        matrix.rotate(rot.getYaw(), 0,1,0);
        Vector3 scale = entity.getScale();
        matrix.scale(scale.getXf(), scale.getYf(), scale.getZf());
        return matrix;
    }

    public Entity getEntity() {
        return entity;
    }

    @Override
    public IVAO getVAO() {
        return vao;
    }

    @Override
    public IMaterial getMaterial() {
        return material;
    }
}
