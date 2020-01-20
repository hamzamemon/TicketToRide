import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Collections;
import java.awt.event.*;
import java.util.*;

/**
 * Class to create the panel where the player is able to purchase routes
 * 
 * @author Josh Dratler
 * @author Hamza Memon
 * @author Patrick Milano
 * @author Jhoan Esteban Osorno
 * @author Brian Smith
 * @version (1.0)
 */
public class BuyRoutePanel extends JPanel
{
    // instance variables - replace the example below with your own

    private JLabel completePurchase = new JLabel(ResourceLoader.loadImage("confirmPurchaseButton.png"));
    //confirm button
    private Font font = new Font("TimeRoman", Font.BOLD, 18);

    protected static JLabel trainBuilding = new JLabel(); //label intented to tell user what color and how many trains they need
    private static Player player;
    private  static Route route;
    protected static int secondPath = 0;
    protected static TrainCard[] trainCardsArray; 
    protected static TrainCard[] trainCardsArrayTemp;
    protected static JLabel[] cards = new JLabel[9];//JLabel array to hold train card images the user hasnt selected
    protected static JLabel[] cards2 = new JLabel[9];//JLabel array to hold train card images the user has selected
    private int[] chosenOccurrences = new int[9];//array to hold the cards the user has chosen.
    private JLabel back_arrow = new JLabel(ResourceLoader.loadImage("back_arrow.png"));
    private ArrayList<TrainCard> chosenCards = new ArrayList<>(); //arraylist to hold cards the user has chosen
    private final Color[] trainCardColors = {Color.MAGENTA, Color.BLACK, Color.BLUE, Color.GREEN, Color.ORANGE, Color.PINK,Color.RED,Color.WHITE,Color.YELLOW};
    //array to hold color of train cards
    private ImageIcon background;

