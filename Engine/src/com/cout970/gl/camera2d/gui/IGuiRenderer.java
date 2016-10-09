package com.cout970.gl.camera2d.gui;

import com.cout970.gl.util.vector.Vector2;
import com.cout970.gl.util.vector.Vector3;

/**
 * Created by cout970 on 02/05/2016.
 */
public interface IGuiRenderer {

    void drawHorizontalLine(int startX, int endX, int y, Vector3 Vector3);

    void drawVerticalLine(int x, int startY, int endY, Vector3 Vector3);

    void drawRectangle(Vector2 start, Vector2 end, Vector3 Vector3);

    void drawGradientRectangle(Vector2 start, Vector2 end, Vector3 startVector3, Vector3 endVector3);

    void drawTexturedRectangle(Vector2 pos, Vector2 texturePos, Vector2 size);

    void drawRectangleWithCustomSizedTexture(Vector2 pos, Vector2 size, Vector2 textureUV, Vector2 textureSize);

    void drawScaledCustomSizeRectangle(Vector2 pos, Vector2 size, Vector2 textureUV, Vector2 textureSize, Vector2 tileSize);

    void drawRectangleWithTextureUV(Vector2 pos, Vector2 size, Vector2 first, Vector2 end);

    void enableBlend();

    void disableBlend();
}
