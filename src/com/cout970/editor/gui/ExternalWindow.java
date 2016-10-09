package com.cout970.editor.gui;

import com.cout970.gl.util.vector.Vector2;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

/**
 * Created by cout970 on 08/06/2016.
 */
public abstract class ExternalWindow {

    public JPanel root;
    private JButton xButton;
    private JLabel label;
    private JPanel content;
    private JPanel topbar;
    private Vector2 lastPos = new Vector2();
    private Vector2 moved = new Vector2();
    private Vector2 lastLoc = new Vector2();

    public ExternalWindow() {
        this.label.setText(getLabel());
        xButton.addActionListener(e -> onClose());
        topbar.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                super.mouseDragged(e);
                int scale = 8;

                Vector2 pos = new Vector2(e.getX(), e.getY());

                if (!e.isShiftDown()) {
                    moved = new Vector2(Math.floor(moved.getX() / scale) * scale, Math.floor(moved.getY() / scale) * scale);
                }

                pos.add(moved);//moved to the new location
                moved = pos.sub(lastPos);//updated amount moved from the original place
                Vector2 loc = lastLoc.copy();
                loc.add(moved);//move the original location the amount of distance moved by the mouse

                if (!e.isShiftDown()) {
                    loc = new Vector2(Math.floor(loc.getX() / scale) * scale, Math.floor(loc.getY() / scale) * scale);
                }
                getWindow().setLocation(loc.getXi(), loc.getYi());
            }

            @Override
            public void mouseMoved(MouseEvent e) {
                super.mouseMoved(e);
                lastPos = new Vector2(e.getX(), e.getY());
            }
        });
        topbar.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                lastLoc = new Vector2(getWindow().getX(), getWindow().getY());
            }
        });
    }

    public abstract CustomWindow getWindow();

    protected abstract void onClose();

    protected abstract String getLabel();

    private void createUIComponents() {
        content = getContentPanel();
    }

    protected abstract JPanel getContentPanel();
}
