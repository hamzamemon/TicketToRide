import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Collections;
import java.awt.event.*;
import java.util.*;

/**
 * Allows the player to convert any four trainCards (or locomotive, as per the rules) into a locomotive by trading them in.
 * These "locomotives" are not drawn from the deck, but rather the trainCards are used in their stead. The exception to the cost
 * is if the player owns the Booster technology, which allows them to trade in one less train card (3 instead of 4) if they so wish.
 * Discarded trainCards are added to the Player.discardedTrainCards ArrayList, and when the purchased "locomotive" is spent,
 * it is not added to the Player.discardedTrainCards ArrayList, because it technically does not exist.
 * 
 * @author Josh Dratler
 * @author Hamza Memon
 * @author Patrick Milano
 * @author Jhoan Esteban Osorno
 * @author Brian Smith
 * @version (1.0)
 */
public class ConvertPanel extends JPanel
{
    // instance variables - replace the example below with your own

    private JLabel completeConversion = new JLabel(ResourceLoader.loadImage("completeConversion.jpg"));
    private Font font = new Font("TimeRoman", Font.BOLD, 18);

    protected static JLabel trainBuilding = new JLabel(); 
    private static Player player;
    private  static Route route;
    protected static TrainCard[] trainCardsArray;
    protected static TrainCard[] trainCardsArrayTemp;
    protected static JLabel[] cards = new JLabel[9];
    protected static JLabel[] cards2 = new JLabel[9];
    protected static int tradedLocomotives = 0;
    private int[] chosenOccurrences = new int[9];
    private JLabel back_arrow = new JLabel(ResourceLoader.loadImage("back_arrow.png"));
    private ArrayList<TrainCard> chosenCards = new ArrayList<>();
    private final Color[] trainCardColors = {Color.MAGENTA, Color.BLACK, Color.BLUE, Color.GREEN, Color.ORANGE, Color.PINK,Color.RED,Color.WHITE,Color.YELLOW};
     
