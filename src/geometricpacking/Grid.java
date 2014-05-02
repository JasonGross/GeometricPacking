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
    private double width = 0;
    private double offset = 0;
    private Rectangle.Double boundingBox = new Rectangle.Double();

    public Grid() {
        rand = new Random();
        LOG.finest("new Grid()");
    }

    public Grid(final long seed) {
        rand = new Random(seed);
        LOG.log(Level.FINEST, "new Grid({0})", seed);
    }

    public void resetOffset() {
        resetOffset(rand.nextFloat() * width);
    }

    public void resetOffset(final double newOffset) {
        LOG.log(Level.FINEST, "Old Offset: {0}", offset);
        offset = newOffset;
        LOG.log(Level.FINE, "resetOffset({0})", newOffset);
    }

    public void setWidth(final double newWidth) {
        LOG.log(Level.FINEST, "Old Width: {0}", width);
        width = newWidth;
        LOG.log(Level.FINER, "setWidth({0})", newWidth);
    }

    public double getWidth() {
        return width;
    }

    public double getOffset() {
        return offset;
    }

    public Rectangle2D.Double getBoundingBox() {
        return boundingBox;
    }

    public void setBoundingBox(final Rectangle newBoundingBox) {
        LOG.log(Level.FINEST, "Old Bounding Box: {0}", boundingBox);
        boundingBox = newBoundingBox;
        LOG.log(Level.FINER, "setBoundingBox({0})", newBoundingBox);
    }

    @Override
    public Iterator<Rectangle2D.Double> iterator() {
        class it implements Iterator<Rectangle2D.Double> {

            private Point2D.Double curCorner = new Point2D.Double(boundingBox.getMinX(), boundingBox.getMinY());

            it() {
            }

            @Override
            public boolean hasNext() {
                return boundingBox.contains(curCorner);
            }

            @Override
            public Rectangle2D.Double next() {
                final Rectangle2D.Double ret = new Rectangle2D.Double(curCorner.x, curCorner.y, width, width);
                LOG.log(Level.FINER, "Grid.next() -> {0}", ret);
                curCorner = new Point2D.Double(curCorner.x + width, curCorner.y);
                if (!boundingBox.contains(curCorner)) { // Assume we went off the side
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
