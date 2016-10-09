package com.cout970.gl.camera2d.gui;

import com.cout970.gl.util.vector.Vector2;

/**
 * Created by cout970 on 02/05/2016.
 */
public interface IFontRenderer {

    int drawStringWithShadow(String text, Vector2 pos, int color);

    int drawString(String text, Vector2 pos, int color);

    int getStringWidth(String text);

    int getCharWidth(char character);

    String trimStringToWidth(String text, int width);
}
