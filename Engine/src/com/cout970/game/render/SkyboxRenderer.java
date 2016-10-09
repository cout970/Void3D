package com.cout970.game.render;

import com.cout970.game.Game;
import com.cout970.game.shader.SkyboxShader;
import com.cout970.gl.matrix.MainMatrixHandler;
import com.cout970.gl.model.AbstractRenderer;
import com.cout970.gl.model.ModelVAO;
import com.cout970.gl.resource.IResource;
import com.cout970.gl.resource.internal.ResourceLocation;
import com.cout970.gl.tesellator.ITessellator;
import com.cout970.gl.tesellator.Tessellator;
import com.cout970.gl.texture.ITexture;
import com.cout970.gl.util.vector.Vector3;

import static org.lwjgl.opengl.GL11.GL_TRIANGLES;
import static org.lwjgl.opengl.GL11.glBindTexture;
import static org.lwjgl.opengl.GL13.GL_TEXTURE_CUBE_MAP;

/**
 * Created by cout970 on 05/05/2016.
 */
public class SkyboxRenderer extends AbstractRenderer {

    private static final float SIZE = 1f;

    private static final float[] VERTICES = {
            -SIZE, SIZE, -SIZE,
            -SIZE, -SIZE, -SIZE,
            SIZE, -SIZE, -SIZE,
            SIZE, -SIZE, -SIZE,
            SIZE, SIZE, -SIZE,
            -SIZE, SIZE, -SIZE,

            -SIZE, -SIZE, SIZE,
            -SIZE, -SIZE, -SIZE,
            -SIZE, SIZE, -SIZE,
            -SIZE, SIZE, -SIZE,
            -SIZE, SIZE, SIZE,
            -SIZE, -SIZE, SIZE,

            SIZE, -SIZE, -SIZE,
            SIZE, -SIZE, SIZE,
            SIZE, SIZE, SIZE,
            SIZE, SIZE, SIZE,
            SIZE, SIZE, -SIZE,
            SIZE, -SIZE, -SIZE,

            -SIZE, -SIZE, SIZE,
            -SIZE, SIZE, SIZE,
            SIZE, SIZE, SIZE,
            SIZE, SIZE, SIZE,
            SIZE, -SIZE, SIZE,
            -SIZE, -SIZE, SIZE,

            -SIZE, SIZE, -SIZE,
            SIZE, SIZE, -SIZE,
            SIZE, SIZE, SIZE,
            SIZE, SIZE, SIZE,
            -SIZE, SIZE, SIZE,
            -SIZE, SIZE, -SIZE,

            -SIZE, -SIZE, -SIZE,
            -SIZE, -SIZE, SIZE,
            SIZE, -SIZE, -SIZE,
            SIZE, -SIZE, -SIZE,
            -SIZE, -SIZE, SIZE,
            SIZE, -SIZE, SIZE
    };

    private ModelVAO model;
    private ITexture texture;
    private SkyboxShader shader;

    public SkyboxRenderer(Game g) {
        createModel();

        IResource[] res = new IResource[6];
        String[] texNames = {"right", "left", "top", "bottom", "back", "front"};
        for (int i = 0; i < 6; i++) {
            res[i] = g.getResourceManager().getResource(new ResourceLocation("", texNames[i] + ".png"));
        }
        texture = g.getTextureLoader().loadTextureCube(res);

        shader = SkyboxShader.getSkyboxShader(g);
        if (shader == null) { g.stop(); }
    }

    public void createModel(){
        ITessellator tes = new Tessellator();
        tes.begin(GL_TRIANGLES);
        for (int i = 0; i < VERTICES.length;) {
            tes.glVertex3f(VERTICES[i++], VERTICES[i++], VERTICES[i++]).endVertex();
        }
        tes.end();
        model = tes.getData();
    }

    @Override
    public void render() {
        shader.start();
        shader.setSkyColor(new Vector3(0.5444f, 0.62f, 0.69f));
        MainMatrixHandler.loadMatrix(shader);
        bindVAO(model);
        glBindTexture(GL_TEXTURE_CUBE_MAP, texture.getID());
        bindVBOs(1);
        draw(model.getDrawMode(), model.getVertexCount(), model.useElements());
        unbindVBOs(1);
        unbindVAO();
        shader.stop();
    }
}
