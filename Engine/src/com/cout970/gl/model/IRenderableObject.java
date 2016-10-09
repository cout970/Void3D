package com.cout970.gl.model;

import org.joml.Matrix4f;

/**
 * Created by cout970 on 09/05/2016.
 */
public interface IRenderableObject {

    Matrix4f getTransformationMatrix();

    IVAO getVAO();

    IMaterial getMaterial();
}
