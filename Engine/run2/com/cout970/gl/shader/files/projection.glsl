
//vertex input
in vec3 vertexPos;

//camera pos output
out vec3 toCameraVector;

//matrix
uniform mat4 projectionMatrix;
uniform mat4 viewMatrix;
uniform mat4 transformationMatrix;

//global vars
vec3 worldPos;
vec3 posRelativeToCam;
vec3 finalPos;

void applyProjection(void){

    worldPos = transformationMatrix * vec4(vertexPos, 1.0);
    posRelativeToCam = viewMatrix * worldPos;
    finalPos = projectionMatrix * posRelativeToCam;
    gl_Position = finalPos;
    toCameraVector = (inverse(viewMatrix) * vec4(0.0, 0.0, 0.0, 1.0)).xyz - worldPos.xyz;
}