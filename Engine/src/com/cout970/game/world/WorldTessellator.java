package com.cout970.game.world;

import com.cout970.gl.tesellator.ITessellable;
import com.cout970.gl.tesellator.ITessellator;
import com.cout970.gl.tesellator.Tessellator;
import com.cout970.gl.util.vector.Vector2;
import com.cout970.gl.util.vector.Vector3;

import static org.lwjgl.opengl.GL11.GL_TRIANGLES;

/**
 * Created by cout970 on 30/04/2016.
 */
public class WorldTessellator implements ITessellable {

    private WorldMap map;

    public WorldTessellator(WorldMap map) {
        this.map = map;
        change();
    }

    public void change() {
        Tessellator tes = new Tessellator();
        tessellate(tes);
    }

    @Override
    public void tessellate(ITessellator tes) {
        for (int i = -8; i < 8; i++) {
            for (int j = -8; j < 8; j++) {
                WorldSection sec = map.getSection(i, j);
                if (sec.isDirty()) {
                    tessellateSection(tes, sec);
                }
            }
        }
        map.setDirty(false);
    }

    public void tessellateSection(ITessellator tes, WorldSection section) {
        tes.begin(GL_TRIANGLES);
        for (int i = 0; i < WorldSection.SECTION_SIZE; i++) {
            for (int k = 0; k < WorldSection.SECTION_SIZE; k++) {

                //world pos
                int x = section.getPosition().getXi() * WorldSection.SECTION_SIZE + i;
                int z = section.getPosition().getYi() * WorldSection.SECTION_SIZE + k;
                //heights
                float a = map.getHeight(x, z);
                float b = map.getHeight(x + 1, z);
                float c = map.getHeight(x, z + 1);
                float d = map.getHeight(x + 1, z + 1);

                Vector2 texa = new Vector2(i, k + 1);
                Vector3 va = new Vector3(i, c, k + 1);

                Vector2 texb = new Vector2(i + 1, k);
                Vector3 vb = new Vector3(i + 1, b, k);

                Vector2 texc = new Vector2(i, k);
                Vector3 vc = new Vector3(i, a, k);

                addVertex(tes, WorldMap.toRenderScale(va), texa, calculateNormal(x, z + 1));
                addVertex(tes, WorldMap.toRenderScale(vb), texb, calculateNormal(x + 1, z));
                addVertex(tes, WorldMap.toRenderScale(vc), texc, calculateNormal(x, z));

                texa = new Vector2(i, k + 1);
                va = new Vector3(i, c, k + 1);

                texb = new Vector2(i + 1, k + 1);
                vb = new Vector3(i + 1, d, k + 1);

                texc = new Vector2(i + 1, k);
                vc = new Vector3(i + 1, b, k);

                addVertex(tes, WorldMap.toRenderScale(va), texa, calculateNormal(x, z + 1));
                addVertex(tes, WorldMap.toRenderScale(vb), texb, calculateNormal(x + 1, z + 1));
                addVertex(tes, WorldMap.toRenderScale(vc), texc, calculateNormal(x + 1, z));
            }
        }
        tes.end();
        if (section.getModel() != null) {
            try {
                section.getModel().close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        section.setModel(tes.getData());
    }

    private Vector3 calculateNormal(int x, int z) {
        float heightL = map.getHeight(x - 1, z);
        float heightR = map.getHeight(x + 1, z);
        float heightD = map.getHeight(x, z - 1);
        float heightU = map.getHeight(x, z + 1);
        Vector3 normal = new Vector3(heightL - heightR, 0.1f, heightD - heightU);
        normal.normalize();
        return normal;
    }

    public void addVertex(ITessellator tes, Vector3 vertex, Vector2 texture, Vector3 normal) {
        tes.glTexCoord2f(texture.getXf(), texture.getYf());
        tes.glNormal3f(normal.getXf(), normal.getYf(), normal.getZf());
        tes.glVertex3f(vertex.getXf(), vertex.getYf(), vertex.getZf());
        tes.endVertex();
    }

    public Vector3 getOffset() {
        return map.getOffset();
    }
}
