/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package geometricpacking.ui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.Rectangle2D;
import java.util.logging.Logger;
import javax.swing.JPanel;


public class Grid extends JPanel {

    private static final Logger LOG = Logger.getLogger(Grid.class.getName());
    private static final long serialVersionUID = 1L;
    public final geometricpacking.Grid grid = new geometricpacking.Grid();

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        grid.setBoundingBox(this.getBounds());
        this.getBounds()
    for (Rectangle2D rect : grid) {
            g.setColor(Color.black);
            g.drawRect(rect.getX(), rect.getY(), rect.getWidth(), rect.getHeight());
            g.drawLine(line.x1, line.y1, line.x2, line.y2);
        }
    }
}
