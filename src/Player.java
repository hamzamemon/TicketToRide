import javax.swing.*;
import java.util.ArrayList;
import java.awt.Color;
////////////////////////////////////////////////////////////////////////////////
/**
 * This class will construct a player. The player will contain a hand of 
 * train cards, the player's destination cards, the technologies that 
 * the player purchased, the number of trains that the player has, 
 * and the number of points that the player has.
 *
 * @author Hamza Memon
 * @author Jhoan Esteban Osorno
 * @author Brian Smith
 * @author Patrick Milano
 * @author Josh Dratler
 * @version 1.0
 */
public class Player {
    private int number;
    //players image
    private ImageIcon icon;
    private int numTrains;
    private int points;
    //players color
    private Color color;

    //train cards owned by the player
    private ArrayList<TrainCard> trainCards = new ArrayList<>();
    //destination cards owned by the player
    private ArrayList<DestinationCard> destinationCards = new ArrayList<>();
    //technologies owned by the player
    private ArrayList<Technology> technologies = new ArrayList<>();
    //routes owned by the player
    private ArrayList<Route> routesOwned = new ArrayList<>();
    //destination cards owned by the player
    private ArrayList<DestinationCard> completedDestinations = 
        new ArrayList<>();

    /**
     * This class will construct the player.
     *
     * @param number - the player number (player1, player2, etc.)
     * @param icon   - the player icon that the user chose
     * @param color - desired color of the user
     */
    public Player(int number, ImageIcon icon, Color color){
        this.number = number;
        this.icon = icon;
        this.color = color;
        numTrains = 35;
    }

    /**
     * The amount of points that the player is gaining. 
     * 
     * @param amount - the points getting added
     */
    public void addPoints(int amount){
        points += amount;
    }

    /**
     * The number of trains that the player is using to set down on a path. 
     * 
     * @param amount the number of trains being used
     */
    public void subtractTrains(int amount){
        numTrains -= amount;
    }

    /**
     * The player's number whether they are player 1, 2, 3, or 4. 
     * 
     * @return the number the player is
     */
    public int getNumber(){
        return number;
    }

    /**
     * Retrieve the player's points
     *
     * @return the player's points
     */
    public int getPoints(){
        return points;
    }

    /**
     * Retrive the player's icon that the user chose at the beginning 
     * of the game.
     *
     * @return the player's icon
     */
    public ImageIcon getIcon(){
        return icon;
    }

    /**
     * Method to get the color of the player
     * 
     * @return color of the player
     */
    public Color getColor(){
        return color;
    }

    /**
     * Get the number of trains the player has.
     *
     * @return the number of trains
     */
    public int getNumTrains(){
        return numTrains;
    }

    /**
     * This method will add a traincard to the player's hand.
     *
     * @param card - the card you want to add to your hand
     */
    public void addTrainCard(TrainCard card){
        trainCards.add(card);
    }

    /**
     * This method will add a destination card to the player's hand.
     *
     * @param card - the destination you want to add to your list
     */
    public void addDestCard(DestinationCard card){
        destinationCards.add(card);
    }

    /**
     * This method will add a technology card to the player's hand.
     *
     * @param card - the technology you want to add to your list
     */
    public void addTechnologyCard(Technology card){
        technologies.add(card);
    }

    /**
     * This method will retrieve the player's technology cards.
     *
     * @return list of technology cards
     */
    public ArrayList<Technology> getTechnologies(){
        return technologies;
    }

    /**
     * This method will retrieve the player's train cards.
     *
     * @return list of train cards
     */
    public ArrayList<TrainCard> getTrainCards(){
        return trainCards;
    }

    /**
     * This method will retrieve the player's destination cards.
     *
     * @return list of destination cards
     */
    public ArrayList<DestinationCard> getDestinationCards(){
        return destinationCards;
    }

    /**
     * Method to add routes to the players routes owner arraylist
     * 
     * @param route route to be added 
     */
    public void addRoute(Route route){
        routesOwned.add(route);
    }

    /**
     * Method to get the routes owned by the player
     * 
     * @return arraylist of routes
     */
    public ArrayList<Route> getRoutesOwned(){
        return routesOwned;
    }

    /**
     * Method to get destination cards completed by the player
     * 
     * @return arraylist of completed destination cards
     */
    public ArrayList<DestinationCard> getCompletedDestinations(){
        return completedDestinations;
    }
}