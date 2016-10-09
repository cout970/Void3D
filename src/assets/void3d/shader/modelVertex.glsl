#version 400 core

in vec3 in_position;
in vec2 in_texture;
in vec3 in_normal;

out vec2 pass_texture;
out vec3 surfaceNormal;
out vec3 toLightVectorA;
out vec3 toLightVectorB;
out vec3 toCameraVector;

uniform mat4 projectionMatrix;
uniform mat4 viewMatrix;
uniform mat4 transformationMatrix;
uniform vec3 lightPositionA;
uniform vec3 lightPositionB;

void main(void){

    vec4 worldPos = transformationMatrix * vec4(in_position.xyz, 1.0);
    vec4 positionRelativeToCam = viewMatrix * worldPos;
    gl_Position = projectionMatrix * positionRelativeToCam;

    pass_texture = in_texture;
    surfaceNormal = in_normal;

    toLightVectorA = lightPositionA - worldPos.xyz;
    toLightVectorB = lightPositionB - worldPos.xyz;
    toCameraVector = (inverse(viewMatrix) * vec4(0.0, 0.0, 0.0, 1.0)).xyz - worldPos.xyz;
}