package com.cout970.gl.util;

import com.cout970.gl.util.vector.Vector3;
import org.joml.Matrix4f;

/**
 * Created by cout970 on 28/04/2016.
 */
public class MatrixMath {

    public static Matrix4f createTransformationMatrix(Vector3 translation, Vector3 rotation, float scale){
        Matrix4f matrix = new Matrix4f();
        matrix.identity();
        matrix.translate(translation.getXf(), translation.getYf(), translation.getZf());
        matrix.rotate((float)Math.toRadians(rotation.getX()), 1,0,0);
        matrix.rotate((float)Math.toRadians(rotation.getY()), 0,1,0);
        matrix.rotate((float)Math.toRadians(rotation.getZ()), 0,0,1);
        matrix.scale(scale);
        return matrix;
    }
}
