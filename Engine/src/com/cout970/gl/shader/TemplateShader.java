package com.cout970.gl.shader;

import com.cout970.gl.light.BasicLight;
import com.cout970.gl.model.IMaterial;
import com.cout970.gl.util.vector.Vector3;
import org.joml.Matrix4f;

/**
 * Created by cout970 on 02/05/2016.
 */
public abstract class TemplateShader extends AbstractShaderProgram {

    public TemplateShader(String vertexShader, String fragmentShader) {
        super(vertexShader, fragmentShader);
    }

    public void loadLight(BasicLight light){}

    public void enableLight(){}

    public void disableLight(){}

    public void loadTransformationMatrix(Matrix4f matrix){}

    public void loadViewMatrix(Matrix4f matrix){}

    public void loadProjectionMatrix(Matrix4f matrix) {}

    public void setSkyColor(Vector3 vector3) {}

    public void loadMaterial(IMaterial material) {}
}
