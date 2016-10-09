#version 400 core

in vec3 pass_texture;

out vec4 out_color;

uniform samplerCube cubeMap;

void main(void){

    out_color = texture(cubeMap, pass_texture);
}