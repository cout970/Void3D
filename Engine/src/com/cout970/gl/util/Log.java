package com.cout970.gl.util;

public class Log {

    public static void debug(Object obj) {
        log("[DEBUG] " + String.valueOf(obj));
    }

    public static void error(String s) {
        log("[ERROR] " + s);
    }

    private static void log(String message) {
        System.out.println(message);
    }
}
