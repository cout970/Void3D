package com.cout970.gl.tesellator;

import com.cout970.gl.model.ModelVAO;
import com.cout970.gl.model.VAOFactory;
import com.cout970.gl.util.vector.Vector2;
import com.cout970.gl.util.vector.Vector3;
import org.lwjgl.BufferUtils;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

/**
 * Created by cout970 on 29/04/2016.
 */
public class Tessellator implements ITessellator {

    private static final int MAX_VERTICES = 2048 * 1024;
    private boolean running;
    private IntBuffer indices;
    private FloatBuffer vertices;
    private FloatBuffer textures;
    private FloatBuffer normals;
    private int currentVertex;
    private int drawMode;

    private Vector3 vertexTemp = new Vector3();
    private Vector2 textureTemp = new Vector2();
    private Vector3 normalTemp = new Vector3();

    public Tessellator() {
        indices = BufferUtils.createIntBuffer(MAX_VERTICES);
        vertices = BufferUtils.createFloatBuffer(MAX_VERTICES * 3);
        textures = BufferUtils.createFloatBuffer(MAX_VERTICES * 2);
        normals = BufferUtils.createFloatBuffer(MAX_VERTICES * 3);
    }

    private void reset() {
        currentVertex = 0;
        indices.rewind();
        vertices.rewind();
        textures.rewind();
        normals.rewind();

        vertexTemp.set(0, 0, 0);
        textureTemp.set(0, 0);
        normalTemp.set(0, 0, 0);
    }

    @Override
    public void begin(int drawMode) {
        if (running) {
            throw new IllegalStateException("No se puede iniciar el tessellator si ya se esta usando");
        }
        this.drawMode = drawMode;
        running = true;
        reset();
    }

    @Override
    public ITessellator glTexCoord2f(float u, float v) {
        textureTemp.set(u, v);
        return this;
    }

    @Override
    public ITessellator glNormal3f(float x, float y, float z) {
        normalTemp.set(x, y, z);
        return this;
    }

    @Override
    public ITessellator glVertex3f(float x, float y, float z) {
        vertexTemp.set(x, y, z);
        return this;
    }

    @Override
    public ITessellator glTexCoord2(Vector2 uv) {
        return glTexCoord2f(uv.getXf(), uv.getYf());
    }

    @Override
    public ITessellator glNormal3(Vector3 norm) {
        return glNormal3f(norm.getXf(), norm.getYf(), norm.getZf());
    }

    @Override
    public ITessellator glVertex3(Vector3 vert) {
        return glVertex3f(vert.getXf(), vert.getYf(), vert.getZf());
    }

    @Override
    public void endVertex() {
        vertices.put(currentVertex * 3, vertexTemp.getXf()).put(currentVertex * 3 + 1, vertexTemp.getYf()).put(currentVertex * 3 + 2, vertexTemp.getZf());
        textures.put(currentVertex * 2, textureTemp.getXf()).put(currentVertex * 2 + 1, textureTemp.getYf());
        normals.put(currentVertex * 3, normalTemp.getXf()).put(currentVertex * 3 + 1, normalTemp.getYf()).put(currentVertex * 3 + 2, normalTemp.getZf());
        indices.put(currentVertex++);
    }

    @Override
    public void end() {
        if (!running) {
            throw new IllegalStateException("No se puede finalizar el tessellator porque no se ha iniciado aun");
        }
        running = false;
    }

    @Override
    public ModelVAO getData() {
        IntBuffer newIndices = BufferUtils.createIntBuffer(currentVertex);
        FloatBuffer newVertices = BufferUtils.createFloatBuffer(currentVertex * 3);
        FloatBuffer newTextures = BufferUtils.createFloatBuffer(currentVertex * 2);
        FloatBuffer newNormals = BufferUtils.createFloatBuffer(currentVertex * 3);

        indices.flip();
        newIndices.put(indices).rewind();

        vertices.limit(currentVertex * 3);
        newVertices.put(vertices).rewind();

        textures.limit(currentVertex * 2);
        newTextures.put(textures).rewind();

        normals.limit(currentVertex * 3);
        newNormals.put(normals).rewind();

        VAOFactory factory = new VAOFactory(drawMode);

        factory.addIndices(newIndices);
        factory.addAttribArray(newVertices, 3);
        factory.addAttribArray(newTextures, 2);
        factory.addAttribArray(newNormals, 3);

        return factory.build();
    }
}
