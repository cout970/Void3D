package com.cout970.game;

import com.cout970.gl.util.vector.Vector2;
import com.cout970.gl.util.vector.Vector3;
import com.cout970.gl.window.IWindowProperties;
import com.cout970.gl.window.Window;

/**
 * Created by cout970 on 01/05/2016.
 */
public class CustomWindow extends Window {

    public CustomWindow() {
        super(new IWindowProperties() {
            @Override
            public Vector2 getSize() {
                return new Vector2(800, 600);
            }

            @Override
            public String getTitle() {
                return "Game";
            }

            @Override
            public Vector3 getBackground() {
                return new Vector3(0.5444f, 0.62f, 0.69f);
            }
        });
    }

    @Override
    protected void onChange(int x, int y) {

    }
}
