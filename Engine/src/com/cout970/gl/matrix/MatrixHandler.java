package com.cout970.gl.matrix;

import com.cout970.gl.util.vector.Vector3;
import org.joml.Matrix4f;

import java.util.Stack;

/**
 * Created by cout970 on 28/04/2016.
 */
public class MatrixHandler {

    private Stack<Matrix4f> matrixStack;
    private Matrix4f currentMatrix;

    public MatrixHandler() {
        matrixStack = new Stack<>();
        currentMatrix = new Matrix4f();
    }

    public void pushMatrix(){
        matrixStack.push(new Matrix4f(currentMatrix));
    }

    public void popMatrix(){
        setMatrix(matrixStack.pop());
    }

    public void translate(Vector3 vec){
        currentMatrix.translate(vec.getXf(), vec.getYf(), vec.getZf());
    }

    public void rotate(float angle, Vector3 vec){
        currentMatrix.rotate(angle, vec.getXf(), vec.getYf(), vec.getZf());
    }

    public void scale(Vector3 vec){
        currentMatrix.scale(vec.getXf(), vec.getYf(), vec.getZf());
    }

    public void setIdentity(){
        currentMatrix.identity();
    }

    public Matrix4f getMatrix() {
        return currentMatrix;
    }

    public void setMatrix(Matrix4f currentMatrix) {
        this.currentMatrix = currentMatrix;
    }
}
