package com.cout970.gl.raytrace;

import org.lwjgl.BufferUtils;

import java.nio.IntBuffer;

/**
 * Created by cout970 on 15/02/2016.
 */
public class Util {
    /**
     * temp IntBuffer of one for getting an int from some GL functions
     */
    private static IntBuffer scratch = BufferUtils.createIntBuffer(16);

    /**
     * Return ceiling of integer division
     *
     * @param a
     * @param b
     * @return int
     */
    protected static int ceil(int a, int b) {
        return (a % b == 0 ? a / b : a / b + 1);
    }

    /**
     * Normalize vector
     *
     * @param v
     * @return float[]
     */
    protected static float[] normalize(float[] v) {
        float r;

        r = (float) Math.sqrt(v[0] * v[0] + v[1] * v[1] + v[2] * v[2]);
        if (r == 0.0)
            return v;

        r = 1.0f / r;

        v[0] *= r;
        v[1] *= r;
        v[2] *= r;

        return v;
    }

    /**
     * Calculate cross-product
     *
     * @param v1
     * @param v2
     * @param result
     */
    protected static void cross(float[] v1, float[] v2, float[] result) {
        result[0] = v1[1] * v2[2] - v1[2] * v2[1];
        result[1] = v1[2] * v2[0] - v1[0] * v2[2];
        result[2] = v1[0] * v2[1] - v1[1] * v2[0];
    }

    /**
     * Method nearestPower.
     * <p>
     * Compute the nearest power of 2 number.  This algorithm is a little strange, but it works quite well.
     *
     * @param value
     * @return int
     */
    protected static int nearestPower(int value) {
        int i;

        i = 1;

		/* Error! */
        if (value == 0)
            return -1;

        for (; ; ) {
            if (value == 1) {
                return i;
            } else if (value == 3) {
                return i << 2;
            }
            value >>= 1;
            i <<= 1;
        }
    }
}
