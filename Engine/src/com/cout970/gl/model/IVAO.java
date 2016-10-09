package com.cout970.gl.model;

import com.cout970.gl.util.IIdentifiable;

/**
 * Created by cout970 on 09/05/2016.
 */
public interface IVAO extends IIdentifiable {

    //numero de vertices
    int getVertexCount();

    //numero de atributos o VBO en el VAO
    int getVertexAttribCount();

    //modo de dibujo, ej. GL_TRIANGLES
    int getDrawMode();

    //renderizar usando indices o no
    boolean useElements();
}
