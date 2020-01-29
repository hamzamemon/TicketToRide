import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Creates the panel where the player is able to purchase routes.
 *
 * @author Josh Dratler
 * @author Hamza Memon
 * @author Patrick Milano
 * @author Jhoan Esteban Osorno
 * @author Brian Smith
 * @version (1.0)
 */
public class BuyRoutePanel extends JPanel {
    
    protected static JLabel trainBuilding = new JLabel();
    protected static int secondPath = 0;
    protected static TrainCard[] trainCardsArray = TrainCard.values();
    protected static TrainCard[] trainCardsArrayTemp = TrainCard.values();
    
    // JLabel array to hold train card images the user hasn't selected
    protected static JLabel[] cards = new JLabel[9];
    
    // JLabel array to hold train card images the user has selected
    protected static JLabel[] cards2 = new JLabel[9];
    
    // ArrayList to hold cards the user has chosen
    private ArrayList<TrainCard> chosenCards = new ArrayList<>();
    
    private static Player player = null;
    private static Route route = null;
    private final Color[] trainCardColors = {Color.MAGENTA, Color.BLACK, Color.BLUE, Color.GREEN, Color.ORANGE, Color.PINK, Color.RED, Color.WHITE, Color.YELLOW};
    
    // Confirm button
    private JLabel completePurchase = new JLabel(ResourceLoader.loadImage("confirmPurchaseButton.png"));
    private int[] chosenOccurrences = new int[9];//array to hold the cards the user has chosen.
    private JLabel back_arrow = new JLabel(ResourceLoader.loadImage("back_arrow.png"));
    
    private ImageIcon topBackground = ResourceLoader.loadImage("claiming_background1.jpg");
    private ImageIcon bottomBackground = ResourceLoader.loadImage("claiming_background2.jpg");
    
