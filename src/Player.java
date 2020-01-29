import javax.swing.ImageIcon;
import java.awt.Color;
import java.util.ArrayList;

/**
 * Constructs a player, which has a hand of train cards, destination cards, the technologies, the number of trains
 * and the number of points.
 *
 * @author Josh Dratler
 * @author Hamza Memon
 * @author Patrick Milano
 * @author Jhoan Esteban Osorno
 * @author Brian Smith
 * @version (1.0)
 */
public class Player {
    
    private int number;
    private ImageIcon icon;
    private int numTrains;
    private int points;
    private Color color;
    
    // Train cards owned by the player
    private ArrayList<TrainCard> trainCards = new ArrayList<>();
    
    // Destination cards owned by the player
    private ArrayList<DestinationCard> destinationCards = new ArrayList<>();
    // Technologies owned by the player
    private ArrayList<Technology> technologies = new ArrayList<>();
    
    // Routes owned by the player
    private ArrayList<Route> routesOwned = new ArrayList<>();
    
    // Destination cards completed by the player
    private ArrayList<DestinationCard> completedDestinations = new ArrayList<>();
    
    /**
     * This class will construct the player.
     *
     * @param number - the player number (player1, player2, etc.)
     * @param icon   - the player icon that the user chose
     * @param color  - desired color of the user
     */
    public Player(int number, ImageIcon icon, Color color) {
        this.number = number;
        this.icon = icon;
        this.color = color;
        points = 0;
        numTrains = 35;
    }
    
    /**
     * Amount of points that the player is gaining.
     *
     * @param amount the points getting added
     */
    public void addPoints(int amount) {
        points += amount;
    }
    
    /**
     * Number of trains that the player is using to set down on a path.
     *
     * @param amount the number of trains being used
     */
    public void subtractTrains(int amount) {
        numTrains -= amount;
    }
    
    /**
     * The player's number whether they are player 1, 2, 3, or 4.
     *
     * @return the number the player is
     */
    public int getNumber() {
        return number;
    }
    
    /**
     * Retrieves the player's points
     *
     * @return the player's points
     */
    public int getPoints() {
        return points;
    }
    
    /**
     * Retrieves the player's icon that the user chose at the beginning of the game.
     *
     * @return the player's icon
     */
    public ImageIcon getIcon() {
        return icon;
    }
    
    /**
     * Retrieves the color of the player
     *
     * @return color of the player
     */
    public Color getColor() {
        return color;
    }
    
    /**
     * Retrieves the number of trains the player has.
     *
     * @return the number of trains
     */
    public int getNumTrains() {
        return numTrains;
    }
    
    /**
     * Adds a Train Card to the player's hand.
     *
     * @param card the card you want to add to your hand
     */
    public void addTrainCard(TrainCard card) {
        trainCards.add(card);
    }
    
    /**
     * Adds a destination card to the player's hand.
     *
     * @param card the destination you want to add to your list
     */
    public void addDestinationCard(DestinationCard card) {
        destinationCards.add(card);
    }
    
    /**
     * Adds a technology card to the player's hand.
     *
     * @param card the technology you want to add to your list
     */
    public void addTechnologyCard(Technology card) {
        technologies.add(card);
    }
    
    /**
     * Retrieves the player's technology cards.
     *
     * @return list of technology cards
     */
    public ArrayList<Technology> getTechnologies() {
        return technologies;
    }
    
    /**
     * Retrieves the player's train cards.
     *
     * @return list of train cards
     */
    public ArrayList<TrainCard> getTrainCards() {
        return trainCards;
    }
    
    /**
     * Retrieves the player's destination cards.
     *
     * @return list of destination cards
     */
    public ArrayList<DestinationCard> getDestinationCards() {
        return destinationCards;
    }
    
    /**
     * Adds routes to the player's routes owned
     *
     * @param route route to be added
     */
    public void addRoute(Route route) {
        routesOwned.add(route);
    }
    
    /**
     * Retrieves the routes owned by the player
     *
     * @return list of routes
     */
    public ArrayList<Route> getRoutesOwned() {
        return routesOwned;
    }
    
    /**
     * Retrieves the destination cards completed by the player
     *
     * @return list of completed destination cards
     */
    public ArrayList<DestinationCard> getCompletedDestinations() {
        return completedDestinations;
    }
}
