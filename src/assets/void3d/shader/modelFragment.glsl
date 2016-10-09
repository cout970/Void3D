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

uniform float textureSize;

const float ambient = 0.1;

vec3 getLight(vec3 color, vec3 lcolor, vec3 toLight, vec3 normal, vec3 toCamera);

void main(void){

    vec4 color = texture(textureSampler, pass_texture/textureSize);

    if(color.w < 0.01) discard;

    if(enableLight > 0.5){
        //out_color = vec4(diffuse, 1.0) * color + vec4(finalSpecular, 1.0);
        out_color = vec4(getLight(color.xyz, lightColorA, normalize(toLightVectorA), normalize(surfaceNormal), normalize(toCameraVector)), 1.0);
        out_color = mix(out_color, vec4(getLight(color.xyz, lightColorB, normalize(toLightVectorB), normalize(surfaceNormal), normalize(toCameraVector)), 1.0), 0.5);
        //out_color = color;
    }else{
        out_color = color;
    }
}

//apply Phong light model
vec3 getLight(vec3 color, vec3 lcolor, vec3 toLight, vec3 normal, vec3 toCamera){
    vec3 I =
        vec3(ambient, ambient, ambient) + // ambient light
        lcolor * max(0.0, dot(toLight, normal)) + // diffuse light
        lcolor * pow(max(0.0, dot(reflect(toLight, normal), toCamera)), shineDamper) * reflectivity; //specular light

//    I = normal;
//    return vec3(I.x+0.5, I.y+0.5, I.z+0.5);// very cool effect
    return color * I * 1.5;
}

