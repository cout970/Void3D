package com.cout970.gl.shader;

/**
 * Created by cout970 on 28/04/2016.
 */
public class StaticShader extends AbstractShaderProgram {

    private static final String vertex = "" +
            "#version 400 core\n" +
            "in vec3 position;\n" +
            "in vec2 in_texture;\n" +
            "out vec2 pass_texture;\n" +
            "void main(void){\n" +
            "gl_Position = vec4(position*2-1, 1.0);\n" +
            "pass_texture = in_texture;\n" +
            "}\n";
    private static final String fragment = "" +
            "#version 400 core\n" +
            "in vec2 pass_texture;\n" +
            "out vec4 out_color;\n" +
            "uniform sampler2D textureSampler;\n" +
            "void main(void){\n" +
            "out_color = texture(textureSampler, 1-pass_texture);\n" +
            "}\n";

//    private UniformVariable transformationMatrix;

    public StaticShader() {
        super(vertex, fragment);
    }

    @Override
    protected void bindAttributes() {
        bindAttribute(0, "position");
        bindAttribute(1, "in_texture");
    }

    @Override
    protected void getAllUniformLocations() {
//        transformationMatrix = getUniformLocation("transformationMatrix");
    }
}
