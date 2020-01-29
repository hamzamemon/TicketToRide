import javax.swing.ImageIcon;
import java.io.File;

/**
 * Utilizes file paths to access images for our trains and buttons
 *
 * @author Josh Dratler
 * @author Hamza Memon
 * @author Patrick Milano
 * @author Jhoan Esteban Osorno
 * @author Brian Smith
 * @version (1.0)
 */
public class ResourceLoader {
    
    /**
     * Loads an image
     *
     * @param path the name of the path we want to access
     *
     * @return the image that we retrieved
     */
    public static ImageIcon loadImage(String path) {
        return new ImageIcon("res/images/" + path);
    }
    
    /**
     * Loads a file
     *
     * @param path the rest of the path we want to access
     *
     * @return the file that we retrieved
     */
    public static File loadFile(String path) {
        return new File("res/" + path);
    }
}
