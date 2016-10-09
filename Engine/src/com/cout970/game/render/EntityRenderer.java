package com.cout970.game.render;

import com.cout970.game.Game;
import com.cout970.game.shader.EntityShader;
import com.cout970.game.world.WorldMap;
import com.cout970.gl.matrix.MainMatrixHandler;
import com.cout970.gl.model.DelayRenderer;
import com.cout970.gl.model.IMaterial;
import com.cout970.gl.model.IRenderableObject;
import com.cout970.gl.util.vector.Vector3;

/**
 * Created by cout970 on 09/05/2016.
 */
public class EntityRenderer extends DelayRenderer {

    private WorldMap map;

    private EntityShader shader;

    public EntityRenderer(Game g) {
        this.map = g.getMap();
        shader = EntityShader.getEntityShader(g);
        if (shader == null) { g.stop(); }
    }

    @Override
    public void render() {
        shader.start();
        shader.setSkyColor(new Vector3(0.5444f, 0.62f, 0.69f));
        shader.enableLight();
        shader.loadLight(map.getLight());
        MainMatrixHandler.loadMatrix(shader);
        super.render();
        shader.stop();
    }

    @Override
    protected void bindMaterial(IMaterial material) {
        shader.loadMaterial(material);
    }

    @Override
    protected void processObject(IRenderableObject obj) {
        shader.loadTransformationMatrix(obj.getTransformationMatrix());
    }
}
