#version 400 core

in vec2 pass_texture;
in vec3 surfaceNormal;
in vec3 toLightVector;
in vec3 toCameraVector;
in float visibility;

out vec4 out_color;

uniform sampler2D textureSampler;
uniform vec3 lightColor;
uniform float shineDamper;
uniform float reflectivity;
uniform float enableLight;
uniform vec3 skyColor;

void main(void){

    vec4 color = texture(textureSampler, pass_texture);

    vec3 surfaceNorm = normalize(surfaceNormal);
    vec3 lightVectorNorm = normalize(toLightVector);
    float nDot1 = dot(surfaceNorm, lightVectorNorm);
    float brigthtness = max(nDot1, 0.1);

    vec3 diffuse = brigthtness * lightColor;
    vec3 cameraVectorNorm = normalize(toCameraVector);
    vec3 lightDirection = -lightVectorNorm;

    vec3 reflectedLightDirection = reflect(lightDirection, surfaceNorm);
    float specularFactor = dot(reflectedLightDirection, cameraVectorNorm);
    specularFactor = max(specularFactor, 0.1);

    float dampedFactor = pow(specularFactor, shineDamper);
    vec3 finalSpecular = dampedFactor * reflectivity * lightColor;

    if(enableLight > 0.5){
        out_color = vec4(diffuse, 1.0) * color + vec4(finalSpecular, 1.0);
        out_color = mix(vec4(skyColor, 1.0), out_color, visibility);
    }else{
        out_color = vec4(1.0, 1.0, 1.0, 1.0);
    }
}