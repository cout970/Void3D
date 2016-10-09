package com.cout970.gl.model;

import com.cout970.gl.texture.ITexture;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL13.GL_TEXTURE0;
import static org.lwjgl.opengl.GL13.glActiveTexture;
import static org.lwjgl.opengl.GL20.glDisableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL30.glBindVertexArray;

/**
 * Created by cout970 on 09/05/2016.
 */
public abstract class AbstractRenderer {

    protected static int[] bindTextures;

    public AbstractRenderer() {
        bindTextures = new int[32];
    }

    public abstract void render();

    protected void bindTexture(ITexture... tex){
        for (int i = 0; i < tex.length && i < 32; i++) {
            if (bindTextures[i] != tex[i].getID()) {
                glActiveTexture(GL_TEXTURE0 + i);
                glBindTexture(GL_TEXTURE_2D, tex[i].getID());
                bindTextures[i] = tex[i].getID();
            }
        }
    }

    protected void unbindTextures(int amount){
        for (int i = 0; i < amount && i < 32; i++) {
            if (bindTextures[i] != 0) {
                glActiveTexture(GL_TEXTURE0 + i);
                glBindTexture(GL_TEXTURE_2D, 0);
                bindTextures[i] = 0;
            }
        }
    }

    protected void drawVAO(IVAO vao){
        bindVAO(vao);
        bindVBOs(vao.getVertexAttribCount());
        draw(vao.getDrawMode(), vao.getVertexCount(), vao.useElements());
        unbindVBOs(vao.getVertexAttribCount());
        unbindVAO();
    }

    protected void bindVAO(IVAO vao){
        glBindVertexArray(vao.getID());
    }

    protected void unbindVAO(){
        glBindVertexArray(0);
    }

    protected void bindVBOs(int amount){
        for (int i = 0; i < amount; i++) {
            glEnableVertexAttribArray(i);
        }
    }

    protected void unbindVBOs(int amount){
        for (int i = 0; i < amount; i++) {
            glDisableVertexAttribArray(i);
        }
    }

    protected void draw(int drawMode, int vertexCount, boolean elements) {
        if (elements) {
            glDrawElements(drawMode, vertexCount, GL_UNSIGNED_INT, 0);
        } else {
            glDrawArrays(drawMode, vertexCount, 0);
        }
    }
}
