
//texture coords input
in textureCoords

//the current bind texture
uniform sampler2D textureSampler;

vec4 pixelColor;

void applyTexture(){
    pixelColor = texture(textureSampler, textureCoords);
}
