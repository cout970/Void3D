#version 400 core

//input
in vec2 pass_texture;

//output
out vec4 out_color;

uniform sampler2D textureSampler;

void main(void){

    out_color = texture(textureSampler, pass_texture);
    if(out_color.w < 0.1)discard;
}
