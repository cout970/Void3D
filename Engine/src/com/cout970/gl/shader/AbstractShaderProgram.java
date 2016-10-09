package com.cout970.gl.shader;

import com.cout970.gl.exception.ShaderCompileException;
import com.cout970.gl.util.IClosable;
import com.cout970.gl.util.Log;
import org.lwjgl.opengl.GL11;

import static org.lwjgl.opengl.GL20.*;

/**
 * Created by cout970 on 28/04/2016.
 */
public abstract class AbstractShaderProgram implements IClosable {

    private int programId;
    private int vertexShaderId;
    private int fragmentShaderId;

    public AbstractShaderProgram(String vertexShader, String fragmentShader) {
        vertexShaderId = compileShader(GL_VERTEX_SHADER, vertexShader);
        fragmentShaderId = compileShader(GL_FRAGMENT_SHADER, fragmentShader);
        programId = glCreateProgram();
        glAttachShader(programId, vertexShaderId);
        glAttachShader(programId, fragmentShaderId);
        bindAttributes();
        glLinkProgram(programId);
        glValidateProgram(programId);
        getUniformLocations();
    }

    public void start() {
        glUseProgram(programId);
    }

    public void stop() {
        glUseProgram(0);
    }

    private int compileShader(int type, String code) {
        int shader = glCreateShader(type);
        glShaderSource(shader, code);
        glCompileShader(shader);
        if (glGetShaderi(shader, GL_COMPILE_STATUS) == GL11.GL_FALSE) {
            throw new ShaderCompileException("Error trying to compile shader: \n", glGetShaderInfoLog(shader, 500));
        }
        return shader;
    }

    protected abstract void bindAttributes();

    protected void bindAttribute(int attribute, String variableName) {
        glBindAttribLocation(programId, attribute, variableName);
    }

    protected abstract void getUniformLocations();

    protected UniformVariable getUniformLocation(String name) {
        int id = glGetUniformLocation(programId, name);
        if (id == -1) {
            Log.error("Error trying to get a uniform variable location for: " + name);
        }
        return new UniformVariable(id);
    }

    @Override
    public void close() {
        stop();
        glDetachShader(programId, vertexShaderId);
        glDetachShader(programId, fragmentShaderId);
        glDeleteShader(vertexShaderId);
        glDeleteShader(fragmentShaderId);
        glDeleteProgram(programId);
    }
}
