/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package geometricpacking.ui;

import geometricpacking.Circle;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

/**
 *
 * @author Jason
 */
public class PackingRunner {
    private int delay = 0;
    private final PropertyChangeSupport props = new PropertyChangeSupport(this);
    private final java.awt.Component repainter;
    private final geometricpacking.Grid grid;
    private final geometricpacking.CircleList circles;
    private final geometricpacking.Packing runner;

    public PackingRunner(java.awt.Component repainter, geometricpacking.Grid grid, geometricpacking.CircleList circles) {
        this.circles = circles;
        this.repainter = repainter;
        this.grid = grid;
        this.runner = new geometricpacking.Packing(grid);
    }

    public void go() {
        for (Circle c : circles) {
            c.state = Circle.State.DEFAULT;
        }
        repainter.repaint();
        runner.start(circles);
        repainter.repaint();
        //Thread.sleep(delay);
        while (runner.hasNext()) {
            runner.getNext(circles);
            repainter.repaint();
            runner.considerNext(circles);
            repainter.repaint();
        }
        runner.finish(circles);
        repainter.repaint();
    }


    public void reset() {
        runner.stop();
    }

    public int getDelay() {
        return delay;
    }

    public void setDelay(int newDelay) {
        int oldDelay = delay;
        delay = newDelay;
        props.firePropertyChange("delay", oldDelay, delay);
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

}
