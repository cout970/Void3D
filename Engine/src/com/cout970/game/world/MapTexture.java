package com.cout970.game.world;

import com.cout970.game.TextureLoader;
import com.cout970.gl.resource.IResource;
import com.cout970.gl.resource.internal.AbstractResourceManager;
import com.cout970.gl.resource.internal.ResourceLocation;
import com.cout970.gl.texture.ITexture;
import org.lwjgl.glfw.GLFW;

/**
 * Created by cout970 on 05/05/2016.
 */
public class MapTexture {

    private ITexture[] water;
    private ITexture[] textures;

    public MapTexture(AbstractResourceManager man, TextureLoader tex) {
        textures = new ITexture[5];
        water = new ITexture[8];
        tex.setUseLinear(true);
        for (int i = 1; i < 6; i++) {
            IResource res = man.getResource(new ResourceLocation("", "texture" + (i + 1) + ".png"));
            textures[i - 1] = tex.loadTexture(res);
        }

        for (int i = 0; i < water.length; i++) {
            IResource res = man.getResource(new ResourceLocation("", "agua" + i + ".png"));
            water[i] = tex.loadTexture(res);
        }
    }

    public int getTextureCount(){
        return textures.length+1;
    }

    public ITexture getTexture(int i) {
        if (i == 0) {
            int index = (int) (GLFW.glfwGetTime() * 5);
            index %= (water.length * 2);
            if (index >= water.length) {
                index = water.length * 2 - index;
            }
            if (index >= water.length){
                index = water.length -1;
            }
            return water[index];
        }
        return textures[i - 1];
    }
}
