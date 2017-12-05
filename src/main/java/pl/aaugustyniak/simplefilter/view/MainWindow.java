/*
 *      MainWindow.java
 *
 *      Copyright 2013 Artur Augustyniak <artur@aaugustyniak.pl>
 *
 *      This program is free software; you can redistribute it and/or modify
 *      it under the terms of the GNU General Public License as published by
 *      the Free Software Foundation; either version 2 of the License, or
 *      (at your option) any later version.
 *
 *      This program is distributed in the hope that it will be useful,
 *      but WITHOUT ANY WARRANTY; without even the implied warranty of
 *      MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *      GNU General Public License for more details.
 *
 *      You should have received a copy of the GNU General Public License
 *      along with this program; if not, write to the Free Software
 *      Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston,
 *      MA 02110-1301, USA.
 */
package pl.aaugustyniak.simplefilter.view;

import java.awt.event.ActionEvent;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import javax.imageio.ImageIO;
import javax.swing.*;

import org.simple.pluginspi.PluginManager;
import pl.aaugustyniak.simplefilter.model.ImagePair;
import pl.aaugustyniak.simplefilter.model.ImageModifierInterface;
import pl.aaugustyniak.simplefilter.utils.BoundedLinkedList;

/**
 * @author Artur Augustyniak
 */
public class MainWindow extends javax.swing.JFrame {

    private ImagePair displayBuffer;
    private PluginManager pluginManager;
    List<ImageModifierInterface> imageModifiers;
    LinkedList<BufferedImage> undoBuffer, redoBuffer;
    private javax.swing.JMenu editMenu;
    private javax.swing.JFileChooser fileChooser;
    private javax.swing.JMenu fileMenu;
    private javax.swing.JMenuBar menuBar;
    private javax.swing.JMenu modifiersMenu;
    private javax.swing.JMenuItem openItem;
    private pl.aaugustyniak.simplefilter.view.ImagePanel origPanel;
    private javax.swing.JMenuItem redoItem;
    private javax.swing.JMenuItem saveItem;
    private pl.aaugustyniak.simplefilter.view.ImagePanel transformPanel;
    private javax.swing.JMenuItem undoItem;

    private class imgFileFilter extends javax.swing.filechooser.FileFilter {

        @Override
        public boolean accept(File file) {
            return file.isDirectory() || file.getAbsolutePath().endsWith(".jpg");
        }

        @Override
        public String getDescription() {
            return "JPEG (*jpg)";
        }
    }

