package com.cout970.game.world;

import com.cout970.gl.tesellator.ITessellator;
import com.cout970.gl.util.vector.Vector2;
import com.cout970.gl.util.vector.Vector3;

/**
 * Created by cout970 on 30/04/2016.
 */
public class Point {

    private Vector3 vertex;
    private Vector2 texture;
    private Vector3 normal;

    public Point(Vector3 vertex, Vector2 texture) {
        this.vertex = vertex;
        this.texture = texture;
    }

    public Vector3 getVertex() {
        return vertex;
    }

    public void setNormal(Vector3 normal) {
        this.normal = normal;
    }

    public void render(ITessellator tes){
        tes.glTexCoord2f(texture.getXf(), texture.getYf());
        tes.glNormal3f(normal.getXf(), normal.getYf(), normal.getZf());
        tes.glVertex3f(vertex.getXf(), vertex.getYf(), vertex.getZf());
        tes.endVertex();
    }

    public Point toNormal(int offX, int offY) {
        return new Point(new Vector3(offX, 0, offY).add(vertex), texture);
    }
}
