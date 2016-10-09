package com.cout970.gl.model;

import com.cout970.gl.texture.ITexture;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by cout970 on 09/05/2016.
 */
public class MultiMaterial extends BasicMaterial {

    private List<ITexture> textures;

    public MultiMaterial(ITexture... tex) {
        super(tex[0]);
        textures = new ArrayList<>();
        textures.addAll(Arrays.asList(tex).subList(1, tex.length));
    }

    @Override
    public ITexture[] getTextures() {
        ITexture[] tex = new ITexture[textures.size()+1];
        tex[0] = super.getTextures()[0];
        for (int i = 0; i < textures.size(); i++) {
            tex[i+1] = textures.get(i);
        }
        return tex;
    }
}
