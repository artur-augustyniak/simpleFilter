/*
 *      FilterAdjustPanel.java
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

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import pl.aaugustyniak.simplefilter.model.ImageModifierInterface;

/**
 *
 * @author Artur Augustyniak
 */
public class FilterAdjustPanel extends JDialog {

    public FilterAdjustPanel(JFrame mf, ImageModifierInterface md, boolean modal) {
        super(mf, md.getName(), modal);

        this.setSize(350, md.getParams().entrySet().size() * 60);
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        for (Map.Entry<String, Object> entry : md.getParams().entrySet()) {
            panel.add(new JLabel(entry.getKey()));
            panel.add((Component) entry.getValue());
        }
        JButton ok = new JButton("OK");
        ok.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                FilterAdjustPanel.this.dispose();
            }
        });
        panel.add(ok);
        this.getContentPane().add(panel);
        this.setVisible(true);
    }
}