    private ImageIcon topBackground;
    private ImageIcon bottomBackground;
    /**
     * Constructor for objects of the buy route panel. Initializes background, 
     * draws train cards, draw two panels, draws selected train cards, draws back arrow,
     * and complete purchase button.
     * 
     */
    public BuyRoutePanel()
    {
        // initialise instance variables
        topBackground = ResourceLoader.loadImage("claiming_background1.jpg");
        bottomBackground = ResourceLoader.loadImage("claiming_background2.jpg");

        setPreferredSize(new Dimension(1280, 1024));
        setLayout(new BorderLayout());

        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 20));
        panel.setPreferredSize(new Dimension(1280, 512));

        back_arrow.addMouseListener(new Handler());
        completePurchase.addMouseListener(new Handler());
        trainCardsArray = TrainCard.values();
        trainCardsArrayTemp = TrainCard.values();

        //draw all the train cards onto the screen
        for(int i = 0; i < trainCardsArray.length; i++){
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
        //panel2.setBackground(Color.WHITE);

        panel2.add(back_arrow, BorderLayout.LINE_START);

        //draw all the selected train cards onto the screen
        for(int i = 0; i < trainCardsArrayTemp.length; i++){
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
        add(panel2, BorderLayout.NORTH);
        panel.setOpaque(false);
        panel2.setOpaque(false);
    }

    /**
     * Override method from JPanel. Allows objects to be painted to the screen. In this 
     * class, we show the user the length route and color they are looking to fill, and we 
     * draw the train cards onto the screen.
     * 
     * @param g instance of Graphics class.
     */
    @Override
    protected void paintComponent(Graphics g){
        super.paintComponent(g);
        topBackground.paintIcon(this,g,0,0);        
        bottomBackground.paintIcon(this,g,0,511);

        int length = route.getLength();
        int ferry = route.getFerry();
        trainBuilding.setForeground(Color.YELLOW);
        if(player.getTechnologies().contains(Technology.DIESEL_POWER))
        {   //if user has diesel power card 
            if((length - ferry) > 1)
                length -= 1;
        }
        //tell the user what color trains and how many trains they are looking for
        if(route.getColor() == Color.RED)
        {
            trainBuilding.setText("Train Color: Red " + "Route Length: " + length);
        }
        if(route.getColor() == Color.GREEN)
        {
            trainBuilding.setText("Train Color: Green " + "Route Length: " + length);
        }
        if(route.getColor() == Color.PINK)
        {
            trainBuilding.setText("Train Color: Pink " + "Route Length: " + length);
        }
        if(route.getColor() == Color.BLACK)
        {
            trainBuilding.setText("Train Color: Black " + "Route Length: " + length);
        }
        if(route.getColor() == Color.ORANGE)
        {
            trainBuilding.setText("Train Color: Orange " + "Route Length: " + length);
        }
        if(route.getColor() == Color.WHITE)
        {
            trainBuilding.setText("Train Color: White " + "Route Length: " + length);
        }
        if(route.getColor() == Color.YELLOW)
        {
            trainBuilding.setText("Train Color: Yellow " + "Route Length: " + length);
        }
        if(route.getColor() == Color.GRAY)
        {
            trainBuilding.setText("Train Color: Gray " + "Route Length: " + length);
        }
        if(route.getColor() == Color.BLUE)
        {
            trainBuilding.setText("Train Color: Blue " + "Route Length: " + length);
        }
        for(int i = 0; i < cards.length; i++)
        {   //draw the train cards
            int occurrences = Collections.frequency(player.getTrainCards(), trainCardsArray[i]);
            cards[i].setText(String.valueOf(occurrences));
        }
    }

    /**
     * Method to set the route
     * 
     * @param rou the new route
     */
    public static void setRoute(Route rou)
    {
        route = rou;
    }

    /**
     * Method to set the player for this panel
     * 
     * @param player the new player to be set
     */

    public static void setPlayer(Player player){
        BuyRoutePanel.player = player;
    }

    /**
     * Method to check to see if a player can purchase that route. 
     * 
     * @return true if player can purchase route. False if they cannot
     */
    public boolean purchase()
    {
        int length = route.getLength();
        int ferry = route.getFerry();
        Color color = route.getColor();

        Color col = route.getColor();
        int trains = 0;
        if(player.getTechnologies().contains(Technology.DIESEL_POWER))
        {
            if((length - ferry) > 1)
                length -= 1;
        }
        if (color != Color.GRAY)
        {   //check to see how many train cards the player has chosen total.
            for(int i = 1; i < trainCardColors.length; i++)
            {
                if (chosenOccurrences[i] > 0)
                {
                    if(trainCardColors[i] != route.getColor())
                        return false;
                }
                trains += chosenOccurrences[i];
            }
            if(player.getTechnologies().contains(Technology.DIESEL_POWER))
            {//If the player has the Diesel Power technology card, they can either use the card
                //or not use the card.
                if(chosenOccurrences[0] == length || chosenOccurrences[0] +  trains == length ||
                chosenOccurrences[0] == length + 1 || chosenOccurrences[0] +  trains == length + 1)
                {
                    return true;
                }
                else 
                {
                    return false;
                }
            }
            else
            {   //if locomotives cover length of path, or locomotives and trains combined cover 
                //length of path.
                if(chosenOccurrences[0] == length || chosenOccurrences[0] +  trains == length)
                {
                    return true;
                }
                else 
                {  //if they dont
                    return false;
                }
            }
        }
        else//color is gray
        {
            if(chosenOccurrences[0] >= ferry)
            {//if the player has enough locomotives to  cover the ferries.
                int totalTrains = 0;
                int maxTrains = 0;
                for (int i = 1; i < chosenOccurrences.length; i++)
                {  //find the train color with the most trains selected
                    if(maxTrains < chosenOccurrences[i])
                        maxTrains = chosenOccurrences[i];
                    //count the total number of trains
                    totalTrains += chosenOccurrences[i];
                }
                boolean equal = false;
                //if there is only one train color selected
                //basically checks to see if there is a player is 
                //able to purchase a route.
                if(maxTrains == totalTrains)
                    equal = true;

                if(player.getTechnologies().contains(Technology.DIESEL_POWER))
                {//allows player to play one less train card when buying a route.
                    if((chosenOccurrences[0] == length && equal) || 
                    (chosenOccurrences[0] + maxTrains == length && equal) || 
                    chosenOccurrences[0] == length + 1 && equal
                    || chosenOccurrences[0] + maxTrains == length + 1 && equal)
                    {
                        return true;
                    }
                }
                else
                {
                    //if # of locomotives == length of route and equal is true
                    //or if loco + train pieces add up to route length and equal is true
                    if((chosenOccurrences[0] == length && equal) || 
                    (chosenOccurrences[0] + maxTrains == length && equal))
                    {
                        return true;
                    }
                }
            }
        }
        return false;
    }
    /**
     * Class that allows us to extend mouseAdapter so mouseClicked, 
     * mouseEntered, and mouseExited can be used
     */
    private class Handler extends MouseAdapter 
    {
        /**
         * Override method from mouselistener. Used in this class to 
         * select and unselect cards, allows user to exit screen by 
         * clicking back button, and allows user to complete purchase.
         * 
         * @param e instance of mouseevent object. 
         */
        @Override
        public void mouseClicked(MouseEvent e){
            //if you want to select a card
            if (Arrays.asList(cards).contains(e.getSource()))
            {   //if card was clicked on
                int index = Arrays.asList(cards).indexOf(e.getSource());
                if (RightPanel.occurrences[index] > 0)
                {  //if that color train has at least one card
                    RightPanel.occurrences[index] --;
                    chosenOccurrences[index] ++;
                    player.getTrainCards().remove(trainCardsArray[index]);
                    chosenCards.add(trainCardsArray[index]);
                    JLabel label = cards2[index];                    
                    int occurrences = Collections.frequency(chosenCards, trainCardsArray[index]);
                    cards2[index].setText(String.valueOf(occurrences));
                    repaint();
                }
                else
                {  //otherwise that player doesnt have any cards, and let the player know
                    JFrame frame = new JFrame();
                    JOptionPane.showMessageDialog(frame, "You don't have enough of that card.");
                }
            }
            //if you want to remove (deselct) a card
            if (Arrays.asList(cards2).contains(e.getSource()))
            {  //if card was clicked on
                int index = Arrays.asList(cards2).indexOf(e.getSource());
                if (chosenOccurrences[index] > 0)
                { //if that color train has at least one card
                    RightPanel.occurrences[index] ++;
                    chosenOccurrences[index] --;
                    chosenCards.remove(trainCardsArray[index]);
                    player.addTrainCard(trainCardsArray[index]);
                    JLabel label = cards2[index];                    
                    int occurrences = Collections.frequency(chosenCards, trainCardsArray[index]);
                    cards2[index].setText(String.valueOf(occurrences));
                    repaint();
                }
                else
                {//otherwise that player doesnt have cards to deselect, and let the player know
                    JFrame frame = new JFrame();
                    JOptionPane.showMessageDialog(frame, "You don't have any card to deselect.");
                }
            }
            if(e.getSource().equals(back_arrow))
            {//if you click on backarrow
                boolean empty = true;
                for (int i = 0; i < chosenOccurrences.length; i++)
                {//check to see if player has any cards selected
                    if(chosenOccurrences[i] != 0)
                    {
                        empty = false;
                        break;
                    }
                }
                if(empty)
                {//if player has no cards selected
                    Driver.cardLayout.show(Driver.cards, "Container");
                }
                else
                {//otherwise player has cards selected, dont let them leave and alert them.
                    JFrame frame = new JFrame();
                    JOptionPane.showMessageDialog(frame, "Deselect cards before leaving.");
                }
            }
            if (e.getSource().equals(completePurchase))
            {  //if player clicks on complete purchase button
                boolean confirm = purchase();
                if (confirm)
                {  //if player is able to purchase
                    for (int i = 0; i < cards.length; i++)
                    {
                        JLabel label = cards2[i];                    
                        cards2[i].setText(String.valueOf(0));
                    }
                    repaint();

                    if (player.getTechnologies().contains(Technology.THERMO_COMPRESSOR))
                    {   //if player has a thermocompressor which allows them to buy two routes
                        //in one turn
                        if (secondPath == 0)
                        {
                            GameLoop.middlePanel.claimPath(route);
                            Driver.cardLayout.show(Driver.cards, "Container");
                            chosenOccurrences = new int[9];
                            GameLoop.discardedTrainCards.addAll(chosenCards);
                            if(ConvertPanel.tradedLocomotives < RightPanel.occurrences[0])
                            {
                                System.out.println("count");
                                RightPanel.occurrences[0]--;
                            }
                            else
                            {
                                ConvertPanel.tradedLocomotives--;
                                RightPanel.occurrences[0]--;
                                GameLoop.discardedTrainCards.remove(TrainCard.LOCOMOTIVE);
                            }
                            System.out.println(GameLoop.discardedTrainCards.size());
                            chosenCards.clear();
                            secondPath ++;
                            return;
                        }
                        else
                        {   //gets rid of players thermocompressor card, and returns it.
                            player.getTechnologies().remove(Technology.THERMO_COMPRESSOR);
                            Technology.THERMO_COMPRESSOR.addOne();
                        }
                    }

                    MiddlePanel.placedTrain = true;
                    GameLoop.middlePanel.claimPath(route);
                    Driver.cardLayout.show(Driver.cards, "Container");
                    secondPath = 0;
                    chosenOccurrences = new int[9];
                    GameLoop.discardedTrainCards.addAll(chosenCards);
                    System.out.println(GameLoop.discardedTrainCards.size());
                    chosenCards.clear();
                } else
                {
                    JFrame frame = new JFrame();
                    JOptionPane.showMessageDialog(frame, "That is not a valid option."); 
                }
            }
        }

        /**
         * Override method of mouselistener. Used in this class to change 
         * to hand cursor when hovering over a card.
         * 
         * @param e instance from a mouse event
         */
        @Override
        public void mouseEntered(MouseEvent e){
            if (Arrays.asList(cards).contains(e.getSource()))
            {
                setCursor(new Cursor(Cursor.HAND_CURSOR));
            }
        }

        /**
         * Override method of mouselistener. Used in this class to change
         * back to regular cursor when done hovering over a card.
         * 
         * 
         * @param e instance from mouse event 
         */
        @Override
        public void mouseExited(MouseEvent e){
            if (Arrays.asList(cards).contains(e.getSource()))
            {
                setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            }
        }

    }
}
