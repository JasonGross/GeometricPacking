/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package geometricpacking;

import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.PriorityQueue;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Packing {
    private final Grid grid;
    private boolean hasStarted = false;
    private Iterator<Rectangle2D.Double> cells = null;

    public Packing(Grid grid) {
        this.grid = grid;
    }

    public void start(Iterable<Circle> circles) {
        Rectangle2D boundingBox = new Rectangle2D.Double();
        for (Circle c : circles) {
            c.state = Circle.State.INACTIVE;
            boundingBox.add(new Rectangle2D.Double(c.x - c.radius, c.y - c.radius,
                    2 * c.radius, 2 * c.radius));
        }
        cells = grid.iterator(boundingBox);
    }

    public void stop() {
        cells = null;
    }

    public boolean hasNext() {
        return cells != null && cells.hasNext();
    }

    public void finish(Iterable<Circle> circles) {
        for (Circle c : circles) {
            if (c.state != Circle.State.ACTIVE) {
                c.state = Circle.State.INACTIVE;
            }
        }
        cells = null;
    }

    public ArrayList<Circle> getNext(Iterable<Circle> circles) {
        ArrayList<Circle> ret = new ArrayList<>(10);
        Rectangle2D box = cells.next();
        for (Circle c : circles) {
            if (box.contains(c.x - c.radius, c.y - c.radius,
                    2 * c.radius, 2 * c.radius)) {
                c.state = Circle.State.CONSIDERING;
                ret.add(c);
            }
        }
        return ret;
    }

    public static ArrayList<ArrayList<Circle>> makePossiblePackings(Iterable<Circle> circles) {
        ArrayList<ArrayList<Circle>> ret = new ArrayList<>();
        ret.add(new ArrayList<Circle>());
        for (Circle c : circles) {
            ArrayList<ArrayList<Circle>> new_ret = new ArrayList<>(ret.size() * 2);
            for (ArrayList<Circle> old_list : ret) {
                new_ret.add(old_list);
                // see if we can add c to the list, and, if we can, add it
                boolean can_add_c = true;
                for (Circle existing_c : old_list) {
                    can_add_c = can_add_c && !existing_c.overlaps(c);
                }
                if (can_add_c) {
                    ArrayList<Circle> new_list = new ArrayList<Circle>(old_list);
                    new_list.add(c);
                    new_ret.add(new_list);
                }
            }
            ret = new_ret;
        }
        return ret;
    }

    public ArrayList<Circle> considerNext(Iterable<Circle> circles) {
        class comp<T> implements Comparator<ArrayList<T>> {

            @Override
            public int compare(ArrayList<T> o1, ArrayList<T> o2) {
                // reverse the sizes to get the longest list
                return Integer.compare(o2.size(), o1.size());
            }

        }
        final ArrayList<Circle> considering = new ArrayList<>();
        for (Circle c : circles) {
            if (c.state == Circle.State.CONSIDERING) {
                considering.add(c);
            }
        }
        LOG.log(Level.FINE, "considering = {0}", considering);
        if (considering.isEmpty()) {
            return new ArrayList<Circle>();
        }
        LOG.log(Level.FINE, "new PriorityQueue({0}, new comp())", considering.size());
        final PriorityQueue<ArrayList<Circle>> packings = new PriorityQueue<>(considering.size(), new comp<Circle>());
        packings.addAll(makePossiblePackings(considering));
        for (Circle c : considering) {
            c.state = Circle.State.INACTIVE;
        }
        LOG.log(Level.FINE, "best packing: {0}", packings.peek());
        for (Circle c : packings.peek()) {
            c.state = Circle.State.ACTIVE;
        }
        return packings.poll();
    }
    private static final Logger LOG = Logger.getLogger(Packing.class.getName());
}
