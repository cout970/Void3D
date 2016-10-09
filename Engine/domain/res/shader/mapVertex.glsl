#version 400 core

in vec3 in_position;
in vec2 in_texture;
in vec3 in_normal;

out vec2 pass_texture;
out vec3 surfaceNormal;
out vec3 toLightVector;
out vec3 toCameraVector;
out float visibility;
out float mapHeight;

uniform mat4 projectionMatrix;
uniform mat4 viewMatrix;
uniform mat4 transformationMatrix;
uniform vec3 lightPosition;

const float density = 5;
const float gradient = 10;

void main(void){

    mapHeight = in_position.y * 50;
    vec4 worldPos = transformationMatrix * vec4(in_position.xyz, 1.0);
    vec4 positionRelativeToCam = viewMatrix * worldPos;
    vec4 v = projectionMatrix * positionRelativeToCam;
    gl_Position = v;
    //gl_Position = vec4(v.x, v.y-(v.x*v.x+v.z*v.z)*1.5f, v.z, v.w);

    pass_texture = in_texture / 16;
    surfaceNormal = in_normal;

    toLightVector = lightPosition - worldPos.xyz;
    toCameraVector = (inverse(viewMatrix) * vec4(0.0, 0.0, 0.0, 1.0)).xyz - worldPos.xyz;

    float distance = length(positionRelativeToCam.xyz);
    visibility = exp(-pow((distance*density), gradient));
    visibility = clamp(visibility, 0.0, 1.0);
}