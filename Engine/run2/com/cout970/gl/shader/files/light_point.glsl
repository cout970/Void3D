
#require projection.glsl

//the position of the light point
uniform vec3 lightPosition;

//the vector from the vertex to the light
out vec3 toLightVector;


void setToLightVector(void){
    toLightVector = lightPosition - worldPos.xyz;
}
