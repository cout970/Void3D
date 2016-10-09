package com.cout970.editor.gui;

import com.cout970.editor.config.Config;

import javax.swing.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 * Created by cout970 on 25/06/2016.
 */
public class ControlPanelGrids {

    private JCheckBox xAxisCheckBox;
    private JCheckBox yAxisCheckBox;
    private JCheckBox zAxisCheckBox;
    private JPanel panel0;
    private JTextField posX;
    private JButton sizeXplus;
    private JButton sizeXminus;
    private JTextField posZ;
    private JButton sizeZplus;
    private JButton sizeZminus;
    private JTextField posY;
    private JButton sizeYplus;
    private JButton sizeYminus;
    private JPanel content;
    public JPanel root;

    public ControlPanelGrids() {

        xAxisCheckBox.addActionListener(e -> Config.INSTANCE.setShowGridX(xAxisCheckBox.isSelected()));
        yAxisCheckBox.addActionListener(e -> Config.INSTANCE.setShowGridY(yAxisCheckBox.isSelected()));
        zAxisCheckBox.addActionListener(e -> Config.INSTANCE.setShowGridZ(zAxisCheckBox.isSelected()));

        sizeXplus.addActionListener(e -> setXPos(Config.INSTANCE.getGridXPos() + 1));
        sizeXminus.addActionListener(e -> setXPos(Config.INSTANCE.getGridXPos() - 1));

        sizeYplus.addActionListener(e -> setYPos(Config.INSTANCE.getGridYPos() + 1));
        sizeYminus.addActionListener(e -> setYPos(Config.INSTANCE.getGridYPos() - 1));

        sizeZplus.addActionListener(e -> setZPos(Config.INSTANCE.getGridZPos() + 1));
        sizeZminus.addActionListener(e -> setZPos(Config.INSTANCE.getGridZPos() - 1));

        posX.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                try {
                    setXPos(Float.parseFloat(posX.getText()));
                } catch (NumberFormatException e0) {
                    setXPos(Config.INSTANCE.getGridXPos());
                }
            }

            @Override
            public void focusGained(FocusEvent e) {
                ((JTextField) e.getComponent()).selectAll();
                super.focusGained(e);
            }
        });

        posY.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                try {
                    setYPos(Float.parseFloat(posY.getText()));
                } catch (NumberFormatException e0) {
                    setYPos(Config.INSTANCE.getGridYPos());
                }
            }

            @Override
            public void focusGained(FocusEvent e) {
                ((JTextField) e.getComponent()).selectAll();
                super.focusGained(e);
            }
        });

        posZ.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                try {
                    setZPos(Float.parseFloat(posZ.getText()));
                } catch (NumberFormatException e0) {
                    setZPos(Config.INSTANCE.getGridZPos());
                }
            }

            @Override
            public void focusGained(FocusEvent e) {
                ((JTextField) e.getComponent()).selectAll();
                super.focusGained(e);
            }
        });

        posX.addMouseWheelListener(e -> {
            if (e.getComponent().isEnabled()) {
                float val = Float.parseFloat(posX.getText()) - e.getWheelRotation();
                posX.setText(String.valueOf(val));
                setXPos(val);
            }
        });

        posY.addMouseWheelListener(e -> {
            if (e.getComponent().isEnabled()) {
                float val = Float.parseFloat(posY.getText()) - e.getWheelRotation();
                posY.setText(String.valueOf(val));
                setYPos(val);
            }
        });

        posZ.addMouseWheelListener(e -> {
            if (e.getComponent().isEnabled()) {
                float val = Float.parseFloat(posZ.getText()) - e.getWheelRotation();
                posZ.setText(String.valueOf(val));
                setZPos(val);
            }
        });

        posX.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    try {
                        setXPos(Float.parseFloat(posX.getText()));
                    } catch (NumberFormatException e0) {
                        setXPos(Config.INSTANCE.getGridXPos());
                    }
                }
            }
        });

        posY.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    try {
                        setYPos(Float.parseFloat(posY.getText()));
                    } catch (NumberFormatException e0) {
                        setYPos(Config.INSTANCE.getGridYPos());
                    }
                }
            }
        });

        posZ.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    try {
                        setZPos(Float.parseFloat(posZ.getText()));
                    } catch (NumberFormatException e0) {
                        setZPos(Config.INSTANCE.getGridZPos());
                    }
                }
            }
        });
    }

    private void setXPos(double pos) {
        posX.setText(String.valueOf(pos));
        Config.INSTANCE.setGridXPos(pos);
    }

    private void setYPos(double pos) {
        posY.setText(String.valueOf(pos));
        Config.INSTANCE.setGridYPos(pos);
    }

    private void setZPos(double pos) {
        posZ.setText(String.valueOf(pos));
        Config.INSTANCE.setGridZPos(pos);
    }
}
