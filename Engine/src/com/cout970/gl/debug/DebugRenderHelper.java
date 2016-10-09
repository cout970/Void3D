package com.cout970.gl.debug;

import com.cout970.gl.raytrace.AABB;
import com.cout970.gl.raytrace.Ray;
import com.cout970.gl.util.vector.Vector3;

import static org.lwjgl.opengl.GL11.*;

/**
 * Created by cout970 on 02/05/2016.
 */
public class DebugRenderHelper {


    public static void drawRay(Ray mouseRay) {
        glColor3f(1, 1, 1);
        glBegin(GL_LINES);
        glNormal3f(0, 1, 0);
        glVertex3d(0, 0, 0);
        glVertex3f(
                mouseRay.getStart().getXf(),
                mouseRay.getStart().getYf(),
                mouseRay.getStart().getZf());
        glVertex3d(0, 0, 0);
        glVertex3f(
                mouseRay.getEnd().getXf(),
                mouseRay.getEnd().getYf(),
                mouseRay.getEnd().getZf());

        glVertex3f(
                mouseRay.getStart().getXf(),
                mouseRay.getStart().getYf(),
                mouseRay.getStart().getZf());
        glVertex3f(
                mouseRay.getEnd().getXf(),
                mouseRay.getEnd().getYf(),
                mouseRay.getEnd().getZf());
        glEnd();


        glColor3f(1, 1, 1);
    }

    public static void drawDebugLines() {
        glBegin(GL_LINES);

        glNormal3f(1, 0, 0);
        glColor3f(1, 0, 0);
        glVertex3d(-100, 0, 0);
        glVertex3d(100, 0, 0);

        glNormal3f(0, 1, 0);
        glColor3f(0, 1, 0);
        glVertex3d(0, -100, 0);
        glVertex3d(0, 100, 0);

        glNormal3f(0, 0, 1);
        glColor3f(0, 0, 1);
        glVertex3d(0, 0, -100);
        glVertex3d(0, 0, 100);

//        glColor3f(1, 1, 1);
//        glVertex3d(-100, -100, -100);
//        glVertex3d(100, 100, 100);

        glEnd();
    }

    public static void drawAABB(AABB aabb) {
        Vector3 inicio = aabb.getStart();
        Vector3 fin = aabb.getEnd();
        glColor3f(1, 1, 1);
        glBegin(GL_LINES);

        glNormal3f(0, 1, 0);
        glVertex3f(inicio.getXf(), inicio.getYf(), inicio.getZf());
        glVertex3f(fin.getXf(), inicio.getYf(), inicio.getZf());

        glVertex3f(inicio.getXf(), inicio.getYf(), inicio.getZf());
        glVertex3f(inicio.getXf(), fin.getYf(), inicio.getZf());

        glVertex3f(inicio.getXf(), inicio.getYf(), inicio.getZf());
        glVertex3f(inicio.getXf(), inicio.getYf(), fin.getZf());

        glVertex3f(fin.getXf(), inicio.getYf(), fin.getZf());
        glVertex3f(inicio.getXf(), inicio.getYf(), fin.getZf());

        glVertex3f(fin.getXf(), inicio.getYf(), fin.getZf());
        glVertex3f(fin.getXf(), inicio.getYf(), inicio.getZf());

        glVertex3f(fin.getXf(), inicio.getYf(), inicio.getZf());
        glVertex3f(fin.getXf(), fin.getYf(), inicio.getZf());

        glVertex3f(inicio.getXf(), inicio.getYf(), fin.getZf());
        glVertex3f(inicio.getXf(), fin.getYf(), fin.getZf());

        glVertex3f(fin.getXf(), fin.getYf(), fin.getZf());
        glVertex3f(inicio.getXf(), fin.getYf(), fin.getZf());

        glVertex3f(fin.getXf(), fin.getYf(), fin.getZf());
        glVertex3f(fin.getXf(), inicio.getYf(), fin.getZf());

        glVertex3f(fin.getXf(), fin.getYf(), fin.getZf());
        glVertex3f(fin.getXf(), fin.getYf(), inicio.getZf());

        glVertex3f(inicio.getXf(), fin.getYf(), inicio.getZf());
        glVertex3f(fin.getXf(), fin.getYf(), inicio.getZf());

        glVertex3f(inicio.getXf(), fin.getYf(), inicio.getZf());
        glVertex3f(inicio.getXf(), fin.getYf(), fin.getZf());

        glEnd();
    }
}
