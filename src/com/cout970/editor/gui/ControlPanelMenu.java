package com.cout970.editor.gui;

import javax.swing.*;

/**
 * Created by cout970 on 27/06/2016.
 */
public class ControlPanelMenu {

    private JButton button1;
    private JButton button2;
    private JButton button3;
    private JButton button4;
    private JButton button5;
    private JButton button6;
    private JButton button7;
    private JButton button8;
    private JButton button9;
    public JPanel root;

    public ControlPanelMenu() {
        button1.addActionListener(e -> {
            GuiControllerKt.newModel();
        });
        button2.addActionListener(e -> {
            GuiControllerKt.importModel();
        });
        button3.addActionListener(e -> {
            GuiControllerKt.saveModel();
        });
        button4.addActionListener(e -> {
            GuiControllerKt.saveModelAs();
        });
        button5.addActionListener(e -> {
            GuiControllerKt.openOptions();
        });
        button6.addActionListener(e -> {
            GuiControllerKt.toggleEditorWindow();
        });
        button7.addActionListener(e -> {
            GuiControllerKt.toggleModelTreeWindow();
        });
        button8.addActionListener(e -> {
            GuiControllerKt.toggleTextureWindow();
        });
        button9.addActionListener(e -> {
            GuiControllerKt.toggleGridsWindow();
        });
    }
}
