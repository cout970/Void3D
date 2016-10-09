#version 400 core

in vec2 pass_texture;
in vec3 surfaceNormal;
in vec3 toLightVector;
in vec3 toCameraVector;
in float visibility;
in float mapHeight;

out vec4 out_color;

const float ambientLight = 0.1;

uniform vec3 lightColor;
uniform float shineDamper;
uniform float reflectivity;
uniform float enableLight;
uniform vec3 skyColor;

//binded textures
uniform sampler2D water;
uniform sampler2D sand;
uniform sampler2D dirt;
uniform sampler2D grass;
uniform sampler2D stone;
uniform sampler2D snow;

vec3 getLight(vec3 color, float Iambient, float Ilight, vec3 toLight, vec3 normal, vec3 toCamera);

void main(void){

    //get texture color
    vec4 waterColor = texture(water, pass_texture);
    vec4 sandColor = texture(sand, pass_texture);
    vec4 dirtColor = texture(dirt, pass_texture);
    vec4 grassColor = texture(grass, pass_texture);
    vec4 stoneColor = texture(stone, pass_texture);
    vec4 snowColor = texture(snow, pass_texture);

    float height = mapHeight;
    float waterProp = 0;
    float sandProp  = 0;
    float dirtProp  = 0;
    float grassProp = 0;
    float stoneProp = 0;
    float snowProp  = 0;

    /**
    if(height < 0.1){ //0.0 0.1
        waterProp = 1;
    }else if(height < 0.2){ //0.1 0.2
        sandProp = 1 - (height-0.1)*10;
        dirtProp = 1 - sandProp;
    }else if(height < 0.4){ //0.2 0.4
        dirtProp = 1 - (height-0.2)*5;
        grassProp = 1 - dirtProp;
    }else if(height < 0.6){ //0.4 0.6
        grassProp = 1 - (height-0.4)*5;
        stoneProp = 1 - grassProp;
    } else if(height < 0.8){
        stoneProp = 1 - (height-0.6)*5;
        snowProp = 1 - stoneProp;
    }else{
        snowProp = 1;
    }
    */

    if(height < 0.075){ //0.0 0.075
        waterProp = 1;
    }else if(height < 0.1){ //0.075 0.1
        waterProp = 1 - (height-0.075)*40;
        dirtProp = 1 - waterProp;
    }else if(height < 0.2){ //0.1 0.2
        dirtProp = 1 - (height-0.1)*10;
        grassProp = 1 - dirtProp;
    }else if(height < 0.6){ //0.2 0.6
        grassProp = 1 - (height-0.2)*2.5;
        stoneProp = 1 - grassProp;
    } else if(height < 0.8){
        stoneProp = 1 - (height-0.6)*5;
        snowProp = 1 - stoneProp;
    }else{
        snowProp = 1;
    }

    float sum = waterProp + sandProp + dirtProp + grassProp + stoneProp + snowProp;

    vec4 color = waterColor * waterProp/sum + sandColor * sandProp/sum + dirtColor * dirtProp/sum + grassColor * grassProp/sum + stoneColor * stoneProp/sum + snowColor * snowProp/sum;

    //get diffuse light
    vec3 surfaceNorm = normalize(surfaceNormal);
    vec3 lightVectorNorm = normalize(toLightVector);
    float nDot1 = dot(surfaceNorm, lightVectorNorm);
    float brightness = max(nDot1, ambientLight);
    vec3 diffuse = brightness * lightColor;

    //get Specular light
    vec3 cameraVectorNorm = normalize(toCameraVector);
    vec3 lightDirection = -lightVectorNorm;
    vec3 reflectedLightDirection = reflect(lightDirection, surfaceNorm);
    float specularFactor = dot(reflectedLightDirection, cameraVectorNorm);
    specularFactor = max(specularFactor, 0.1);
    float dampedFactor = pow(specularFactor, shineDamper);
    vec3 finalSpecular = dampedFactor * reflectivity * lightColor;

    //use light or not
    if(enableLight > 0.5){
        out_color = vec4(diffuse, 1.0) * color + vec4(finalSpecular, 1.0);
        //out_color = vec4(getLight(color.xyz, ambientLight, 0.5, normalize(toLightVector), normalize(surfaceNormal), normalize(toCameraVector)), 1.0);
        //out_color = vec4(surfaceNorm.x/2+0.5, surfaceNorm.y/2+0.5, surfaceNorm.z/2+0.5, 1.0);
        out_color = mix(vec4(skyColor, 1.0), out_color, visibility);
    }else{
        out_color = vec4(1.0, 1.0, 1.0, 1.0);
    }
}

vec3 getLight(vec3 color, float Iambient, float Ilight, vec3 toLight, vec3 normal, vec3 toCamera){
        float I = 0;
            //Iambient + // luz ambiente
            Ilight * max(0.0, dot(toLight, normal)) + // luz difusa
            Ilight * max(0.0, dot(reflect(-toLight, normal), toCamera)); //luz especular


         return color * min(1, I);
}