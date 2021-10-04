/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AppointmentAutomation;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Stroke;
import javax.swing.JButton;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;

/**
 *
 * @author Bilal
 */
public class TriangleButton extends JButton {

    public TriangleButton() {
        super();

        Dimension size = new Dimension(50, 50);
        this.setSize(size);
        this.setBorder(new BevelBorder(BevelBorder.RAISED));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        if (getModel().isArmed()) {
            this.setBorder(new BevelBorder(BevelBorder.LOWERED));
        } else {
            this.setBorder(new BevelBorder(BevelBorder.RAISED));
        }
        g2d.setColor(new Color(204, 0, 0));
        int[] pointsx2 = {5, 25, 45};
        int[] pointsy2 = {45, 5, 45};
        g2d.fillPolygon(pointsx2, pointsy2, 3);

        g2d.setColor(new Color(0, 51, 153));
        int[] pointsx = {10, 25, 40};
        int[] pointsy = {42, 12, 42};
        g2d.fillPolygon(pointsx, pointsy, 3);

        g2d.setColor(Color.WHITE);
        Font font = new Font("!", Font.BOLD, 22);
        g2d.setFont(font);
        g2d.drawString("!", 22, 38);
    }

}
