package com.cout970.gl.model.obj;

import com.cout970.gl.tesellator.ITessellable;
import com.cout970.gl.tesellator.ITessellator;
import org.joml.Vector2f;
import org.joml.Vector3f;

import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.opengl.GL11.GL_QUADS;

/**
 * Created by carlos couto on 29/02/2016.
 */
public class ObjData implements ITessellable{

    protected List<Vector3f> vertices = new ArrayList<>();
    protected List<Vector3f> normals = new ArrayList<>();
    protected List<Vector2f> texcoords = new ArrayList<>();
    protected List<Face> faces = new ArrayList<>();

    public ObjData() {}

    @Override
    public void tessellate(ITessellator tes) {

        tes.begin(GL_QUADS);
        for (Face f : faces) {

            for (int i = 0; i < f.vertices; i++) {
                if (f.normIn != null) {
                    Vector3f n = normals.get(f.normIn[i] - 1);
                    tes.glNormal3f(-n.x, n.y, n.z);
                }

                if (f.textIn != null) {
                    Vector2f t = texcoords.get(f.textIn[i] - 1);
                    tes.glTexCoord2f(t.x, t.y);
                }

                Vector3f v = vertices.get(f.vertIn[i] - 1);
                tes.glVertex3f(v.x, v.y, v.z).endVertex();
            }
        }
        tes.end();
    }

    protected static class Face {

        private int[] vertIn;
        private int[] normIn;
        private int[] textIn;
        private int vertices;

        Face(int[] vertIn, int[] normIn, int vertices) {
            this.vertIn = vertIn;
            this.normIn = normIn;
            this.vertices = vertices;
        }

        Face(int[] vertIn, int[] normIn, int[] textIn, int vertices) {
            this.vertIn = vertIn;
            this.normIn = normIn;
            this.textIn = textIn;
            this.vertices = vertices;
        }
    }
}
