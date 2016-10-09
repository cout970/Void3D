package com.cout970.editor.gui;

import com.cout970.editor.Editor;
import com.cout970.editor.modeltree.AbstractTreeNode;
import com.cout970.editor.modeltree.Cube;
import com.cout970.editor.modeltree.Group;
import com.cout970.editor.modeltree.ModelTree;
import com.cout970.editor.util.Log;
import com.cout970.gl.resource.IResource;
import com.cout970.gl.resource.internal.ResourceLocation;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.TreePath;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.LinkedList;

/**
 * Created by cout970 on 07/06/2016.
 */
public class ControlPanelModelTree {

    public JPanel root;
    private JButton cubeButton;
    private JButton quadButton;
    public JTree tree1;
    private JButton groupButton;
    private JLabel label;

    public ControlPanelModelTree() {
        cubeButton.addActionListener(e -> GuiControllerKt.newCube());
        quadButton.setEnabled(false);
        groupButton.addActionListener(e -> GuiControllerKt.newGroup());
        tree1.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                super.keyPressed(e);
                if (e.getKeyCode() == KeyEvent.VK_DELETE) {
                    TreePath[] paths = tree1.getSelectionPaths();
                    if (paths == null) {
                        return;
                    }
                    for (TreePath path : paths) {
                        AbstractTreeNode t = (AbstractTreeNode) path.getLastPathComponent();
                        if (t == Editor.INSTANCE.getModelTree().getRoot()) {
                            continue;
                        }
                        if (t instanceof Cube) {
                            GuiControllerKt.deleteCube((Cube) t);
                        } else if (t instanceof Group) {
                            GuiControllerKt.deleteGroup((Group) t);
                        }
                    }
                    tree1.getSelectionModel().clearSelection();
                }
            }
        });
        tree1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                TreePath[] paths = tree1.getSelectionPaths();
                if (paths == null || paths.length != 1) {
                    return;
                }
                AbstractTreeNode comp = (AbstractTreeNode) paths[0].getLastPathComponent();
                if (comp instanceof Group) {
                    Editor.INSTANCE.getModelTree().setSelectedGroup((Group) comp);
                }
                Editor.INSTANCE.getModelTree().selectPart(comp);
                update(Editor.INSTANCE.getModelTree());
            }
        });
    }

    public void select(AbstractTreeNode node) {
        if (node == null) {
            tree1.clearSelection();
        } else {
            tree1.clearSelection();
            LinkedList<Object> list = new LinkedList<>();
            list.add(node);
            AbstractTreeNode parent = node.getParent();
            while (parent != null) {
                list.addFirst(parent);
                parent = parent.getParent();
            }
            tree1.addSelectionPath(new TreePath(list.stream().toArray()));
        }
    }

    private void createUIComponents() {
        tree1 = new JTree(Editor.INSTANCE.getModelTree().getTreeWrapper());
        tree1.setEditable(false);

        DefaultTreeCellRenderer render = new DefaultTreeCellRenderer();

        try {
            IResource res = Editor.INSTANCE.getResourceManager().getResource(new ResourceLocation(Editor.PROJECT_NAME.toLowerCase(), "cube.png"));
            ImageIcon icon = new ImageIcon(ImageIO.read(res.getInputStream()));
            render.setLeafIcon(icon);
        } catch (IOException e) {
            e.printStackTrace(Log.INSTANCE.getWriter());
        }
        try {
            IResource res = Editor.INSTANCE.getResourceManager().getResource(new ResourceLocation(Editor.PROJECT_NAME.toLowerCase(), "group.png"));
            ImageIcon icon = new ImageIcon(ImageIO.read(res.getInputStream()));
            render.setOpenIcon(icon);
            render.setClosedIcon(icon);
        } catch (IOException e) {
            e.printStackTrace(Log.INSTANCE.getWriter());
        }
        tree1.setCellRenderer(render);
    }

    public void update(ModelTree tree) {
        AbstractTreeNode node = tree.getSelectedGroup();
        String path = "";
        while (node != null) {
            path = node.getName() + "/" + path;
            node = node.getParent();
        }
        label.setText(path);
    }

    public void reset() {
        tree1.treeDidChange();
    }
}
