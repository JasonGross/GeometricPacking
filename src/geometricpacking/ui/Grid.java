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
    protected final geometricpacking.Grid grid;
    protected final CircleList circles;
    protected final PackingRunner runner;
    //private PropertyChangeSupport props = new PropertyChangeSupport(this);

    public Grid() {
        grid = new geometricpacking.Grid();
        circles = new CircleList(this);
        runner = new PackingRunner(this, grid, circles.circles);
    }

    public void resetOffset() {
        grid.resetOffset();
        repaint();
    }

    public void setDiskRadius(final int newRadius) {
        int oldRadius = circles.getRadius();
        circles.setRadius(newRadius);
        super.firePropertyChange("diskRadius", oldRadius, circles.getRadius());
        runner.reset();
        circles.resetState();
        repaint();
    }

    public int getDiskRadius() {
        return circles.getRadius();
    }

    public void setDelay(final int newDelay) {
        int oldDelay = runner.getDelay();
        runner.setDelay(newDelay);
        super.firePropertyChange("delay", oldDelay, runner.getDelay());
    }

    public int getDelay() {
        return runner.getDelay();
    }

    public void setGridWidth(final int newWidth) {
        double oldWidth = grid.getWidth();
        grid.setWidth(newWidth);
        super.firePropertyChange("gridWidth", oldWidth, grid.getWidth());
        runner.reset();
        circles.resetState();
        repaint();
    }

    public int getGridWidth() {
        return (int) grid.getWidth();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        circles.paintComponent(g);
        grid.setBoundingBox(this.getBounds());
        for (Rectangle2D rect : grid) {
            g.setColor(Color.black);
            g.drawRect((int) rect.getX(), (int) rect.getY(), (int) rect.getWidth(), (int) rect.getHeight());
        }
    }

}
