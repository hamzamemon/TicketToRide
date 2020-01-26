import javax.swing.ImageIcon;

/**
 * Enum to store all the technologies in the game that stores the quantity of the card and the cost.
 *
 * @author Hamza Memon
 * @author Jhoan Esteban Osorno
 * @author Brian Smith
 * @author Patrick Milano
 * @author Josh Dratler
 * @version 1.0
 */
public enum Technology {
    
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
    
    private ImageIcon icon;
    private int cost;
    private int quantity;
    
    /**
     * Creates a technology card with the proper quantity and cost of the card.
     *
     * @param quantity - the availability of this card
     * @param cost     - the cost that the player must pay to obtain the card
     */
    Technology(int cost, int quantity) {
        this.cost = cost;
        this.quantity = quantity;
        
        String s = name().toLowerCase();
        icon = new ImageIcon(ResourceLoader.loadImage("technologies/" + s + ".jpg").getImage().getScaledInstance(230, 152, 0));
    }
    
    /**
     * Retrieves the cost of the card.
     *
     * @return the cost of the card
     */
    public int getCost() {
        return cost;
    }
    
    /**
     * Retrieves the quantity of the card.
     *
     * @return the availability of the card
     */
    public int getQuantity() {
        return quantity;
    }
    
    /**
     * Retrieve the image of the card.
     *
     * @return the image of the card
     */
    public ImageIcon getIcon() {
        return icon;
    }
    
    /**
     * Reduces the quantity of the card by 1
     */
    public void subtractOne() {
        quantity--;
    }
    
    /**
     * Increment the quantity of the card by 1
     */
    public void addOne() {
        quantity++;
    }
}
