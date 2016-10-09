#version 400 core

in vec3 in_position;
in vec2 in_texture;

out vec2 pass_texture;
uniform mat4 transformationMatrix;

void main(void){

    gl_Position = transformationMatrix * vec4(in_position.xyz, 1.0);
    pass_texture = in_texture;
}