    /**
     * Constructs the buy route panel.
     *
     * Initializes background, draws train cards, two panels, back arrow and complete purchase button.
     */
    public BuyRoutePanel() {
        setPreferredSize(new Dimension(1280, 1024));
        setLayout(new BorderLayout());
        
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 20));
        panel.setPreferredSize(new Dimension(1280, 512));
        panel.setOpaque(false);
        
        back_arrow.addMouseListener(new Handler());
        completePurchase.addMouseListener(new Handler());
        
        // Draw all the train cards onto the screen
        Font font = new Font("TimesRoman", Font.BOLD, 18);
        for(int i = 0, length = trainCardsArray.length; i < length; i++) {
            TrainCard card = trainCardsArray[i];
            ImageIcon icon = card.getIcon();
            icon = new ImageIcon(icon.getImage().getScaledInstance(130, 73, 0));
            
            JLabel label = new JLabel(icon);
            label.setForeground(Color.YELLOW);
            label.setVerticalTextPosition(SwingConstants.TOP);
            label.setFont(font);
            label.setText("0");
            panel.add(label);
            
            cards2[i] = label;
            cards2[i].addMouseListener(new Handler());
        }
        
        add(panel, BorderLayout.SOUTH);
        
        JPanel panel2 = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 20));
        panel2.setPreferredSize(new Dimension(1280, 512));
        panel2.add(back_arrow, BorderLayout.LINE_START);
        
        // Draw all the selected train cards onto the screen
        for(int i = 0, length = trainCardsArrayTemp.length; i < length; i++) {
            TrainCard card = trainCardsArrayTemp[i];
            ImageIcon icon = card.getIcon();
            icon = new ImageIcon(icon.getImage().getScaledInstance(130, 73, 0));
            
            JLabel label = new JLabel(icon);
            label.setForeground(Color.YELLOW);
            label.setVerticalTextPosition(SwingConstants.TOP);
            label.setFont(font);
            panel2.add(label);
            
            cards[i] = label;
            cards[i].addMouseListener(new Handler());
        }
        
        panel2.add(trainBuilding, BorderLayout.EAST);
        panel2.add(completePurchase, BorderLayout.LINE_START);
        panel2.setOpaque(false);
        add(panel2, BorderLayout.NORTH);
    }
    
    /**
     * Sets the route
     *
     * @param rou the new route
     */
    public static void setRoute(Route rou) {
        route = rou;
    }
    
    /**
     * Sets the player for this panel
     *
     * @param player the new player to be set
     */
    public static void setPlayer(Player player) {
        BuyRoutePanel.player = player;
    }
    
    /**
     * Allows objects to be painted to the screen, like the length route and color they are looking to fill.
     *
     * @param g instance of Graphics.
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        topBackground.paintIcon(this, g, 0, 0);
        bottomBackground.paintIcon(this, g, 0, 511);
        
        int routeLength = route.getLength();
        int ferry = route.getFerry();
        trainBuilding.setForeground(Color.YELLOW);
        ArrayList<Technology> playerTechnologies = player.getTechnologies();
        if(playerTechnologies.contains(Technology.DIESEL_POWER)) {
            if(routeLength - ferry > 1) {
                routeLength--;
            }
        }
        
        // Tells the user what color trains and how many trains they are looking for
        Color color = route.getColor();
        if(color.equals(Color.RED)) {
            trainBuilding.setText("Train Color: Red " + "Route Length: " + routeLength);
        }
        else if(color.equals(Color.GREEN)) {
            trainBuilding.setText("Train Color: Green " + "Route Length: " + routeLength);
        }
        else if(color.equals(Color.PINK)) {
            trainBuilding.setText("Train Color: Pink " + "Route Length: " + routeLength);
        }
        else if(color.equals(Color.BLACK)) {
            trainBuilding.setText("Train Color: Black " + "Route Length: " + routeLength);
        }
        else if(color.equals(Color.ORANGE)) {
            trainBuilding.setText("Train Color: Orange " + "Route Length: " + routeLength);
        }
        else if(color.equals(Color.WHITE)) {
            trainBuilding.setText("Train Color: White " + "Route Length: " + routeLength);
        }
        else if(color.equals(Color.YELLOW)) {
            trainBuilding.setText("Train Color: Yellow " + "Route Length: " + routeLength);
        }
        else if(color.equals(Color.GRAY)) {
            trainBuilding.setText("Train Color: Gray " + "Route Length: " + routeLength);
        }
        else if(color.equals(Color.BLUE)) {
            trainBuilding.setText("Train Color: Blue " + "Route Length: " + routeLength);
        }
        
        for(int i = 0, length = cards.length; i < length; i++) {
            int occurrences = Collections.frequency(player.getTrainCards(), trainCardsArray[i]);
            cards[i].setText(String.valueOf(occurrences));
        }
    }
    
    /**
     * Checks to see if a player can purchase that route.
     *
     * @return true if player can purchase route. False if they cannot
     */
    public boolean canPurchase() {
        int routeLength = route.getLength();
        int ferry = route.getFerry();
        Color color = route.getColor();
        
        int trains = 0;
        ArrayList<Technology> playerTechnologies = player.getTechnologies();
        if(playerTechnologies.contains(Technology.DIESEL_POWER)) {
            if(routeLength - ferry > 1) {
                routeLength -= 1;
            }
        }
        
        if(color.equals(Color.GRAY)) {
            if(chosenOccurrences[0] >= ferry) {
                // If the player has enough locomotives to  cover the ferries.
                int totalTrains = 0;
                int maxTrains = 0;
                for(int i = 1, length = chosenOccurrences.length; i < length; i++) {
                    // Finds the train color with the most trains selected
                    if(maxTrains < chosenOccurrences[i]) {
                        maxTrains = chosenOccurrences[i];
                    }
                    // Counts the total number of trains
                    totalTrains += chosenOccurrences[i];
                }
                
                boolean equal = false;
                // If there is only one train color selected basically checks to see if there is a player is able to
                // purchase a route.
                if(maxTrains == totalTrains) {
                    equal = true;
                }
                
                if(playerTechnologies.contains(Technology.DIESEL_POWER)) {
                    // Allows player to play one less train card when buying a route.
                    return equal && (chosenOccurrences[0] == routeLength
                            || chosenOccurrences[0] + maxTrains == routeLength
                            || chosenOccurrences[0] == routeLength + 1
                            || chosenOccurrences[0] + maxTrains == routeLength + 1);
                }
                
                return equal && (chosenOccurrences[0] == routeLength
                        || chosenOccurrences[0] + maxTrains == routeLength);
            }
        }
        else {
            // Check to see how many train cards the player has chosen total.
            for(int i = 1, length = trainCardColors.length; i < length; i++) {
                if(chosenOccurrences[i] > 0) {
                    if(!trainCardColors[i].equals(route.getColor())) {
                        return false;
                    }
                }
                
                trains += chosenOccurrences[i];
            }
            if(playerTechnologies.contains(Technology.DIESEL_POWER)) {//If the player has the Diesel Power technology card, they
                // can either use the card
                //or not use the card.
                return chosenOccurrences[0] == routeLength
                        || chosenOccurrences[0] + trains == routeLength
                        || chosenOccurrences[0] == routeLength + 1
                        || chosenOccurrences[0] + trains == routeLength + 1;
            }
            
            return chosenOccurrences[0] == routeLength || chosenOccurrences[0] + trains == routeLength;
        }
        
        return false;
    }
    
    /**
     * Allows us to extend mouseAdapter so mouseClicked, mouseEntered and mouseExited can be used
     */
    private class Handler extends MouseAdapter {
        
        /**
         * Used in this class to select and deselect cards, allows user to exit screen by clicking back button, and
         * allows user to complete purchase.
         *
         * @param e instance of MouseEvent object.
         */
        @Override
        public void mouseClicked(MouseEvent e) {
            List<JLabel> cardsList = Arrays.asList(cards);
            
            if(cardsList.contains(e.getSource())) {
                // If you want to select a card
                int index = cardsList.indexOf(e.getSource());
                if(RightPanel.occurrences[index] > 0) {
                    // If that color train has at least one card
                    RightPanel.occurrences[index]--;
                    player.getTrainCards().remove(trainCardsArray[index]);
                    
                    chosenCards.add(trainCardsArray[index]);
                    chosenOccurrences[index]++;
                    
                    int occurrences = Collections.frequency(chosenCards, trainCardsArray[index]);
                    cards2[index].setText(String.valueOf(occurrences));
                    
                    repaint();
                }
                else {
                    JFrame frame = new JFrame();
                    JOptionPane.showMessageDialog(frame, "You don't have enough of that card.");
                }
            }
            
            // If you want to remove (deselect) a card
            List<JLabel> cards2List = Arrays.asList(cards2);
            if(cards2List.contains(e.getSource())) {
                int index = cards2List.indexOf(e.getSource());
                if(chosenOccurrences[index] > 0) {
                    // If that color train has at least one card
                    player.addTrainCard(trainCardsArray[index]);
                    RightPanel.occurrences[index]++;
                    
                    chosenCards.remove(trainCardsArray[index]);
                    chosenOccurrences[index]--;
                    
                    int occurrences = Collections.frequency(chosenCards, trainCardsArray[index]);
                    cards2[index].setText(String.valueOf(occurrences));
                    
                    repaint();
                }
                else {
                    JFrame frame = new JFrame();
                    JOptionPane.showMessageDialog(frame, "You don't have any card to deselect.");
                }
            }
            else if(e.getSource().equals(back_arrow)) {
                boolean empty = true;
                for(int chosenOccurrence : chosenOccurrences) {
                    if(chosenOccurrence != 0) {
                        empty = false;
                        break;
                    }
                }
                if(empty) {
                    Driver.cardLayout.show(Driver.cards, "Container");
                }
                else {
                    JFrame frame = new JFrame();
                    JOptionPane.showMessageDialog(frame, "Deselect cards before leaving.");
                }
            }
            else if(e.getSource().equals(completePurchase)) {
                boolean confirm = canPurchase();
                if(confirm) {
                    for(int i = 0, length = cards.length; i < length; i++) {
                        cards2[i].setText(String.valueOf(0));
                    }
                    
                    repaint();
                    
                    ArrayList<Technology> playerTechnologies = player.getTechnologies();
                    if(playerTechnologies.contains(Technology.THERMO_COMPRESSOR)) {
                        // Allows them to buy two routes in one turn
                        if(secondPath == 0) {
                            GameLoop.middlePanel.claimPath(route);
                            Driver.cardLayout.show(Driver.cards, "Container");
                            
                            chosenOccurrences = new int[9];
                            GameLoop.discardedTrainCards.addAll(chosenCards);
                            
                            if(ConvertPanel.tradedLocomotives < RightPanel.occurrences[0]) {
                                System.out.println("count");
                                RightPanel.occurrences[0]--;
                            }
                            else {
                                ConvertPanel.tradedLocomotives--;
                                RightPanel.occurrences[0]--;
                                GameLoop.discardedTrainCards.remove(TrainCard.LOCOMOTIVE);
                            }
                            
                            System.out.println(GameLoop.discardedTrainCards.size());
                            secondPath++;
                            
                            chosenCards.clear();
                            return;
                        }
                        
                        // Gets rid of players Thermocompressor card, and returns it.
                        playerTechnologies.remove(Technology.THERMO_COMPRESSOR);
                        Technology.THERMO_COMPRESSOR.addOne();
                    }
                    
                    GameLoop.middlePanel.setPlacedTrain(true);
                    GameLoop.middlePanel.claimPath(route);
                    
                    secondPath = 0;
                    
                    chosenOccurrences = new int[9];
                    GameLoop.discardedTrainCards.addAll(chosenCards);
                    System.out.println(GameLoop.discardedTrainCards.size());
                    chosenCards.clear();
                    
                    Driver.cardLayout.show(Driver.cards, "Container");
                }
                else {
                    JFrame frame = new JFrame();
                    JOptionPane.showMessageDialog(frame, "That is not a valid option.");
                }
            }
        }
        
        /**
         * Used in this class to change to hand cursor when hovering over a card.
         *
         * @param e instance from a mouse event
         */
        @Override
        public void mouseEntered(MouseEvent e) {
            if(Arrays.asList(cards).contains(e.getSource())) {
                setCursor(new Cursor(Cursor.HAND_CURSOR));
            }
        }
        
        /**
         * Used in this class to change back to regular cursor when done hovering over a card.
         *
         * @param e instance from mouse event
         */
        @Override
        public void mouseExited(MouseEvent e) {
            if(Arrays.asList(cards).contains(e.getSource())) {
                setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            }
        }
    }
}
