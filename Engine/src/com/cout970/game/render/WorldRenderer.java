package com.cout970.game.render;

import com.cout970.game.Game;
import com.cout970.game.shader.MapShader;
import com.cout970.game.world.MapTexture;
import com.cout970.game.world.WorldMap;
import com.cout970.game.world.WorldSection;
import com.cout970.game.world.WorldTessellator;
import com.cout970.gl.model.AbstractRenderer;
import com.cout970.gl.model.IVAO;
import com.cout970.gl.util.vector.Vector3;

import static com.cout970.gl.matrix.MainMatrixHandler.*;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.glBindTexture;
import static org.lwjgl.opengl.GL13.GL_TEXTURE0;
import static org.lwjgl.opengl.GL13.glActiveTexture;

/**
 * Created by cout970 on 09/05/2016.
 */
public class WorldRenderer extends AbstractRenderer {

    private WorldTessellator worldTessellator;
    private WorldMap map;
    private MapShader shader;

    public WorldRenderer(Game g) {
        map = g.getMap();
        worldTessellator = new WorldTessellator(g.getMap());
        shader = MapShader.getMapShader(g);
        if (shader == null) { g.stop(); }
    }

    @Override
    public void render() {
        shader.start();
        shader.setSkyColor(new Vector3(0.5444f, 0.62f, 0.69f));
        shader.enableLight();
        shader.loadLight(map.getLight());
        shader.loadMaterial(null);
        bindTexture(map.getWorldTexture());

        for (int i = -WorldMap.SECTION_AMOUNT / 2; i < WorldMap.SECTION_AMOUNT / 2; i++) {
            for (int j = -WorldMap.SECTION_AMOUNT / 2; j < WorldMap.SECTION_AMOUNT / 2; j++) {

                Vector3 tranlation = WorldMap.moveOffset(WorldMap.toRenderScale(
                        new Vector3(i, 0, j).mul(WorldSection.SECTION_SIZE)), WorldMap.toRenderScale(getOffset()));
                if (tranlation.lengthSquared() > 2 / 32f) { continue; }
                WorldSection sec = map.getSection(i, j);
                pushMatrix();
                translate(tranlation);
                loadMatrix(shader);

                IVAO vao = sec.getModel();

                bindVAO(vao);
                bindVBOs(vao.getVertexAttribCount());
                draw(vao.getDrawMode(), vao.getVertexCount(), vao.useElements());
                unbindVBOs(vao.getVertexAttribCount());
                unbindVAO();
                popMatrix();
            }
        }
        unbindTextures(map.getWorldTexture().getTextureCount());
        //DEBUG
        //renderiza las lines cardinales donde se encuentra la luz
//        Vector3 p = map.getLight().getPosition();
//        shader.loadTransformationMatrix(new Matrix4f().identity().translate(p.getXf(), p.getYf(), p.getZf()));
//        DebugRenderHelper.drawDebugLines();
        shader.stop();
    }

    protected void bindTexture(MapTexture tex){
        for (int i = 0; i < tex.getTextureCount() && i < 32; i++) {
            if (bindTextures[i] != tex.getTexture(i).getID()) {
                glActiveTexture(GL_TEXTURE0 + i);
                glBindTexture(GL_TEXTURE_2D, tex.getTexture(i).getID());
                bindTextures[i] = tex.getTexture(i).getID();
            }
        }
    }

    public void addOffset(Vector3 vector3) {
        setOffset(getOffset().copy().add(vector3));
    }

    public void setOffset(Vector3 pos) {
        map.setOffset(pos);
    }

    public Vector3 getOffset() {
        return map.getOffset();
    }

    public void change() {
        worldTessellator.change();
    }

    public WorldTessellator getWorldTessellator() {
        return worldTessellator;
    }
}
