package com.cout970.game;

/**
 * Created by cout970 on 27/04/2016.
 */
public class Principal {

    public static void main(String[] args) {
        Game g = new Game(new CustomWindow());
        g.run();
        g.close();
    }
}
