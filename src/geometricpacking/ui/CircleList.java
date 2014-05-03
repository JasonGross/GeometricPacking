/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package geometricpacking.ui;

import geometricpacking.Circle;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.Point2D;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.logging.Logger;


public class CircleList {
    private static final Logger LOG = Logger.getLogger(CircleList.class.getName());
    protected final geometricpacking.CircleList circles;
    private final PropertyChangeSupport props = new PropertyChangeSupport(this);
    private final java.awt.Component repainter;

    public CircleList(java.awt.Component myRepainter) {
        this.circles = new geometricpacking.CircleList(10);
        repainter = myRepainter;
    }

    public void addCircle(final Point2D center) {
        circles.addCircle(center);
        repainter.repaint();
    }

    public void addCircle(final int x, final int y) {
        circles.addCircle(x, y);
        repainter.repaint();
    }

    public Circle removeCircleContainingPoint(final Point2D p) {
        final Circle ret = circles.removeCircleContainingPoint(p);
        repainter.repaint();
        return ret;
    }

    public Circle removeCircleContainingPoint(final int x, final int y) {
        final Circle ret = circles.removeCircleContainingPoint(x, y);
        repainter.repaint();
        return ret;
    }

    public void clear() {
        circles.clear();
        repainter.repaint();
    }

    public void setRadius(int newRadius) {
        int oldRadius = circles.getRadius();
        circles.setRadius(newRadius);
        props.firePropertyChange("radius", oldRadius, circles.getRadius());
        repainter.repaint();
    }

    public int getRadius() {
        return circles.getRadius();
    }

    public void resetState() {
        circles.resetState();
        repainter.repaint();
    }

    public void addPropertyChangeListener(PropertyChangeListener l) {
        props.addPropertyChangeListener(l);
    }

    public void addPropertyChangeListener(String propName, PropertyChangeListener l) {
        props.addPropertyChangeListener(propName, l);
    }

    public void removePropertyChangeListener(PropertyChangeListener l) {
        props.removePropertyChangeListener(l);
    }

    public void removePropertyChangeListener(String propName, PropertyChangeListener l) {
        props.removePropertyChangeListener(propName, l);
    }

    protected void paintComponent(Graphics g) {
        for (Circle c : circles) {
            switch (c.state) {
                case DEFAULT:
                    g.setColor(new Color(0, 0, 1, 0.75f));
                    break;
                case ACTIVE:
                    g.setColor(new Color(0, 0, 1, 1.0f));
                    break;
                case CONSIDERING:
                    g.setColor(new Color(0, 0, 1, 0.5f));
                    break;
                case INACTIVE:
                    g.setColor(new Color(0, 0, 1, 0.2f));
                    break;
            }
            g.fillOval(c.x - c.radius, c.y - c.radius, 2 * c.radius, 2 * c.radius);
        }
    }
}
