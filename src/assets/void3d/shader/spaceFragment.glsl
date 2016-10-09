#version 400 core

//input
in vec2 pass_texture;
in vec3 surfaceNormal;
in vec3 toLightVectorA;
in vec3 toLightVectorB;
in vec3 toCameraVector;

//output
out vec4 out_color;

//texture
uniform sampler2D textureSampler;

//light
uniform vec3 lightColorA;
uniform vec3 lightColorB;
uniform float shineDamper;
uniform float reflectivity;
uniform float enableLight;

//color
uniform vec3 in_color;
uniform float enableColor;

const float ambient = 0.1;

vec3 getLight(vec3 color, vec3 lcolor, vec3 toLight, vec3 normal, vec3 toCamera);

void main(void){

    if(enableColor > 0.5){
        out_color = vec4(in_color, 1.0);
        return;
    }

    vec4 color = texture(textureSampler, pass_texture);

    if(color.w < 0.1) discard;
/*
    vec3 normal = normalize(surfaceNormal);
    vec3 lightVectorNorm = normalize(toLightVector);
    float brightness = max(dot(normal, lightVectorNorm), ambient);

    vec3 diffuse = brightness * lightColor;
    vec3 cameraVectorNorm = normalize(toCameraVector);
    vec3 brightness2 = -lightVectorNorm;

    vec3 reflectedLightDirection = reflect(brightness2, normal);
    float specularFactor = dot(reflectedLightDirection, cameraVectorNorm);
    specularFactor = max(specularFactor, 0.1);

    float dampedFactor = pow(specularFactor, shineDamper);
    vec3 finalSpecular = dampedFactor * reflectivity * lightColor;
*/

    if(enableLight > 0.5){
        //out_color = vec4(diffuse, 1.0) * color + vec4(finalSpecular, 1.0);
        out_color = vec4(getLight(color.xyz, lightColorA, normalize(toLightVectorA), normalize(surfaceNormal), normalize(toCameraVector)), 1.0);
        out_color = mix(out_color, vec4(getLight(color.xyz, lightColorB, normalize(toLightVectorB), normalize(surfaceNormal), normalize(toCameraVector)), 1.0), 0.5);
        //out_color = color;
    }else{
        out_color = color;
    }
}

//apply Phong light
vec3 getLight(vec3 color, vec3 lcolor, vec3 toLight, vec3 normal, vec3 toCamera){
    vec3 I =
        vec3(ambient, ambient, ambient) + // ambient light
        lcolor * max(0.0, dot(toLight, normal)) + // diffuse light
        lcolor * pow(max(0.0, dot(reflect(toLight, normal), toCamera)), shineDamper) * reflectivity; //specular light

    //I = normal;
    //return vec3(I.x+0.5, I.y+0.5, I.z+0.5);// very cool effect

    return color * I * 1.5;
}

