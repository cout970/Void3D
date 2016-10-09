package com.cout970.gl.raytrace;

import com.cout970.gl.util.vector.Vector3;

/**
 * Created by cout970 on 02/05/2016.
 */
public class RayTraceUtil {

    public static RayTraceResult rayTraceQuad(Ray ray, IRayObstacle obstacle, Vector3 a, Vector3 b, Vector3 c, Vector3 d){
        RayTraceResult r0 = rayTraceTriangle(ray, obstacle, a, b, c);
        if (r0 != null) {
            return r0;
        }
        RayTraceResult r1 = rayTraceTriangle(ray, obstacle, a, d, c);
        if (r1 != null) {
            return r1;
        }
        return null;
    }

    public static RayTraceResult rayTraceTriangle(Ray ray, IRayObstacle obstacle, Vector3 a, Vector3 b, Vector3 c) {
        Vector3 i = intersectTriangle(ray, a, b, c);
        if (i == null) return null;

        Vector3 edge0 = b.copy().sub(a);
        Vector3 edge1 = c.copy().sub(b);
        Vector3 edge2 = a.copy().sub(c);

        Vector3 C0 = i.copy().sub(a);
        Vector3 C1 = i.copy().sub(b);
        Vector3 C2 = i.copy().sub(c);

        Vector3 ab = b.copy().sub(a);
        Vector3 ac = c.copy().sub(a);
        Vector3 N = ab.cross(ac);

        if (N.dot(edge0.cross(C0)) >= 0 &&
                N.dot(edge1.cross(C1)) >= 0 &&
                N.dot(edge2.cross(C2)) >= 0) {
            return new RayTraceResult(ray, i, obstacle);
        }
        return null;
    }

    public static Vector3 intersectTriangle(Ray r, Vector3 a, Vector3 b, Vector3 c) {
        Vector3 ab = b.copy().sub(a);
        Vector3 ac = c.copy().sub(a);
        Vector3 n = ab.cross(ac);
        Vector3 v = r.getEnd().sub(r.getStart());
        Vector3 w = a.copy().sub(r.getStart());
        double div = v.dot(n);
        if (Math.abs(div) < 0.0000000001) {
            return null;
        }
        double k = w.dot(n) / v.dot(n);
        return r.getStart().add(v.copy().mul(k));
    }
}
