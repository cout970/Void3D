package com.cout970.game.shader;

import com.cout970.game.Game;
import com.cout970.gl.light.BasicLight;
import com.cout970.gl.matrix.IMatrixShader;
import com.cout970.gl.model.IMaterial;
import com.cout970.gl.resource.internal.ResourceLocation;
import com.cout970.gl.shader.TemplateShader;
import com.cout970.gl.shader.UniformVariable;
import com.cout970.gl.shader.loader.ShaderCode;
import com.cout970.gl.shader.loader.ShaderLoader;
import com.cout970.gl.util.vector.Vector3;
import org.joml.Matrix4f;

import java.io.IOException;

/**
 * Created by cout970 on 28/04/2016.
 */
public class EntityShader extends TemplateShader implements IMatrixShader {

    private UniformVariable projectionMatrix;
    private UniformVariable viewMatrix;
    private UniformVariable transformationMatrix;
    private UniformVariable lightPosition;
    private UniformVariable lightColor;
    private UniformVariable shineDamper;
    private UniformVariable reflectivity;
    private UniformVariable enableLight;
    private UniformVariable skyColor;

    public static EntityShader getEntityShader(Game g){
        try{
            ShaderCode mainVertex = ShaderLoader.loadShader(g.getResourceManager().getResource(new ResourceLocation("", "shader/entityVertex.glsl")));
            ShaderCode mainFragment = ShaderLoader.loadShader(g.getResourceManager().getResource(new ResourceLocation("", "shader/entityFragment.glsl")));
            return new EntityShader(mainVertex, mainFragment);
        }catch(IOException e){
            e.printStackTrace();
        }
        return null;
    }

    public EntityShader(ShaderCode vertex, ShaderCode fragment){
        super(vertex.toString(), fragment.toString());
    }

    @Override
    protected void bindAttributes() {
        bindAttribute(0, "in_position");
        bindAttribute(1, "in_texture");
        bindAttribute(2, "in_normal");
    }

    @Override
    protected void getUniformLocations() {
        viewMatrix = getUniformLocation("viewMatrix");
        projectionMatrix = getUniformLocation("projectionMatrix");
        transformationMatrix = getUniformLocation("transformationMatrix");
        lightPosition = getUniformLocation("lightPosition");
        lightColor = getUniformLocation("lightColor");
        shineDamper = getUniformLocation("shineDamper");
        reflectivity = getUniformLocation("reflectivity");
        enableLight = getUniformLocation("enableLight");
        skyColor = getUniformLocation("skyColor");
    }

    @Override
    public void loadMaterial(IMaterial material){
        shineDamper.setFloat(material.getShineDamper());
        reflectivity.setFloat(material.getReflectivity());
    }

    @Override
    public void loadLight(BasicLight light){
        lightPosition.setVector3(light.getPosition());
        lightColor.setVector3(light.getColor());
    }

    @Override
    public void enableLight(){
        enableLight.setBoolean(true);
    }

    @Override
    public void disableLight(){
        enableLight.setBoolean(false);
    }

    @Override
    public void loadTransformationMatrix(Matrix4f matrix){
        transformationMatrix.setMatrix4(matrix);
    }

    @Override
    public void loadModelMatrix(Matrix4f matrix) {
        loadTransformationMatrix(matrix);
    }

    @Override
    public void loadViewMatrix(Matrix4f matrix){
        viewMatrix.setMatrix4(matrix);
    }

    @Override
    public void loadProjectionMatrix(Matrix4f matrix) {
        projectionMatrix.setMatrix4(matrix);
    }

    @Override
    public void setSkyColor(Vector3 vector3) {
        skyColor.setVector3(vector3);
    }
}
