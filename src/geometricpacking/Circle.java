/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package geometricpacking;

import java.awt.geom.Point2D;

/**
 *
 * @author Jason
 */
public class Circle {

    public int x, y, radius;
    public State state;

    public Circle(int x, int y, int radius, State state) {
        this.x = x;
        this.y = y;
        this.radius = radius;
        this.state = state;
    }

    public Circle(int x, int y, int radius) {
        this(x, y, radius, State.DEFAULT);
    }

    public Circle(Point2D center, int radius) {
        this((int) center.getX(), (int) center.getY(), radius);
    }

    @Override
    public String toString() {
        return "Circle(" + x + ", " + y + ", " + radius + ", " + state + ")";
    }

    public Point2D getCenter() {
        return new Point2D.Double(x, y);
    }

    public boolean overlaps(final Circle other) {
        return overlaps(this, other);
    }

    public static boolean overlaps(final Circle c1, final Circle c2) {
        return c1.getCenter().distance(c2.getCenter()) <= c1.radius + c2.radius;
    }

    /**
     * The state of a circle
     */
    public static enum State {

        DEFAULT, ACTIVE, INACTIVE, CONSIDERING
    }

}
