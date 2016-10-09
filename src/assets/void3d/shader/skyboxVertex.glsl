#version 400 core

in vec3 in_position;

out vec3 pass_texture;

uniform mat4 projection;
uniform mat4 view;

void main(void){

    gl_Position = projection * view * vec4(in_position, 1.0);
    pass_texture = in_position;
}