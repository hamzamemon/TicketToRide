import javax.swing.*;

/**
 * This Enum class will create all the technologies in the game that
 * stores the quanity of the card and the cost of the card.
 *
 * @author Hamza Memon
 * @author Jhoan Esteban Osorno
 * @author Brian Smith
 * @author Patrick Milano
 * @author Josh Dratler
 * @version 1.0
 */
public enum Technology {
    // List of technologies
    BOILER_LAGGING(2, 4),
    BOOSTER(2, 4),
    DIESEL_POWER(3, 1),
    DOUBLE_HEADING(4, 4),
    EQUALISING_BEAM(2, 1),
    IRELAND_FRANCE_CONCESSION(1, 4),
    MECHANICAL_STOKER(1, 4),
    PROPELLERS(2, 4),
    RIGHT_OF_WAY(4, 1),
    RISKY_CONTRACTS(2, 1),
    SCOTLAND_CONCESSION(1, 4),
    STEAM_TURBINE(2, 4),
    SUPERHEATED_STEAM_BOILER(2, 4),
    THERMO_COMPRESSOR(1, 1),
    WALES_CONCESSION(1, 4),
    WATER_TENDERS(2, 2);

    private final ImageIcon icon;
    private  int quantity;
    private final int cost;

    /**
     * This constructor will create the technology card with the proper
     * quanity and cost of the card.
     *
     * @param quantity - the availability of this card
     * @param cost     - the cost that the player must pay to obtain the card
     */
    Technology(int cost, int quantity){
        String s = name().toLowerCase();
        icon = new ImageIcon(ResourceLoader.loadImage("technologies/" + s + 
                ".jpg").getImage().getScaledInstance(230, 152, 0));

        this.cost = cost;
        this.quantity = quantity;
    }

    /**
     * This method will retrieve the cost of the card.
     *
     * @return the cost of the technology card
     */
    public int getCost(){
        return cost;
    }

    /**
     * This method will retrieve the quantity of the card.
     *
     * @return the availibility of this card
     */
    public int getQuantity(){
        return quantity;
    }

    /**
     * This method will get the image of the card.
     *
     * @return the image of this card
     */
    public ImageIcon getIcon(){
        return icon;
    }

    /**
     * This method will reduce the quantity of a card by 1
     *
     */
    public void subtractOne(){
        quantity--;
    }

    /**
     * This method will increment the quantity of a card by 1
     */
    public void addOne()
    {
        quantity++;
    }
}