/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package geometricpacking.ui;

import geometricpacking.Circle;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Jason
 */
public class PackingRunner {

    private int delay = 500;
    private final PropertyChangeSupport props = new PropertyChangeSupport(this);
    private final java.awt.Component repainter;
    private final geometricpacking.Grid grid;
    private final geometricpacking.CircleList circles;
    private final geometricpacking.Packing runner;
    private Thread algorithmThread = null;

    public PackingRunner(java.awt.Component repainter, geometricpacking.Grid grid, geometricpacking.CircleList circles) {
        this.circles = circles;
        this.repainter = repainter;
        this.grid = grid;
        this.runner = new geometricpacking.Packing(grid);
    }

    public void go() {
        class runAlgorithm extends Thread {

            @Override
            public void run() {
                try {
                    runner.start(circles);
                    repainter.repaint();
                    sleep(delay);
                    while (runner.hasNext()) {
                        ArrayList<Circle> considering = runner.getNext(circles);
                        repainter.repaint();
                        if (!considering.isEmpty()) {
                            sleep(delay);
                            runner.considerNext(circles);
                            repainter.repaint();
                            sleep(delay);
                        }
                    }
                    runner.finish(circles);
                    repainter.repaint();
                } catch (InterruptedException ex) {
                    Logger.getLogger(PackingRunner.class.getName()).log(Level.SEVERE, null, ex);
                    for (Circle c : circles) {
                        c.state = Circle.State.DEFAULT;
                        repainter.repaint();
                    }
                }
            }
        }
        if (algorithmThread != null) {
            if (algorithmThread.isAlive()) {
                algorithmThread.interrupt();
                try {
                    algorithmThread.join(delay);
                } catch (InterruptedException ex) {
                    Logger.getLogger(PackingRunner.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            algorithmThread = null;
        }
        algorithmThread = new runAlgorithm();
        algorithmThread.start();
    }


    public void reset() {
        if (algorithmThread != null && algorithmThread.isAlive()) {
            algorithmThread.interrupt();
        }
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
