/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package geometricpacking;

import java.awt.Rectangle;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.Iterator;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Jason
 */
public class Grid implements Iterable<Rectangle.Double> {

    private static final Logger LOG = Logger.getLogger(geometricpacking.Grid.class.getName());
    private final Random rand;
    private int width = 30;
    private Point2D offset = new Point2D.Double();
    private Rectangle2D boundingBox = new Rectangle.Double();

    public Grid() {
        rand = new Random();
        LOG.finest("new Grid()");
    }

    public Grid(final long seed) {
        rand = new Random(seed);
        LOG.log(Level.FINEST, "new Grid({0})", seed);
    }

    public void resetOffset() {
        resetOffset(new Point2D.Double(rand.nextInt(width), rand.nextInt(width)));
    }

    public void resetOffset(final Point2D newOffset) {
        LOG.log(Level.FINEST, "Old Offset: {0}", offset);
        offset = new Point2D.Double(newOffset.getX(), newOffset.getY());
        LOG.log(Level.FINE, "resetOffset({0})", newOffset);
    }

    public void setWidth(final int newWidth) {
        LOG.log(Level.FINEST, "Old Width: {0}", width);
        width = newWidth;
        LOG.log(Level.FINER, "setWidth({0})", newWidth);
    }

    public double getWidth() {
        return width;
    }

    public Point2D getOffset() {
        return offset;
    }

    public Rectangle2D getBoundingBox() {
        return boundingBox;
    }

    public void setBoundingBox(final Rectangle newBoundingBox) {
        LOG.log(Level.FINEST, "Old Bounding Box: {0}", boundingBox);
        boundingBox = new Rectangle2D.Double(newBoundingBox.getX(), newBoundingBox.getY(),
                newBoundingBox.getWidth(), newBoundingBox.getHeight());
        LOG.log(Level.FINER, "setBoundingBox({0})", newBoundingBox);
    }

    @Override
    public Iterator<Rectangle2D.Double> iterator() {
        return iterator(boundingBox);
    }

    public Iterator<Rectangle2D.Double> iterator(final Rectangle2D boundingBox) {
        class it implements Iterator<Rectangle2D.Double> {

            private Point2D.Double curCorner = new Point2D.Double(boundingBox.getMinX(), boundingBox.getMinY());

            it() {
            }

            @Override
            public boolean hasNext() {
                return boundingBox.contains(curCorner)
                        || boundingBox.contains(curCorner.getX() - offset.getX(), curCorner.getY())
                        || boundingBox.contains(curCorner.getX(), curCorner.getY() - offset.getY())
                        || boundingBox.contains(curCorner.getX() - offset.getX(), curCorner.getY() - offset.getY());
            }

            @Override
            public Rectangle2D.Double next() {
                final Rectangle2D.Double ret = new Rectangle2D.Double(curCorner.x - offset.getX(),
                        curCorner.y - offset.getY(),
                        width,
                        width);
                LOG.log(Level.FINER, "Grid.next() -> {0}", ret);
                curCorner = new Point2D.Double(curCorner.x + width, curCorner.y);
                if (!hasNext()) { // Assume we went off the side
                    curCorner = new Point2D.Double(boundingBox.getMinX(), curCorner.y + width);
                }
                return ret;
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

        }
        return new it();
    }
}
