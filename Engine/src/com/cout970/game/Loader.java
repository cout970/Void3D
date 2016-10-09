package com.cout970.game;

import com.cout970.game.entity.EntityHouse;
import com.cout970.game.entity.EntityTree;
import com.cout970.game.world.WorldMap;

/**
 * Created by cout970 on 06/05/2016.
 */
public class Loader {

    public static boolean init(Game g) {

        if (!WorldMap.init(g)) { return false; }
        if (!EntityTree.init(g)) { return false; }
        if (!EntityHouse.init(g)) { return false; }
        return true;
    }
}
