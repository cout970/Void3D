#version 400 core

in vec3 in_position;
in vec2 in_texture;

out vec2 pass_texture;

uniform float sizex;
uniform float sizey;

void main(void){
    gl_Position = vec4((in_position.x-0.5) * 100/sizex, (in_position.y-0.5) * 100/sizey, 0.0, 1.0);
    pass_texture = in_texture;
}