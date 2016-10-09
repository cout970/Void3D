package com.cout970.game.shader;

import com.cout970.game.Game;
import com.cout970.game.world.WorldMap;
import com.cout970.gl.matrix.IMatrixShader;
import com.cout970.gl.resource.internal.ResourceLocation;
import com.cout970.gl.shader.TemplateShader;
import com.cout970.gl.shader.UniformVariable;
import com.cout970.gl.shader.loader.ShaderCode;
import com.cout970.gl.shader.loader.ShaderLoader;
import com.cout970.gl.util.vector.Vector3;
import org.joml.Matrix4f;

import java.io.IOException;

/**
 * Created by cout970 on 05/05/2016.
 */
public class SkyboxShader extends TemplateShader implements IMatrixShader {

    private UniformVariable projectionMatrix;
    private UniformVariable viewMatrix;
    private UniformVariable fogColor;
    private WorldMap map;

    public static SkyboxShader getSkyboxShader(Game g) {
        try {
            ShaderCode mainVertex = ShaderLoader.loadShader(g.getResourceManager().getResource(new ResourceLocation("", "shader/skyboxVertex.glsl")));
            ShaderCode mainFragment = ShaderLoader.loadShader(g.getResourceManager().getResource(new ResourceLocation("", "shader/skyboxFragment.glsl")));
            return new SkyboxShader(mainVertex, mainFragment, g.getMap());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public SkyboxShader(ShaderCode vertex, ShaderCode fragment, WorldMap map) {
        super(vertex.toString(), fragment.toString());
        this.map = map;
    }

    @Override
    protected void bindAttributes() {
        bindAttribute(0, "in_position");
    }

    @Override
    protected void getUniformLocations() {
        viewMatrix = getUniformLocation("view");
        projectionMatrix = getUniformLocation("projection");
        fogColor = getUniformLocation("fogColor");
    }

    @Override
    public void loadModelMatrix(Matrix4f matrix) {
        loadTransformationMatrix(matrix);
    }

    @Override
    public void loadViewMatrix(Matrix4f matrix) {
        Matrix4f newMatrix = new Matrix4f(matrix);
        newMatrix.m30 = 0;
        newMatrix.m31 = 0;
        newMatrix.m32 = 0;
        newMatrix.rotate((float) Math.toRadians(map.getSkyRotation()), 0, 1, 0);
        viewMatrix.setMatrix4(newMatrix);
    }

    @Override
    public void loadProjectionMatrix(Matrix4f matrix) {
        projectionMatrix.setMatrix4(matrix);
    }

    @Override
    public void setSkyColor(Vector3 vector3) {
        fogColor.setVector3(vector3);
    }
}
