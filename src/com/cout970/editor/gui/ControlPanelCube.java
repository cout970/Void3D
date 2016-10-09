package com.cout970.editor.gui;

import com.cout970.editor.modeltree.AbstractTreeNode;
import com.cout970.editor.modeltree.Cube;
import com.cout970.editor.modeltree.Group;
import com.cout970.gl.util.vector.Vector2;
import com.cout970.gl.util.vector.Vector3;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 * Created by cout970 on 07/06/2016.
 */
public class ControlPanelCube {

    public JPanel root;
    private JTextField sizeX;
    private JButton sizeXplus;
    private JButton sizeXminus;
    private JTextField nameField;
    private JScrollBar scrollBar1;
    private JScrollBar scrollBar2;
    private JScrollBar scrollBar3;
    private JButton moveRotationPointButton;
    private JPanel panel0;
    private JTextField positionX;
    private JButton positionXplus;
    private JButton positionXminus;
    private JTextField positionZ;
    private JButton positionZplus;
    private JButton positionZminus;
    private JTextField positionY;
    private JButton positionYplus;
    private JButton positionYminus;
    private JTextField rotationPointX;
    private JButton rotationPointXplus;
    private JButton rotationPointXminus;
    private JTextField rotationPointZ;
    private JButton rotationPointZplus;
    private JButton rotationPointZminus;
    private JTextField rotationPointY;
    private JButton rotationPointYplus;
    private JButton rotationPointYminus;
    private JTextField rotationX;
    private JButton rotationXplus;
    private JButton rotationXminus;
    private JTextField rotationZ;
    private JButton rotationZplus;
    private JButton rotationZminus;
    private JTextField rotationY;
    private JButton rotationYplus;
    private JButton rotationYminus;
    private JTextField textureX;
    private JButton textureXplus;
    private JButton textureXminus;
    private JTextField textureY;
    private JButton textureYplus;
    private JButton textureYminus;
    private JTextField sizeZ;
    private JButton sizeZplus;
    private JButton sizeZminus;
    private JTextField sizeY;
    private JButton sizeYplus;
    private JButton sizeYminus;
    private JCheckBox flipCheckBox;
    private JCheckBox lockRPCheckBox;

    //vars
    private Vector3 size = new Vector3(1, 1, 1);
    private Vector3 position = new Vector3();
    private Vector3 rotationPoint = new Vector3();
    private Vector3 rotation = new Vector3();
    private Vector2 textureOffset = new Vector2();
    private AbstractTreeNode model;
    private boolean block = false;

