
//fog constants
const float density = 4;
const float gradient = 4;

//fog value output
out float visibility;

void setVisibility(void){
    float distance = length(positionRelativeToCam.xyz);
    visibility = exp(-pow((distance*density), gradient));
    visibility = clamp(visibility, 0.0, 1.0);
}