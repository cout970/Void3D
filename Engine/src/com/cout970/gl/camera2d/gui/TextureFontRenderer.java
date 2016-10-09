package com.cout970.gl.camera2d.gui;

import com.cout970.gl.texture.ITexture;
import com.cout970.gl.util.vector.Vector2;

import static org.lwjgl.opengl.GL11.*;

/**
 * Created by cout970 on 02/05/2016.
 */
public class TextureFontRenderer implements IFontRenderer {

    private ITexture font;
    private IGuiRenderer renderer;

    public TextureFontRenderer(ITexture font, IGuiRenderer renderer) {
        this.font = font;
        this.renderer = renderer;
    }

    @Override
    public int drawStringWithShadow(String text, Vector2 pos, int color) {
        print(pos.getXi() + 1, pos.getYi() + 1, 0, text);
        print(pos.getXi(), pos.getYi(), color, text);
        return 0;
    }

    @Override
    public int drawString(String text, Vector2 pos, int color) {
        print(pos.getXi(), pos.getYi(), color, text);
        return 0;
    }

    @Override
    public int getStringWidth(String text) {
        return text.length() * 8;
    }

    @Override
    public int getCharWidth(char character) {
        return 8;
    }

    @Override
    public String trimStringToWidth(String text, int width) {
        throw new UnsupportedOperationException();
    }


    private void print(float x, float y, int color, String text) {
        glBindTexture(GL_TEXTURE_2D, font.getID());
        glColor3f((color & 0xFF) / 255f, ((color >> 8) & 0xFF) / 255f, ((color >> 16) & 0xFF) / 255f);
        for (int i = 0; i < text.length(); i++) {
            char character = text.charAt(i);
            renderer.drawRectangleWithCustomSizedTexture(new Vector2(x + i * 8, y), new Vector2(8, 8), new Vector2((character & 15) * 8, (character >> 4) * 8), new Vector2(128, 128));
        }
        glColor4f(1f, 1f, 1f, 1f);
    }
}
