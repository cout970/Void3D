package com.cout970.gl.model;

import static org.lwjgl.opengl.GL15.glDeleteBuffers;
import static org.lwjgl.opengl.GL30.glDeleteVertexArrays;

/**
 * Created by cout970 on 28/04/2016.
 */
public class ModelVAO implements IVAO, AutoCloseable {

    private int id;
    private int count;
    private int vboCount;
    private int[] vbos;
    private int drawMode;
    private boolean useElements;

    public ModelVAO(int id, int count, int vboCount, int[] vbos, int drawMode, boolean useElements) {
        this.id = id;
        this.count = count;
        this.vboCount = vboCount;
        this.vbos = vbos;
        this.drawMode = drawMode;
        this.useElements = useElements;
    }

    @Override
    public int getID() {
        return id;
    }

    @Override
    public int getVertexCount() {
        return count;
    }

    @Override
    public int getVertexAttribCount() {
        return vboCount;
    }

    @Override
    public int getDrawMode() {
        return drawMode;
    }

    @Override
    public boolean useElements() {
        return useElements;
    }

    @Override
    public void close() throws Exception {
        glDeleteVertexArrays(id);
        for (int aVbo : vbos) {
            glDeleteBuffers(aVbo);
        }
    }
}
