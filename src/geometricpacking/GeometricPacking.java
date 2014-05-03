/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package geometricpacking;

import java.util.logging.ConsoleHandler;
import java.util.logging.Handler;
import static java.util.logging.Level.FINE;
import java.util.logging.Logger;

/**
 *
 * @author Jason
 */
public class GeometricPacking {
    private static final Logger LOG = Logger.getLogger(GeometricPacking.class.getName());

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Logger root = Logger.getLogger("");
         root.setLevel(FINE);
        for (Handler handler : root.getHandlers()) {
            if (handler instanceof ConsoleHandler) {
                // java.util.logging.ConsoleHandler.level = ALL
                handler.setLevel(FINE);
            }
        }
        java.util.logging.Level[] levels = {
            java.util.logging.Level.OFF, java.util.logging.Level.SEVERE, java.util.logging.Level.WARNING, java.util.logging.Level.INFO,
            java.util.logging.Level.CONFIG, java.util.logging.Level.FINE, java.util.logging.Level.FINER, java.util.logging.Level.FINEST, java.util.logging.Level.ALL
        };

        // .level= ALL
        for (java.util.logging.Level level : levels) {
            LOG.setLevel(level);
            LOG.log(level, "Hello Logger");
        }
        geometricpacking.ui.Main.main(args);
    }

}