    public ControlPanelCube() {

        nameField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                setName(nameField.getText());
            }
        });

        addFocusListeners();
        addMouseWheelListeners();
        addKeyListeners();

        moveRotationPointButton.addActionListener(e -> setRotationPoint(position));

        scrollBar1.addAdjustmentListener(e -> setRotation(new Vector3(e.getValue(), rotation.getYf(), rotation.getZf())));

        scrollBar2.addAdjustmentListener(e -> setRotation(new Vector3(rotation.getXf(), e.getValue(), rotation.getZf())));

        scrollBar3.addAdjustmentListener(e -> setRotation(new Vector3(rotation.getXf(), rotation.getYf(), e.getValue())));

        sizeXplus.addActionListener(e -> setSize(new Vector3(size.getX() + 1, size.getY(), size.getZ())));
        sizeYplus.addActionListener(e -> setSize(new Vector3(size.getX(), size.getY() + 1, size.getZ())));
        sizeZplus.addActionListener(e -> setSize(new Vector3(size.getX(), size.getY(), size.getZ() + 1)));

        sizeXminus.addActionListener(e -> setSize(new Vector3(size.getX() - 1, size.getY(), size.getZ())));
        sizeYminus.addActionListener(e -> setSize(new Vector3(size.getX(), size.getY() - 1, size.getZ())));
        sizeZminus.addActionListener(e -> setSize(new Vector3(size.getX(), size.getY(), size.getZ() - 1)));

        positionXplus.addActionListener(e -> setPosition(new Vector3(position.getX() + 1, position.getY(), position.getZ())));
        positionYplus.addActionListener(e -> setPosition(new Vector3(position.getX(), position.getY() + 1, position.getZ())));
        positionZplus.addActionListener(e -> setPosition(new Vector3(position.getX(), position.getY(), position.getZ() + 1)));

        positionXminus.addActionListener(e -> setPosition(new Vector3(position.getX() - 1, position.getY(), position.getZ())));
        positionYminus.addActionListener(e -> setPosition(new Vector3(position.getX(), position.getY() - 1, position.getZ())));
        positionZminus.addActionListener(e -> setPosition(new Vector3(position.getX(), position.getY(), position.getZ() - 1)));

        rotationPointXplus.addActionListener(e -> setRotationPoint(new Vector3(rotationPoint.getX() + 1, rotationPoint.getY(), rotationPoint.getZ())));
        rotationPointYplus.addActionListener(e -> setRotationPoint(new Vector3(rotationPoint.getX(), rotationPoint.getY() + 1, rotationPoint.getZ())));
        rotationPointZplus.addActionListener(e -> setRotationPoint(new Vector3(rotationPoint.getX(), rotationPoint.getY(), rotationPoint.getZ() + 1)));

        rotationPointXminus.addActionListener(e -> setRotationPoint(new Vector3(rotationPoint.getX() - 1, rotationPoint.getY(), rotationPoint.getZ())));
        rotationPointYminus.addActionListener(e -> setRotationPoint(new Vector3(rotationPoint.getX(), rotationPoint.getY() - 1, rotationPoint.getZ())));
        rotationPointZminus.addActionListener(e -> setRotationPoint(new Vector3(rotationPoint.getX(), rotationPoint.getY(), rotationPoint.getZ() - 1)));

        rotationXplus.addActionListener(e -> setRotation(new Vector3(rotation.getX() + 1, rotation.getY(), rotation.getZ())));
        rotationYplus.addActionListener(e -> setRotation(new Vector3(rotation.getX(), rotation.getY() + 1, rotation.getZ())));
        rotationZplus.addActionListener(e -> setRotation(new Vector3(rotation.getX(), rotation.getY(), rotation.getZ() + 1)));

        rotationXminus.addActionListener(e -> setRotation(new Vector3(rotation.getX() - 1, rotation.getY(), rotation.getZ())));
        rotationYminus.addActionListener(e -> setRotation(new Vector3(rotation.getX(), rotation.getY() - 1, rotation.getZ())));
        rotationZminus.addActionListener(e -> setRotation(new Vector3(rotation.getX(), rotation.getY(), rotation.getZ() - 1)));

        textureXplus.addActionListener(e -> setTextureOffset(new Vector2(textureOffset.getX() + 1, textureOffset.getY())));
        textureYplus.addActionListener(e -> setTextureOffset(new Vector2(textureOffset.getX(), textureOffset.getY() + 1)));

        textureXminus.addActionListener(e -> setTextureOffset(new Vector2(textureOffset.getX() - 1, textureOffset.getY())));
        textureYminus.addActionListener(e -> setTextureOffset(new Vector2(textureOffset.getX(), textureOffset.getY() - 1)));

        flipCheckBox.addActionListener(e -> setFlip(flipCheckBox.isSelected()));

        enableAll(false);
    }

    public void setSize(Vector3 size) {
        size.set(Math.max(0, size.getX()), Math.max(0, size.getY()), Math.max(0, size.getZ()));
        this.size.set(size);
        sizeX.setText(String.valueOf(size.getXf()));
        sizeY.setText(String.valueOf(size.getYf()));
        sizeZ.setText(String.valueOf(size.getZf()));
        if (model instanceof Cube && !block) {
            GuiControllerKt.resizeCube((Cube) model, size);
        } else if (model instanceof Group && !block) {
            GuiControllerKt.resizeGroup((Group) model, size);
        }
    }

    public void setPosition(Vector3 pos) {
        Vector3 diff = pos.copy().sub(position);
        if (!lockRPCheckBox.isSelected()) {
            setRotationPoint(this.rotationPoint.copy().add(diff));
        }
        this.position.set(pos);
        positionX.setText(String.valueOf(pos.getXf()));
        positionY.setText(String.valueOf(pos.getYf()));
        positionZ.setText(String.valueOf(pos.getZf()));
        if (model instanceof Cube && !block) {
            GuiControllerKt.moveCube((Cube) model, pos);
        } else if (model instanceof Group && !block) {
            GuiControllerKt.moveGroup((Group) model, pos);
        }
    }

    public void setRotationPoint(Vector3 pos) {
        this.rotationPoint.set(pos);
        rotationPointX.setText(String.valueOf(pos.getXf()));
        rotationPointY.setText(String.valueOf(pos.getYf()));
        rotationPointZ.setText(String.valueOf(pos.getZf()));
        if (model instanceof Cube && !block) {
            GuiControllerKt.moveRotationPointCube((Cube) model, pos);
        } else if (model instanceof Group && !block) {
            GuiControllerKt.moveRotationPointGroup((Group) model, pos);
        }
    }

    public void setRotation(Vector3 p) {
        Vector3 pos = new Vector3((p.getXi() + 180) % 360, (p.getYi() + 180) % 360, (p.getZi() + 180) % 360);
        if (pos.getX() < 0) {
            pos.setX(pos.getX() + 360);
        }
        if (pos.getY() < 0) {
            pos.setY(pos.getY() + 360);
        }
        if (pos.getZ() < 0) {
            pos.setZ(pos.getZ() + 360);
        }

        pos.sub(180, 180, 180);
        this.rotation.set(pos);
        rotationX.setText(String.valueOf(pos.getXf()));
        rotationY.setText(String.valueOf(pos.getYf()));
        rotationZ.setText(String.valueOf(pos.getZf()));

        scrollBar1.setValue(p.getXi());
        scrollBar2.setValue(p.getYi());
        scrollBar3.setValue(p.getZi());
        if (model instanceof Cube && !block) {
            GuiControllerKt.rotateCube((Cube) model, pos);
        } else if (model instanceof Group && !block) {
            GuiControllerKt.rotateGroup((Group) model, pos);
        }
    }

    public void setTextureOffset(Vector2 pos) {
        this.textureOffset.set(pos);
        textureX.setText(String.valueOf(pos.getXf()));
        textureY.setText(String.valueOf(pos.getYf()));
        if (model instanceof Cube && !block) {
            GuiControllerKt.changeTextureOffsetCube((Cube) model, pos);
        }
    }

    public void setName(String name) {
        nameField.setText(name);
        if (model instanceof Cube && !block) {
            GuiControllerKt.changeNameCube((Cube) model, name);
        } else if (model instanceof Group && !block) {
            GuiControllerKt.changeNameGroup((Group) model, name);
        }
    }

    public void setFlip(boolean flip) {
        flipCheckBox.setSelected(flip);
        if (model instanceof Cube && !block) {
            GuiControllerKt.flipUVCube((Cube) model, flip);
        }
    }

    public void unselectCube() {
        nameField.setText("");
        enableAll(false);
    }

    public void selectCube(Cube model) {
        this.model = model;
        enableAll(true);
        block = true;
        setName(model.getName());
        setSize(model.getSize());
        setPosition(model.getPosition());
        setRotationPoint(model.getRotationPoint());
        setRotation(model.getRotation());
        setTextureOffset(model.getTextureOffset());
        setFlip(model.getFlipUV());
        block = false;
        model.update();
    }

    public void selectGroup(@NotNull Group model) {
        this.model = model;
        enableAll(false);
        enableGroup(true);
        block = true;
        setName(model.getName());
        setSize(model.getSize());
        setPosition(model.getPosition());
        setRotationPoint(model.getRotationPoint());
        setRotation(model.getRotation());
        block = false;
        model.update();
    }

    private void enableAll(boolean en) {

        nameField.setEnabled(en);

        sizeX.setEnabled(en);
        sizeZ.setEnabled(en);
        sizeY.setEnabled(en);

        positionX.setEnabled(en);
        positionY.setEnabled(en);
        positionZ.setEnabled(en);

        rotationPointX.setEnabled(en);
        rotationPointY.setEnabled(en);
        rotationPointZ.setEnabled(en);

        textureX.setEnabled(en);
        textureY.setEnabled(en);
        flipCheckBox.setEnabled(en);

        rotationX.setEnabled(en);
        rotationZ.setEnabled(en);
        rotationY.setEnabled(en);

        scrollBar1.setEnabled(en);
        scrollBar2.setEnabled(en);
        scrollBar3.setEnabled(en);

        moveRotationPointButton.setEnabled(en);

        sizeXplus.setEnabled(en);
        sizeYplus.setEnabled(en);
        sizeZplus.setEnabled(en);
        sizeXminus.setEnabled(en);
        sizeYminus.setEnabled(en);
        sizeZminus.setEnabled(en);
        positionXplus.setEnabled(en);
        positionYplus.setEnabled(en);
        positionZplus.setEnabled(en);
        positionXminus.setEnabled(en);
        positionYminus.setEnabled(en);
        positionZminus.setEnabled(en);
        rotationPointXplus.setEnabled(en);
        rotationPointYplus.setEnabled(en);
        rotationPointZplus.setEnabled(en);
        rotationPointXminus.setEnabled(en);
        rotationPointYminus.setEnabled(en);
        rotationPointZminus.setEnabled(en);
        rotationXplus.setEnabled(en);
        rotationZplus.setEnabled(en);
        rotationYplus.setEnabled(en);
        rotationXminus.setEnabled(en);
        rotationZminus.setEnabled(en);
        rotationYminus.setEnabled(en);
        textureXplus.setEnabled(en);
        textureYplus.setEnabled(en);
        textureXminus.setEnabled(en);
        textureYminus.setEnabled(en);

        lockRPCheckBox.setEnabled(en);
    }

    private void enableGroup(boolean en) {

        nameField.setEnabled(en);

        sizeX.setEnabled(en);
        sizeZ.setEnabled(en);
        sizeY.setEnabled(en);

        positionX.setEnabled(en);
        positionY.setEnabled(en);
        positionZ.setEnabled(en);

        rotationPointX.setEnabled(en);
        rotationPointY.setEnabled(en);
        rotationPointZ.setEnabled(en);

        rotationX.setEnabled(en);
        rotationZ.setEnabled(en);
        rotationY.setEnabled(en);

        scrollBar1.setEnabled(en);
        scrollBar2.setEnabled(en);
        scrollBar3.setEnabled(en);

        moveRotationPointButton.setEnabled(en);

        sizeXplus.setEnabled(en);
        sizeYplus.setEnabled(en);
        sizeZplus.setEnabled(en);
        sizeXminus.setEnabled(en);
        sizeYminus.setEnabled(en);
        sizeZminus.setEnabled(en);
        positionXplus.setEnabled(en);
        positionYplus.setEnabled(en);
        positionZplus.setEnabled(en);
        positionXminus.setEnabled(en);
        positionYminus.setEnabled(en);
        positionZminus.setEnabled(en);
        rotationPointXplus.setEnabled(en);
        rotationPointYplus.setEnabled(en);
        rotationPointZplus.setEnabled(en);
        rotationPointXminus.setEnabled(en);
        rotationPointYminus.setEnabled(en);
        rotationPointZminus.setEnabled(en);
        rotationXplus.setEnabled(en);
        rotationZplus.setEnabled(en);
        rotationYplus.setEnabled(en);
        rotationXminus.setEnabled(en);
        rotationZminus.setEnabled(en);
        rotationYminus.setEnabled(en);
    }

    private void addKeyListeners() {
        sizeX.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    try {
                        float val = Float.parseFloat(sizeX.getText());
                        setSize(new Vector3(val, size.getY(), size.getZ()));
                    } catch (NumberFormatException e0) {
                        setSize(size);
                    }
                }
            }
        });
        sizeY.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    try {
                        float val = Float.parseFloat(sizeY.getText());
                        setSize(new Vector3(size.getX(), val, size.getZ()));
                    } catch (NumberFormatException e0) {
                        setSize(size);
                    }
                }
            }
        });
        sizeZ.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    try {
                        float val = Float.parseFloat(sizeZ.getText());
                        setSize(new Vector3(size.getX(), size.getY(), val));
                    } catch (NumberFormatException e0) {
                        setSize(size);
                    }
                }
            }
        });

        positionX.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    try {
                        float val = Float.parseFloat(positionX.getText());
                        setPosition(new Vector3(val, position.getY(), position.getZ()));
                    } catch (NumberFormatException e0) {
                        setPosition(position);
                    }
                }
            }
        });
        positionY.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    try {
                        float val = Float.parseFloat(positionY.getText());
                        setPosition(new Vector3(position.getX(), val, position.getZ()));
                    } catch (NumberFormatException e0) {
                        setPosition(position);
                    }
                }
            }
        });
        positionZ.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    try {
                        float val = Float.parseFloat(positionZ.getText());
                        setPosition(new Vector3(position.getX(), position.getY(), val));
                    } catch (NumberFormatException e0) {
                        setPosition(position);
                    }
                }
            }
        });

        rotationX.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    try {
                        float val = Float.parseFloat(rotationX.getText());
                        setRotation(new Vector3(val, rotation.getY(), rotation.getZ()));
                    } catch (NumberFormatException e0) {
                        setRotation(rotation);
                    }
                }
            }
        });
        rotationY.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    try {
                        float val = Float.parseFloat(rotationY.getText());
                        setRotation(new Vector3(rotation.getX(), val, rotation.getZ()));
                    } catch (NumberFormatException e0) {
                        setRotation(rotation);
                    }
                }
            }
        });
        rotationZ.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    try {
                        float val = Float.parseFloat(rotationZ.getText());
                        setRotation(new Vector3(rotation.getX(), rotation.getY(), val));
                    } catch (NumberFormatException e0) {
                        setRotation(rotation);
                    }
                }
            }
        });

        rotationPointX.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    try {
                        float val = Float.parseFloat(rotationPointX.getText());
                        setRotationPoint(new Vector3(val, rotationPoint.getY(), rotationPoint.getZ()));
                    } catch (NumberFormatException e0) {
                        setRotationPoint(rotationPoint);
                    }
                }
            }
        });
        rotationPointY.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    try {
                        float val = Float.parseFloat(rotationPointY.getText());
                        setRotationPoint(new Vector3(rotationPoint.getX(), val, rotationPoint.getZ()));
                    } catch (NumberFormatException e0) {
                        setRotationPoint(rotationPoint);
                    }
                }
            }
        });
        rotationPointZ.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    try {
                        float val = Float.parseFloat(rotationPointZ.getText());
                        setRotationPoint(new Vector3(rotationPoint.getX(), rotationPoint.getY(), val));
                    } catch (NumberFormatException e0) {
                        setRotationPoint(rotationPoint);
                    }
                }
            }
        });

        textureX.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    try {
                        float val = Float.parseFloat(textureX.getText());
                        setTextureOffset(new Vector2(val, textureOffset.getY()));
                    } catch (NumberFormatException e0) {
                        setTextureOffset(textureOffset);
                    }
                }
            }
        });
        textureY.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    try {
                        float val = Float.parseFloat(textureY.getText());
                        setTextureOffset(new Vector2(textureOffset.getX(), val));
                    } catch (NumberFormatException e0) {
                        setTextureOffset(textureOffset);
                    }
                }
            }
        });
    }


    private void addFocusListeners() {

        sizeX.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                try {
                    float val = Float.parseFloat(sizeX.getText());
                    setSize(new Vector3(val, size.getY(), size.getZ()));
                } catch (NumberFormatException e0) {
                    setSize(size);
                }
            }

            @Override
            public void focusGained(FocusEvent e) {
                ((JTextField) e.getComponent()).selectAll();
                super.focusGained(e);
            }
        });

        sizeY.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                try {
                    float val = Float.parseFloat(sizeY.getText());
                    setSize(new Vector3(size.getX(), val, size.getZ()));
                } catch (NumberFormatException e0) {
                    setSize(size);
                }
            }

            @Override
            public void focusGained(FocusEvent e) {
                ((JTextField) e.getComponent()).selectAll();
                super.focusGained(e);
            }
        });

        sizeZ.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                try {
                    float val = Float.parseFloat(sizeZ.getText());
                    setSize(new Vector3(size.getX(), size.getY(), val));
                } catch (NumberFormatException e0) {
                    setSize(size);
                }
            }

            @Override
            public void focusGained(FocusEvent e) {
                ((JTextField) e.getComponent()).selectAll();
                super.focusGained(e);
            }
        });

        positionX.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                try {
                    float val = Float.parseFloat(positionX.getText());
                    setPosition(new Vector3(val, position.getY(), position.getZ()));
                } catch (NumberFormatException e0) {
                    setPosition(position);
                }
            }

            @Override
            public void focusGained(FocusEvent e) {
                ((JTextField) e.getComponent()).selectAll();
                super.focusGained(e);
            }
        });

        positionY.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                try {
                    float val = Float.parseFloat(positionY.getText());
                    setPosition(new Vector3(position.getX(), val, position.getZ()));
                } catch (NumberFormatException e0) {
                    setPosition(position);
                }
            }

            @Override
            public void focusGained(FocusEvent e) {
                ((JTextField) e.getComponent()).selectAll();
                super.focusGained(e);
            }
        });

        positionZ.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                try {
                    float val = Float.parseFloat(positionZ.getText());
                    setPosition(new Vector3(position.getX(), position.getY(), val));
                } catch (NumberFormatException e0) {
                    setPosition(position);
                }
            }

            @Override
            public void focusGained(FocusEvent e) {
                ((JTextField) e.getComponent()).selectAll();
                super.focusGained(e);
            }
        });

        rotationPointX.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                try {
                    float val = Float.parseFloat(rotationPointX.getText());
                    setRotationPoint(new Vector3(val, rotationPoint.getY(), rotationPoint.getZ()));
                } catch (NumberFormatException e0) {
                    setRotationPoint(rotationPoint);
                }
            }

            @Override
            public void focusGained(FocusEvent e) {
                ((JTextField) e.getComponent()).selectAll();
                super.focusGained(e);
            }
        });

        rotationPointY.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                try {
                    float val = Float.parseFloat(rotationPointY.getText());
                    setRotationPoint(new Vector3(rotationPoint.getX(), val, rotationPoint.getZ()));
                } catch (NumberFormatException e0) {
                    setRotationPoint(rotationPoint);
                }
            }

            @Override
            public void focusGained(FocusEvent e) {
                ((JTextField) e.getComponent()).selectAll();
                super.focusGained(e);
            }
        });

        rotationPointZ.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                try {
                    float val = Float.parseFloat(rotationPointZ.getText());
                    setRotationPoint(new Vector3(rotationPoint.getX(), rotationPoint.getY(), val));
                } catch (NumberFormatException e0) {
                    setRotationPoint(rotationPoint);
                }
            }

            @Override
            public void focusGained(FocusEvent e) {
                ((JTextField) e.getComponent()).selectAll();
                super.focusGained(e);
            }
        });

        rotationX.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                try {
                    float val = Float.parseFloat(rotationX.getText());
                    setRotation(new Vector3(val, rotation.getY(), rotation.getZ()));
                } catch (NumberFormatException e0) {
                    setRotation(rotation);
                }
            }

            @Override
            public void focusGained(FocusEvent e) {
                ((JTextField) e.getComponent()).selectAll();
                super.focusGained(e);
            }
        });

        rotationY.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                try {
                    float val = Float.parseFloat(rotationY.getText());
                    setRotation(new Vector3(rotation.getX(), val, rotation.getZ()));
                } catch (NumberFormatException e0) {
                    setRotation(rotation);
                }
            }

            @Override
            public void focusGained(FocusEvent e) {
                ((JTextField) e.getComponent()).selectAll();
                super.focusGained(e);
            }
        });

        rotationZ.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                try {
                    float val = Float.parseFloat(rotationZ.getText());
                    setRotation(new Vector3(rotation.getX(), rotation.getY(), val));
                } catch (NumberFormatException e0) {
                    setRotation(rotation);
                }
            }

            @Override
            public void focusGained(FocusEvent e) {
                ((JTextField) e.getComponent()).selectAll();
                super.focusGained(e);
            }
        });


        textureX.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                try {
                    float val = Float.parseFloat(textureX.getText());
                    setTextureOffset(new Vector2(val, textureOffset.getY()));
                } catch (NumberFormatException e0) {
                    setTextureOffset(textureOffset);
                }
            }

            @Override
            public void focusGained(FocusEvent e) {
                ((JTextField) e.getComponent()).selectAll();
                super.focusGained(e);
            }
        });

        textureY.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                try {
                    float val = Float.parseFloat(textureY.getText());
                    setTextureOffset(new Vector2(textureOffset.getX(), val));
                } catch (NumberFormatException e0) {
                    setTextureOffset(textureOffset);
                }
            }

            @Override
            public void focusGained(FocusEvent e) {
                ((JTextField) e.getComponent()).selectAll();
                super.focusGained(e);
            }
        });
    }

    private void addMouseWheelListeners() {
        sizeX.addMouseWheelListener(e -> {
            if (e.getComponent().isEnabled()) {
                float val = Float.parseFloat(sizeX.getText()) - e.getWheelRotation();
                sizeX.setText(String.valueOf(Math.max(0, val)));
                setSize(new Vector3(val, size.getY(), size.getZ()));
            }
        });

        sizeY.addMouseWheelListener(e -> {
            if (e.getComponent().isEnabled()) {
                float val = Float.parseFloat(sizeY.getText()) - e.getWheelRotation();
                sizeY.setText(String.valueOf(Math.max(0, val)));
                setSize(new Vector3(size.getX(), val, size.getZ()));
            }
        });
        sizeZ.addMouseWheelListener(e -> {
            if (e.getComponent().isEnabled()) {

                float val = Float.parseFloat(sizeZ.getText()) - e.getWheelRotation();
                sizeZ.setText(String.valueOf(Math.max(0, val)));
                setSize(new Vector3(size.getX(), size.getY(), val));
            }
        });

        positionX.addMouseWheelListener(e -> {
            if (e.getComponent().isEnabled()) {
                float val = Float.parseFloat(positionX.getText()) - e.getWheelRotation();
                positionX.setText(String.valueOf(val));
                setPosition(new Vector3(val, position.getY(), position.getZ()));
            }
        });

        positionY.addMouseWheelListener(e -> {
            if (e.getComponent().isEnabled()) {
                float val = Float.parseFloat(positionY.getText()) - e.getWheelRotation();
                positionY.setText(String.valueOf(val));
                setPosition(new Vector3(position.getX(), val, position.getZ()));
            }
        });
        positionZ.addMouseWheelListener(e -> {
            if (e.getComponent().isEnabled()) {

                float val = Float.parseFloat(positionZ.getText()) - e.getWheelRotation();
                positionZ.setText(String.valueOf(val));
                setPosition(new Vector3(position.getX(), position.getY(), val));
            }
        });

        rotationPointX.addMouseWheelListener(e -> {
            if (e.getComponent().isEnabled()) {
                float val = Float.parseFloat(rotationPointX.getText()) - e.getWheelRotation();
                rotationPointX.setText(String.valueOf(val));
                setRotationPoint(new Vector3(val, rotationPoint.getY(), rotationPoint.getZ()));
            }
        });

        rotationPointY.addMouseWheelListener(e -> {
            if (e.getComponent().isEnabled()) {
                float val = Float.parseFloat(rotationPointY.getText()) - e.getWheelRotation();
                rotationPointY.setText(String.valueOf(val));
                setRotationPoint(new Vector3(rotationPoint.getX(), val, rotationPoint.getZ()));
            }
        });
        rotationPointZ.addMouseWheelListener(e -> {
            if (e.getComponent().isEnabled()) {

                float val = Float.parseFloat(rotationPointZ.getText()) - e.getWheelRotation();
                rotationPointZ.setText(String.valueOf(val));
                setRotationPoint(new Vector3(rotationPoint.getX(), rotationPoint.getY(), val));
            }
        });

        rotationX.addMouseWheelListener(e -> {
            if (e.getComponent().isEnabled()) {
                float val = Float.parseFloat(rotationX.getText()) - e.getWheelRotation();
                rotationX.setText(String.valueOf(val));
                setRotation(new Vector3(val, rotation.getY(), rotation.getZ()));
            }
        });

        rotationY.addMouseWheelListener(e -> {
            if (e.getComponent().isEnabled()) {
                float val = Float.parseFloat(rotationY.getText()) - e.getWheelRotation();
                rotationY.setText(String.valueOf(val));
                setRotation(new Vector3(rotation.getX(), val, rotation.getZ()));
            }
        });
        rotationZ.addMouseWheelListener(e -> {
            if (e.getComponent().isEnabled()) {

                float val = Float.parseFloat(rotationZ.getText()) - e.getWheelRotation();
                rotationZ.setText(String.valueOf(val));
                setRotation(new Vector3(rotation.getX(), rotation.getY(), val));
            }
        });

        textureX.addMouseWheelListener(e -> {
            if (e.getComponent().isEnabled()) {
                float val = Float.parseFloat(textureX.getText()) - e.getWheelRotation();
                textureX.setText(String.valueOf(val));
                setTextureOffset(new Vector2(val, textureOffset.getY()));
            }
        });

        textureY.addMouseWheelListener(e -> {
            if (e.getComponent().isEnabled()) {
                float val = Float.parseFloat(textureY.getText()) - e.getWheelRotation();
                textureY.setText(String.valueOf(val));
                setTextureOffset(new Vector2(textureOffset.getX(), val));
            }
        });
    }
}