    private ImageIcon topBackground;
    private ImageIcon bottomBackground;
    /**
     * Constructor for objects of the ConvertPanel JPanel. Its fields are assigned here, as well as the initialization
     * of the JPanel and the trainCard array and its values.
     */
    public ConvertPanel()
    {
        // initialise instance variables
        
        topBackground = ResourceLoader.loadImage("converting_background1.jpg");
        bottomBackground = ResourceLoader.loadImage("converting_background2.jpg");
        setPreferredSize(new Dimension(1280, 1024));
        setLayout(new BorderLayout());

        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 20));
        panel.setPreferredSize(new Dimension(1280, 512));

        back_arrow.addMouseListener(new Handler());
        completeConversion.addMouseListener(new Handler());
        trainCardsArray = TrainCard.values();
        trainCardsArrayTemp = TrainCard.values();

        for(int i = 1; i < trainCardsArray.length; i++){
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
        //creates the back arrow that allows the player to exit without trading for a locomotive
        panel2.add(back_arrow, BorderLayout.LINE_START);

        for(int i = 1; i < trainCardsArrayTemp.length; i++){
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
        panel2.add(completeConversion, BorderLayout.LINE_START);
        add(panel2, BorderLayout.NORTH);
        panel.setOpaque(false);
        panel2.setOpaque(false);
    }

    /**
     * Override of the paintComponent method of the JPanel class in order to display and update the value of the trainCards
     * array as well as the occurrences value, which keeps track of the number of trains that were selected.
     * 
     * @param g instance of Graphics object
     */
    @Override
    protected void paintComponent(Graphics g){
        super.paintComponent(g);
        topBackground.paintIcon(this,g,0,0);        
        bottomBackground.paintIcon(this,g,0,511);
        for(int i = 1; i < cards.length; i++)
        {
            int occurrences = Collections.frequency(player.getTrainCards(), trainCardsArray[i]);
            cards[i].setText(String.valueOf(occurrences));
        }
    }

    /**
     * Sets the current player to player, since the ConvertPanel.player is private in ConvertPanel
     * 
     * @param player new player to look at
     */
    public static void setPlayer(Player player)
    {
        ConvertPanel.player = player;
    }

    /**
     * Handles the logic of the conversion from the trainCards to the locomotives. Also
     * handles the case exception for when the player has the Booster technology card. 
     * 
     * @return true if the convert was possible, false if it was not
     */
    public boolean convert()
    {
        int req = 4;
        int chosenCards = 0;
        if(player.getTechnologies().contains(Technology.BOOSTER))
            req = 3;
        for(int i = 1; i < chosenOccurrences.length; i++)
        {
            chosenCards += chosenOccurrences[i];
        }
        if(chosenCards == req)
            return true;
        else if(player.getTechnologies().contains(Technology.BOOSTER) && chosenCards == req + 1)
            return true;
        return false;
    }
    /**
     * Handles all MouseEvents. MouseAdapter is used in order to selectively override the methods of MouseListener.
     */
    private class Handler extends MouseAdapter 
    {
        /**
         *Override of the mouseClicked method from MouseAdapter. Handles the selection of all different interactive objects in this JPanel. 
         *
         *@param e instance of MouseEvent
         */
        @Override
        public void mouseClicked(MouseEvent e)
        {
            if(e.getSource().equals(back_arrow))
            {
                boolean empty = true;
                for (int i = 0; i < chosenOccurrences.length; i++)
                {
                    if(chosenOccurrences[i] != 0)
                    {
                        empty = false;
                        break;
                    }
                }
                //if the back arrow is clicked and no trains are selected, exit the screen without buying or trading in anything
                if(empty)
                {
                    Driver.cardLayout.show(Driver.cards, "Container");
                }
                else
                {
                    //instruct the player to remove their card selections before canceling the transaction
                    JFrame frame = new JFrame();
                    JOptionPane.showMessageDialog(frame, "Deselect cards before leaving.");
                }
            }
            if (Arrays.asList(cards).contains(e.getSource()))
            {
                int index = Arrays.asList(cards).indexOf(e.getSource());
                if (RightPanel.occurrences[index] > 0)
                {
                    //when a train card is clicked, decrement a trainCard and add it to the array that will be traded for a locomotive
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
                {
                    //if there is no more of that train color available, display a message to the player.
                    JFrame frame = new JFrame();
                    JOptionPane.showMessageDialog(frame, "You don't have enough of that card.");
                }
            }
            if (Arrays.asList(cards2).contains(e.getSource()))
            {
                int index = Arrays.asList(cards2).indexOf(e.getSource());
                if (chosenOccurrences[index] > 0)
                {
                    //if the array that contains the chosen cards is clicked on, decrement that card and add it back
                    //into the player's hand.
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
                {
                    //if there is no more of that train color available, display a message to the player.
                    JFrame frame = new JFrame();
                    JOptionPane.showMessageDialog(frame, "You don't have any card to deselect.");
                }
            }
            if(e.getSource().equals(completeConversion))
            {
                //attempt to complete the trade
                boolean confirm = convert();
                if(confirm)
                {
                    for (int i = 1; i<cards.length; i++)
                    {
                        JLabel label = cards2[i];                    
                        cards2[i].setText(String.valueOf(0));
                    }
                    repaint();
                    //increment the tradedLocomotives value to keep track of locomotives that
                    //were generated by purchase and not drawn from the deck.
                    tradedLocomotives++;
                    player.addTrainCard(TrainCard.LOCOMOTIVE);
                    Driver.cardLayout.show(Driver.cards, "Container");
                    chosenOccurrences = new int[9];
                    GameLoop.discardedTrainCards.addAll(chosenCards);
                    chosenCards.clear();
                }
                else
                {
                    JFrame frame = new JFrame();
                    JOptionPane.showMessageDialog(frame, "That is not a valid option."); 
                }
            }
        }

        /**
         * Make a hand cursor appear when the mouse hovers over a clickable object.
         * 
         * @param e instance of MouseEvent
         */
        @Override
        public void mouseEntered(MouseEvent e){
            if (Arrays.asList(cards).contains(e.getSource()))
            {
                setCursor(new Cursor(Cursor.HAND_CURSOR));
            }
        }

        /**
         * Make the regular cursor appear when the mouse is not hovering over a 
         * clickable object.
         * 
         * @param e instance of MouseEvent
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
