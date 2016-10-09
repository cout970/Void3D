package com.cout970.game.entity;

import com.cout970.game.Game;
import com.cout970.game.world.WorldMap;
import com.cout970.gl.model.BasicMaterial;
import com.cout970.gl.model.IMaterial;
import com.cout970.gl.model.IVAO;
import com.cout970.gl.model.obj.OBJLoader;
import com.cout970.gl.resource.IResource;
import com.cout970.gl.resource.internal.ResourceLocation;
import com.cout970.gl.texture.ITexture;
import com.cout970.gl.util.vector.Vector3;

import java.io.IOException;

/**
 * Created by cout970 on 10/05/2016.
 */
public class EntityHouse extends Entity {

    private static IVAO houseVao;
    private static IMaterial houseMaterial;

    public static boolean init(Game g) {
        IResource treeObj = g.getResourceManager().getResource(new ResourceLocation("", "casa.obj"));
        IResource treeTexture = g.getResourceManager().getResource(new ResourceLocation("", "casa.png"));
        try {
            houseVao = OBJLoader.getObjModel(treeObj);
            ITexture tex = g.getTextureLoader().loadTexture(treeTexture);
            houseMaterial = new BasicMaterial(tex);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public EntityHouse(WorldMap map, Vector3 pos) {
        super(map);
        model = new RenderableEntity(this, houseVao, houseMaterial);
        setPos(pos);
        scale.mul(1 / 1024f);
    }

    @Override
    public void update() {
        float height = map.getHeight(pos.getXf(), pos.getZf());
        pos.setY(height * 20 - 0.0625f);
    }

    @Override
    public void setPos(Vector3 pos) {
        this.pos = pos.copy();
    }
}
