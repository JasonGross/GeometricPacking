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
    public State state = State.DEFAULT;

    public Circle(int x, int y, int radius) {
        this.x = x;
        this.y = y;
        this.radius = radius;
    }

    public Circle(Point2D center, int radius) {
        this((int) center.getX(), (int) center.getY(), radius);
    }

    /**
     * The state of a circle
     */
    public static enum State {

        DEFAULT, ACTIVE, INACTIVE, CONSIDERING
    }

}
