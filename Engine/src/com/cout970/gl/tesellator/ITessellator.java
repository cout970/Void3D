package com.cout970.gl.tesellator;

import com.cout970.gl.model.ModelVAO;
import com.cout970.gl.util.vector.Vector2;
import com.cout970.gl.util.vector.Vector3;

/**
 * Created by cout970 on 29/04/2016.
 */
public interface ITessellator {

    void begin(int drawMode);

    ITessellator glTexCoord2f(float u, float v);
    ITessellator glNormal3f(float x, float y, float z);
    ITessellator glVertex3f(float x, float y, float z);

    ITessellator glTexCoord2(Vector2 uv);
    ITessellator glNormal3(Vector3 normal);
    ITessellator glVertex3(Vector3 vertex);

    void endVertex();

    void end();

    ModelVAO getData();
}
