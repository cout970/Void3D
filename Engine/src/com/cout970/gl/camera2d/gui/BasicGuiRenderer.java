package com.cout970.gl.camera2d.gui;

import com.cout970.gl.texture.ITexture;
import com.cout970.gl.texture.internal.EmptyTexture;
import com.cout970.gl.util.vector.Vector2;
import com.cout970.gl.util.vector.Vector3;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL11.GL_BLEND;

/**
 * Created by cout970 on 02/05/2016.
 */
public class BasicGuiRenderer implements IGuiRenderer {

    private ITexture emptyTexture;

    public BasicGuiRenderer(ITexture emptyTexture) {
        this.emptyTexture = emptyTexture;
    }

    public BasicGuiRenderer() {
        this.emptyTexture = new EmptyTexture();
    }

    @Override
    public void drawHorizontalLine(int startX, int endX, int y, Vector3 color) {
        if (endX < startX) {
            int i = startX;
            startX = endX;
            endX = i;
        }

        drawRectangle(new Vector2(startX, y), new Vector2(endX + 1, y + 1), color);
    }

    @Override
    public void drawVerticalLine(int x, int startY, int endY, Vector3 color) {
        if (endY < startY) {
            int i = startY;
            startY = endY;
            endY = i;
        }

        drawRectangle(new Vector2(x, startY + 1), new Vector2(x + 1, endY), color);
    }

    @Override
    public void drawRectangle(Vector2 start, Vector2 end, Vector3 x) {

        glBindTexture(GL_TEXTURE_2D, emptyTexture.getID());
        glBegin(GL_QUADS);
        glColor3f(x.getXf(), x.getYf(), x.getZf());
        glVertex3d(start.getX(), start.getY(), 0.0D);
        glVertex3d(start.getX(), end.getY(), 0.0D);
        glVertex3d(end.getX(), end.getY(), 0.0D);
        glVertex3d(end.getX(), start.getY(), 0.0D);
        glEnd();
    }

    @Override
    public void drawGradientRectangle(Vector2 start, Vector2 end, Vector3 a, Vector3 b) {

        glBindTexture(GL_TEXTURE_2D, emptyTexture.getID());
        glBegin(GL_QUADS);
        glColor3f(a.getXf(), a.getYf(), a.getZf());
        glVertex3d(start.getX(), start.getY(), 0.0D);
        glVertex3d(start.getX(), end.getY(), 0.0D);
        glColor3f(a.getXf(), a.getYf(), a.getZf());
        glVertex3d(end.getX(), end.getY(), 0.0D);
        glVertex3d(end.getX(), start.getY(), 0.0D);
        glEnd();
    }

    @Override
    public void drawTexturedRectangle(Vector2 pos, Vector2 texturePos, Vector2 size) {
        float f = 0.00390625F;
        float f1 = 0.00390625F;
        int zLevel = 0;

        glBegin(GL_QUADS);
        glTexCoord2d(((texturePos.getX()) * f), ((texturePos.getY() + size.getY()) * f1));
        glVertex3d((pos.getX()), (pos.getY() + size.getY()), zLevel);

        glTexCoord2d(((texturePos.getX() + size.getX()) * f), ((texturePos.getY() + size.getY()) * f1));
        glVertex3d((pos.getX() + size.getX()), (pos.getY() + size.getY()), zLevel);

        glTexCoord2d(((texturePos.getX() + size.getX()) * f), ((texturePos.getY()) * f1));
        glVertex3d((pos.getX() + size.getX()), (pos.getY()), zLevel);

        glTexCoord2d(((texturePos.getX()) * f), ((texturePos.getY()) * f1));
        glVertex3d((pos.getX()), (pos.getY()), zLevel);
        glEnd();
    }

    @Override
    public void drawRectangleWithCustomSizedTexture(Vector2 pos, Vector2 size, Vector2 textureUV, Vector2 textureSize) {
        float f = (float) (1.0F / textureSize.getX());
        float f1 = (float) (1.0F / textureSize.getY());

        glBegin(GL_QUADS);
        glTexCoord2d((textureUV.getX() * f), ((textureUV.getY() + size.getY()) * f1));
        glVertex3d(pos.getX(), pos.getY() + size.getY(), 0.0D);

        glTexCoord2d(((textureUV.getX() + size.getX()) * f), ((textureUV.getY() + size.getY()) * f1));
        glVertex3d(pos.getX() + size.getX(), pos.getY() + size.getY(), 0.0D);

        glTexCoord2d(((textureUV.getX() + size.getX()) * f), (textureUV.getY() * f1));
        glVertex3d(pos.getX() + size.getX(), pos.getY(), 0.0D);

        glTexCoord2d((textureUV.getX() * f), (textureUV.getY() * f1));
        glVertex3d(pos.getX(), pos.getY(), 0.0D);
        glEnd();
    }

    @Override
    public void drawScaledCustomSizeRectangle(Vector2 pos, Vector2 size, Vector2 textureUV, Vector2 textureSize, Vector2 tileSize) {

    }

    @Override
    public void drawRectangleWithTextureUV(Vector2 pos, Vector2 size, Vector2 first, Vector2 end) {
        glBegin(GL_QUADS);

        glTexCoord2d(first.getX(), end.getY());
        glVertex3d(pos.getX(), pos.getY() + size.getY(), 0.0D);

        glTexCoord2d(end.getX(), end.getY());
        glVertex3d(pos.getX() + size.getX(), pos.getY() + size.getY(), 0.0D);

        glTexCoord2d(end.getX(), first.getY());
        glVertex3d(pos.getX() + size.getX(), pos.getY(), 0.0D);

        glTexCoord2d(first.getX(), first.getY());
        glVertex3d(pos.getX(), pos.getY(), 0.0D);

        glEnd();
    }

    @Override
    public void enableBlend() {
        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
    }

    @Override
    public void disableBlend() {
        glDisable(GL_BLEND);
    }
}
