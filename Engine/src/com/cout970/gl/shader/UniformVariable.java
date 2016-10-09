package com.cout970.gl.shader;

import com.cout970.gl.util.vector.Vector2;
import com.cout970.gl.util.vector.Vector3;
import org.joml.Matrix4f;
import org.lwjgl.BufferUtils;

import java.nio.FloatBuffer;

import static org.lwjgl.opengl.GL20.*;

/**
 * Created by cout970 on 28/04/2016.
 */
public class UniformVariable {

    private int uniformID;

    public UniformVariable(int uniformID) {
        this.uniformID = uniformID;
    }

    public int getUniformID() {
        return uniformID;
    }

    public void setFloat(float f){
        glUniform1f(uniformID, f);
    }

    public void setInt(int i){
        glUniform1i(uniformID, i);
    }

    public void setVector2(Vector2 f){
        glUniform2f(uniformID, f.getXf(), f.getYf());
    }

    public void setVector3(Vector3 f){
        glUniform3f(uniformID, f.getXf(), f.getYf(), f.getZf());
    }

    public void setBoolean(boolean bool){
        glUniform1f(uniformID, bool ? 1 : 0);
    }

    public void setMatrix4(Matrix4f matrix){
        FloatBuffer buffer = BufferUtils.createFloatBuffer(16);
        matrix.get(buffer);
        glUniformMatrix4fv(uniformID, false, buffer);
    }
}
