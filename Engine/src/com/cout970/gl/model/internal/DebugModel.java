package com.cout970.gl.model.internal;

import static org.lwjgl.opengl.GL11.*;

/**
 * Created by cout970 on 27/04/2016.
 */
public class DebugModel{

    public void render() {
        glBegin(GL_LINES);
        glNormal3f(0,1,0);

        glColor3f(1, 0, 0);
        glVertex3d(-100, 0, 0);
        glVertex3d(100, 0, 0);

        glColor3f(0, 1, 0);
        glVertex3d(0, -100, 0);
        glVertex3d(0, 100, 0);

        glColor3f(0, 0, 1);
        glVertex3d(0, 0, -100);
        glVertex3d(0, 0, 100);

        glColor3f(1, 1, 1);
        glVertex3d(-100, -100, -100);
        glVertex3d(100, 100, 100);

        glEnd();

        glColor3f(1, 1, 1);
    }
}
