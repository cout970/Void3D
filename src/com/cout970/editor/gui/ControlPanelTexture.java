package com.cout970.editor.gui;

import com.cout970.editor.Editor;
import com.cout970.editor.modeltree.AbstractTreeNode;
import com.cout970.editor.modeltree.Cube;
import com.cout970.editor.renderer.util.Quad;
import com.cout970.editor.texture.TextureExportUtil;
import com.cout970.editor.texture.TextureLoader;
import com.cout970.editor.util.Log;
import com.cout970.gl.model.IMaterial;
import com.cout970.gl.resource.internal.ResourceLocation;
import com.cout970.gl.util.vector.Vector2;
import kotlin.Unit;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.util.Vector;

/**
 * Created by cout970 on 07/06/2016.
 */
public class ControlPanelTexture {

    public JPanel root;
    private JButton loadTextureButton;
    private JCheckBox showGridCheckBox;
    private JCheckBox useTransparencyCheckBox;
    private JPanel texture;
    private JButton exportTextureButton;
    private Vector2 scale = new Vector2(1, 1);
    private Vector2 offset = new Vector2();
    //mouse drag
    private Vector2 lastMousePos = new Vector2();
    private Vector2 lastOffset = new Vector2();
    private Vector2 moved = new Vector2();

    public ControlPanelTexture() {
        showGridCheckBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                WindowTexture.INSTANCE.getWindow().repaint();
            }
        });
        loadTextureButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                File file = WindowPopupHandlerKt.showTextureLoadPopup();
                if (file != null) {
                    Editor.INSTANCE.getTextureManager().setModelTextureLocation(
                            new ResourceLocation(Editor.INSTANCE.getResourceManager().getEXTERNAL_RESOURCE(), file.getAbsolutePath()));
                    Editor.INSTANCE.getTextureManager().refreshMainTexture();
                }
            }
        });
        texture.addMouseWheelListener(new MouseWheelListener() {
            @Override
            public void mouseWheelMoved(MouseWheelEvent e) {
                float amount = scale.getXf();
                float old = scale.getXf();
                if (e.getWheelRotation() > 0) {
                    amount -= 4 / 16f;
                } else {
                    amount += 4 / 16f;
                }
                if (amount < 1) {
                    amount = 1;
                }
                scale.set(amount, amount);
                offset.mul(scale.getXf() / old);
                lastOffset.mul(scale.getXf() / old);
                WindowTexture.INSTANCE.getWindow().repaint();
            }
        });
        texture.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                super.mouseDragged(e);
                int scale = 4;

                //actual mouse position
                Vector2 pos = new Vector2(e.getX(), e.getY());
                //position of the mouse before start dragging
                Vector2 lastPos = lastMousePos.copy();

                //displacement
                moved.set(lastPos).sub(pos);

                if (e.isShiftDown()) {
                    moved = new Vector2(Math.floor(moved.getX() / scale) * scale, Math.floor(moved.getY() / scale) * scale);
                }

                offset.set(lastOffset.copy().sub(moved));
            }

            @Override
            public void mouseMoved(MouseEvent e) {
                super.mouseMoved(e);
                lastMousePos.set(e.getX(), e.getY());
                lastOffset.set(offset);
            }
        });
        texture.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                lastMousePos.set(e.getX(), e.getY());
                lastOffset.set(offset);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                super.mouseReleased(e);
                lastOffset.set(offset);
            }
        });
        exportTextureButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                File result = WindowPopupHandlerKt.showTextureExportPopup();
                if (result != null) {
                    Log.INSTANCE.info("Starting task: export texture");
                    TextureExportUtil.INSTANCE.exportTexture(result);
                    Log.INSTANCE.info("Task finished: export texture");
                }
            }
        });
    }

    private void createUIComponents() {
        texture = new JPanel() {
            @Override
            public void paint(Graphics g) {
                super.paint(g);

                double texScale = getWidth() / TextureLoader.INSTANCE.getTextureScale();
                if (Editor.INSTANCE.getTextureManager().getModelTextureImage() != null) {
                    g.drawImage(Editor.INSTANCE.getTextureManager().getModelTextureImage(), offset.getXi(), offset.getYi(), (int) Math.floor(scale.getX() * getWidth()), (int) Math.floor(scale.getY() * getWidth()), Color.LIGHT_GRAY, null);
                }

                Color[] colors = {new Color(0xFF0000), new Color(0x8B0000), new Color(0x006400), new Color(0x008000), new Color(0x00008B), new Color(0x0000FF)};
                Editor.INSTANCE.getModelTree().iterate(tree -> {

                    int color = 0;
                    Vector<Quad> quads = new Vector<>(tree.getQuads());
                    for (Quad q : quads) {
                        Vector2 a = q.getA().getUv().copy().mul(texScale).mul(scale).add(offset);
                        Vector2 b = q.getB().getUv().copy().mul(texScale).mul(scale).add(offset);
                        Vector2 c = q.getC().getUv().copy().mul(texScale).mul(scale).add(offset);
                        Vector2 d = q.getD().getUv().copy().mul(texScale).mul(scale).add(offset);
                        if (useTransparencyCheckBox.isSelected()) {
                            Color col = colors[color];
                            g.setColor(new Color(col.getRed(), col.getGreen(), col.getBlue(), 127));
                        } else {
                            g.setColor(colors[color]);
                        }
                        g.fillPolygon(new int[]{
                                (int) StrictMath.rint(a.getX()),
                                (int) StrictMath.rint(b.getX()),
                                (int) StrictMath.rint(c.getX()),
                                (int) StrictMath.rint(d.getX())
                        }, new int[]{
                                (int) StrictMath.rint(a.getY()),
                                (int) StrictMath.rint(b.getY()),
                                (int) StrictMath.rint(c.getY()),
                                (int) StrictMath.rint(d.getY())
                        }, 4);
                        color = (color + 1) % colors.length;
                    }
                    return Unit.INSTANCE;
                }, tree -> tree instanceof Cube);

                AbstractTreeNode selected = Editor.INSTANCE.getModelTree().getSelectedPart();
                if (selected instanceof Cube) {
                    Vector<Quad> quads = new Vector<>(selected.getQuads());
                    for (Quad q : quads) {
                        Vector2 a = q.getA().getUv().copy().mul(texScale).mul(scale).add(offset);
                        Vector2 b = q.getB().getUv().copy().mul(texScale).mul(scale).add(offset);
                        Vector2 c = q.getC().getUv().copy().mul(texScale).mul(scale).add(offset);
                        Vector2 d = q.getD().getUv().copy().mul(texScale).mul(scale).add(offset);
                        g.setColor(Color.CYAN);
                        g.drawPolygon(new int[]{
                                (int) StrictMath.rint(a.getX()),
                                (int) StrictMath.rint(b.getX()),
                                (int) StrictMath.rint(c.getX()),
                                (int) StrictMath.rint(d.getX())
                        }, new int[]{
                                (int) StrictMath.rint(a.getY()),
                                (int) StrictMath.rint(b.getY()),
                                (int) StrictMath.rint(c.getY()),
                                (int) StrictMath.rint(d.getY())
                        }, 4);
                    }
                }

                g.setColor(Color.BLACK);

                if (showGridCheckBox.isSelected()) {
                    IMaterial mat = TextureLoader.INSTANCE.getModelTexture();
                    if (mat != null) {
                        int maxX = (int) (this.getWidth() * scale.getX());
                        int maxY = (int) (this.getWidth() * scale.getY());
                        int size = (int) TextureLoader.INSTANCE.getTextureScale();
                        float scale = maxX / (float) size;
                        if (scale >= 4) {
                            for (int i = 0; i <= size; i++) {
                                g.drawLine((int) (scale * i) + offset.getXi(), offset.getYi(), (int) (scale * i) + offset.getXi(), maxY + offset.getYi());
                            }
                            for (int i = 0; i <= size; i++) {
                                g.drawLine(offset.getXi(), (int) (scale * i) + offset.getYi(), maxX + offset.getXi(), (int) (scale * i) + offset.getYi());
                            }
                        }
                    }
                }
            }
        };
    }
}
