import javax.swing.ImageIcon;
import java.io.File;
/**
 * This class will utilize file paths to access images for our trains and buttons
 * 
 * @author Hamza Memon
 * @author Jhoan Esteban Osorno
 * @author Brian Smith
 * @author Patrick Milano
 * @author Josh Dratler
 * @version 1.0
 */
public class ResourceLoader {
    /**
     * Method to shortcut loading an image
     * 
     * @param path the name of the path we want to access
     * 
     * @return the image that we retrieved
     */
    public static ImageIcon loadImage(String path){
        return new ImageIcon("res/images/" + path);
    }

    /**
     * Method to shortcut loading files 
     * 
     * @param the rest of the path we want to access
     * 
     * @return the file that we retrieved
     */
    public static File loadFile(String path){
        return new File("res/" + path);
    }
}