    /**
     * Creates new form MainWindow
     */
    public MainWindow() {
        undoBuffer = new BoundedLinkedList<>(10);
        redoBuffer = new BoundedLinkedList<>(10);
        loadPlugins();
        initComponents();
        fillModifiersMenu();
        setAccelerators();
        this.getRootPane().addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                redrawPanels();
            }
        });
    }

    @SuppressWarnings("unchecked")
    private void initComponents() {

        fileChooser = new javax.swing.JFileChooser();
        origPanel = new pl.aaugustyniak.simplefilter.view.ImagePanel();
        transformPanel = new pl.aaugustyniak.simplefilter.view.ImagePanel();
        menuBar = new javax.swing.JMenuBar();
        fileMenu = new javax.swing.JMenu();
        openItem = new javax.swing.JMenuItem();
        saveItem = new javax.swing.JMenuItem();
        editMenu = new javax.swing.JMenu();
        undoItem = new javax.swing.JMenuItem();
        redoItem = new javax.swing.JMenuItem();
        modifiersMenu = new javax.swing.JMenu();

        fileChooser.setDialogTitle("");
        fileChooser.setFileFilter(new imgFileFilter());

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("SimpleFilter");

        origPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Original"));

        javax.swing.GroupLayout origPanelLayout = new javax.swing.GroupLayout(origPanel);
        origPanel.setLayout(origPanelLayout);
        origPanelLayout.setHorizontalGroup(
                origPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 558, Short.MAX_VALUE)
        );
        origPanelLayout.setVerticalGroup(
                origPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 454, Short.MAX_VALUE)
        );

        transformPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Modified"));

        javax.swing.GroupLayout transformPanelLayout = new javax.swing.GroupLayout(transformPanel);
        transformPanel.setLayout(transformPanelLayout);
        transformPanelLayout.setHorizontalGroup(
                transformPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 558, Short.MAX_VALUE)
        );
        transformPanelLayout.setVerticalGroup(
                transformPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 454, Short.MAX_VALUE)
        );

        fileMenu.setText("File");

        openItem.setText("Open");
        openItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                openItemActionPerformed(evt);
            }
        });
        fileMenu.add(openItem);

        saveItem.setText("Save");
        saveItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveItemActionPerformed(evt);
            }
        });
        fileMenu.add(saveItem);

        menuBar.add(fileMenu);

        editMenu.setText("Edit");

        undoItem.setText("Undo");
        undoItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                undoItemActionPerformed(evt);
            }
        });
        editMenu.add(undoItem);

        redoItem.setText("Redo");
        redoItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                redoItemActionPerformed(evt);
            }
        });
        editMenu.add(redoItem);

        menuBar.add(editMenu);

        modifiersMenu.setText("Filter");
        menuBar.add(modifiersMenu);

        setJMenuBar(menuBar);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(origPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGap(50, 50, 50)
                                .addComponent(transformPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addContainerGap())
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(origPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(transformPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(30, 30, 30))
        );

        pack();
    }

    private void setAccelerators() {
        openItem.setAccelerator(KeyStroke.getKeyStroke(
                KeyEvent.VK_O, ActionEvent.CTRL_MASK));
        saveItem.setAccelerator(KeyStroke.getKeyStroke(
                KeyEvent.VK_S, ActionEvent.CTRL_MASK));
        undoItem.setAccelerator(KeyStroke.getKeyStroke(
                KeyEvent.VK_Z, ActionEvent.CTRL_MASK));
        redoItem.setAccelerator(KeyStroke.getKeyStroke(
                KeyEvent.VK_Y, ActionEvent.CTRL_MASK));
    }

    private void loadPlugins() {
        this.pluginManager = PluginManager.getPluginManager();
        this.imageModifiers = this.pluginManager.findPlugins(ImageModifierInterface.class);
    }

    private void fillModifiersMenu() {
        JMenuItem itemHandle;
        for (final ImageModifierInterface mod : this.imageModifiers) {
            itemHandle = new javax.swing.JMenuItem();
            itemHandle.setText(mod.getName());
            itemHandle.addActionListener(new java.awt.event.ActionListener() {
                @Override
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    applyModifier(mod);
                }
            });
            modifiersMenu.add(itemHandle);
        }
    }

    private void applyModifier(ImageModifierInterface md) {
        Thread worker = new Thread(() -> {
            try {
                this.undoBuffer.addFirst(ImagePair.duplicate(this.displayBuffer.getModified()));
                this.redoBuffer.clear();
                if (md.hasDialog()) {
                    new FilterAdjustPanel(this, md, true);
                }
                md.modifier(this.displayBuffer.getModified());
                this.redrawPanels();
            } catch (Exception ex) {
                Thread.currentThread().interrupt();
                popup("Can't apply modifier", "Achtung!", JOptionPane.INFORMATION_MESSAGE);
            }
        });
        worker.start();
    }

    private void redrawPanels() {
        SwingUtilities.invokeLater(() -> {
            if (!(displayBuffer == null)) {
                origPanel.loadImage(displayBuffer.getOrig());
                origPanel.paintComponent(origPanel.getGraphics());

                transformPanel.loadImage(displayBuffer.getModified());
                transformPanel.paintComponent(transformPanel.getGraphics());
            }
//            repaint();
        });
    }

    private void popup(String msg, String title, int type) {
        JOptionPane.showMessageDialog(this, msg, title, type);
    }

    private void openItemActionPerformed(java.awt.event.ActionEvent evt) {

        fileChooser.setDialogTitle("Open");
        int returnVal = fileChooser.showOpenDialog(this);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            Thread worker = new Thread(() -> {
                File file = fileChooser.getSelectedFile();
                displayBuffer = null;
                try {
                    displayBuffer = new ImagePair(file.getAbsolutePath());
                    redoBuffer.clear();
                    undoBuffer.clear();
                    redrawPanels();
                } catch (IOException e) {
                    Thread.currentThread().interrupt();
                    popup("Can't open file", "Achtung!", JOptionPane.ERROR_MESSAGE);
                }
            });
            worker.start();
        }
    }

    private void undoItemActionPerformed(java.awt.event.ActionEvent evt) {
        if (!this.undoBuffer.isEmpty()) {
            Thread worker = new Thread(() -> {
                this.redoBuffer.addFirst(ImagePair.duplicate(this.displayBuffer.getModified()));
                this.displayBuffer.setModified(this.undoBuffer.remove());
                this.redrawPanels();
            });
            worker.start();
        }
    }

    private void redoItemActionPerformed(java.awt.event.ActionEvent evt) {
        if (!this.redoBuffer.isEmpty()) {
            Thread worker = new Thread(() -> {
                this.undoBuffer.addFirst(ImagePair.duplicate(this.displayBuffer.getModified()));
                this.displayBuffer.setModified(this.redoBuffer.remove());
                this.redrawPanels();
            });
            worker.start();
        }
    }

    private void saveItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveItemActionPerformed
        if (!(this.displayBuffer == null)) {
            fileChooser.setDialogTitle("Save");
            int returnVal = fileChooser.showSaveDialog(this);
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                Thread worker = new Thread(() -> {
                    File outputfile = new File(fileChooser.getSelectedFile().getAbsolutePath());
                    try {
                        ImageIO.write(this.displayBuffer.getModified(), "JPG", outputfile);
                    } catch (IOException ex) {
                        Thread.currentThread().interrupt();
                        popup("Can't save file", "Achtung!", JOptionPane.INFORMATION_MESSAGE);
                    }
                });
                worker.start();
            }
        }
    }

}
