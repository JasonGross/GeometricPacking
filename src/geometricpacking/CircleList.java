/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package geometricpacking;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.PriorityQueue;
import java.util.logging.Logger;


public class CircleList implements Iterable<Circle> {
    private static final Logger LOG = Logger.getLogger(CircleList.class.getName());

    private int radius;
    private final ArrayList<Circle> circles;

    public CircleList(final int myRadius) {
        this.circles = new ArrayList<>(10);
        radius = myRadius;
    }

    public int getRadius() {
        return radius;
    }

    public void setRadius(int newRadius) {
        radius = newRadius;
    }

    public void addCircle(final Point2D center) {
        addCircle((int) center.getX(), (int) center.getY());
    }

    public void addCircle(int x, int y) {
        circles.add(new Circle(x, y, radius));
    }

    public void clear() {
        circles.clear();
    }

    public void resetState() {
        for (Circle c : circles) {
            c.state = Circle.State.DEFAULT;
        }
    }

    public Circle removeCircleContainingPoint(final int x, final int y) {
        return removeCircleContainingPoint(new Point2D.Double(x, y));
    }

    public Circle removeCircleContainingPoint(final Point2D p) {

        class comp implements Comparator<Circle> {

            @Override
            public int compare(final Circle o1, final Circle o2) {
                return Double.compare(p.distance(o1.x, o1.y), p.distance(o2.x, o2.y));
            }

        }
        PriorityQueue<Circle> closest = new PriorityQueue<>(circles.size(), new comp());
        closest.addAll(circles);
        final Circle pt = closest.peek();
        if (pt != null && p.distance(pt.x, pt.y) <= radius) {
            final boolean removed = circles.remove(pt);
            return removed ? pt : null;
        } else {
            return null;
        }
    }

    @Override
    public Iterator<Circle> iterator() {
        class it implements Iterator<Circle> {

            private final Iterator<Circle> center_it = circles.iterator();

            @Override
            public boolean hasNext() {
                return center_it.hasNext();
            }

            @Override
            public Circle next() {
                Circle c = center_it.next();
                c.radius = radius;
                return c;
            }

            @Override
            public void remove() {
                center_it.remove();
            }

        }
        return new it();
    }

}
