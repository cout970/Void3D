#version 400 core

in vec3 pass_texture;

out vec4 out_color;

uniform samplerCube cubeMap;
uniform vec3 fogColor;

const float lowerLimit = 0.0;
const float upperLimit = 0.5;

void main(void){

    vec4 color = texture(cubeMap, pass_texture);

    float factor = (pass_texture.y - lowerLimit) / (upperLimit - lowerLimit);
    factor = clamp(factor, 0.0, 1.0);

    out_color = mix(vec4(fogColor, 1.0), color, factor);
}