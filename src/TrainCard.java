import javax.swing.ImageIcon;
import java.awt.Color;

/**
 * Enum to store all of the train cards. Each train card has a color and an image associated with it.
 *
 * @author Josh Dratler
 * @author Hamza Memon
 * @author Patrick Milano
 * @author Jhoan Esteban Osorno
 * @author Brian Smith
 * @version (1.0)
 */
public enum TrainCard {
    
    // Magenta represents the rainbow (locomotive)
    LOCOMOTIVE(Color.MAGENTA),
    BLACK_TRAIN(Color.BLACK),
    BLUE_TRAIN(Color.BLUE),
    GREEN_TRAIN(Color.GREEN),
    ORANGE_TRAIN(Color.ORANGE),
    PINK_TRAIN(Color.PINK),
    RED_TRAIN(Color.RED),
    WHITE_TRAIN(Color.WHITE),
    YELLOW_TRAIN(Color.YELLOW);
    
    private ImageIcon icon;
    private Color color;
    
    /**
     * Sets the color and image of the TrainCard
     *
     * @param color the color of the train card.
     */
    TrainCard(Color color) {
        this.color = color;
        
        String s = name().toLowerCase();
        icon = ResourceLoader.loadImage("train cards/" + s + ".jpg");
    }
    
    /**
     * Method the get the Image of the train card
     *
     * @return image of the train card.
     */
    public ImageIcon getIcon() {
        return icon;
    }
    
    /**
     * Method to get the Color of the train card.
     *
     * @return color of the train card.
     */
    public Color getColor() {
        return color;
    }
}
