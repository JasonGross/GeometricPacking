/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package geometricpacking;

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
        /*Logger root = Logger.getLogger("");
         root.setLevel(FINE);
        for (Handler handler : root.getHandlers()) {
            if (handler instanceof ConsoleHandler) {
                // java.util.logging.ConsoleHandler.level = ALL
                handler.setLevel(FINE);
            }
        }
        Level[] levels = {
            OFF, SEVERE, WARNING, INFO,
            CONFIG, FINE, FINER, FINEST, ALL
        };

        // .level= ALL
        for (Level level : levels) {
            LOG.setLevel(level);
            LOG.log(level, "Hello Logger");
        }*/
        geometricpacking.ui.Main.main(args);
    }

}
