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
 * Allows the player to convert any four Train Cards (or locomotive, as per the rules) into a locomotive by trading them.
 * These "locomotives" are not drawn from the deck, but rather the Train Cards are used in their stead. The exception
 * to the cost is if the player owns the Booster technology, which allows them to trade in one less train card (3
 * instead of 4) if they so wish. Discarded trainCards are added to the Player.discardedTrainCards ArrayList, and
 * when the purchased "locomotive" is spent, it is not added to the Player.discardedTrainCards ArrayList, because it
 * technically does not exist.
 *
 * @author Josh Dratler
 * @author Hamza Memon
 * @author Patrick Milano
 * @author Jhoan Esteban Osorno
 * @author Brian Smith
 * @version (1.0)
 */
public class ConvertPanel extends JPanel {
    
    protected static JLabel trainBuilding = new JLabel();
    protected static TrainCard[] trainCardsArray = TrainCard.values();
    protected static TrainCard[] trainCardsArrayTemp = TrainCard.values();
    protected static JLabel[] cards = new JLabel[9];
    protected static JLabel[] cards2 = new JLabel[9];
    private int[] chosenOccurrences = new int[9];
    private ArrayList<TrainCard> chosenCards = new ArrayList<>();
    
    private static Player player;
    protected static int tradedLocomotives = 0;
    
    private JLabel completeConversion = new JLabel(ResourceLoader.loadImage("completeConversion.jpg"));
    private JLabel back_arrow = new JLabel(ResourceLoader.loadImage("back_arrow.png"));
    private ImageIcon topBackground = ResourceLoader.loadImage("converting_background1.jpg");
    private ImageIcon bottomBackground = ResourceLoader.loadImage("converting_background2.jpg");
    
    /**
     * Constructs the ConvertPanel JPanel. Its fields are assigned here, as well as the initialization of the JPanel
     * and the Train Card array and its values.
     */
    public ConvertPanel() {
        setPreferredSize(new Dimension(1280, 1024));
        setLayout(new BorderLayout());
        
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 20));
        panel.setPreferredSize(new Dimension(1280, 512));
        
        back_arrow.addMouseListener(new Handler());
        completeConversion.addMouseListener(new Handler());
        
        Font font = new Font("TimesRoman", Font.BOLD, 18);
        for(int i = 1, length = trainCardsArray.length; i < length; i++) {
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
        
        panel.setOpaque(false);
        add(panel, BorderLayout.SOUTH);
        
        JPanel panel2 = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 20));
        panel2.setPreferredSize(new Dimension(1280, 512));
        // Creates the back arrow that allows the player to exit without trading for a locomotive
        panel2.add(back_arrow, BorderLayout.LINE_START);
        
        for(int i = 1, length = trainCardsArrayTemp.length; i < length; i++) {
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
        panel2.setOpaque(false);
        add(panel2, BorderLayout.NORTH);
    }
    
    /**
     * Sets the current player to player.
     *
     * @param player new player to look at
     */
    public static void setPlayer(Player player) {
        ConvertPanel.player = player;
    }
    
    /**
     * Displays and update the value of the Train Cards array as well as the occurrences value, which keeps track of
     * the number of trains that were selected.
     *
     * @param g instance of Graphics object
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        topBackground.paintIcon(this, g, 0, 0);
        bottomBackground.paintIcon(this, g, 0, 511);
        
        for(int i = 1, length = cards.length; i < length; i++) {
            int occurrences = Collections.frequency(player.getTrainCards(), trainCardsArray[i]);
            cards[i].setText(String.valueOf(occurrences));
        }
    }
    
    /**
     * Handles the logic of the conversion from the trainCards to the locomotives. Also handles the case exception
     * for when the player has the Booster technology card.
     *
     * @return true if the convert was possible, false if it was not
     */
    public boolean canConvert() {
        int req = 4;
        int chosenCards = 0;
        
        ArrayList<Technology> playerTechnologies = player.getTechnologies();
        if(playerTechnologies.contains(Technology.BOOSTER)) {
            req = 3;
        }
        for(int i = 1, length = chosenOccurrences.length; i < length; i++) {
            chosenCards += chosenOccurrences[i];
        }
        
        return chosenCards == req || (playerTechnologies.contains(Technology.BOOSTER) && chosenCards == req + 1);
    }
    
    /**
     * Handles all MouseEvents. MouseAdapter is used in order to selectively override the methods of MouseListener.
     */
    private class Handler extends MouseAdapter {
        
        /**
         * Handles the selection of all different interactive objects in this JPanel.
         *
         * @param e instance of MouseEvent
         */
        @Override
        public void mouseClicked(MouseEvent e) {
            if(e.getSource().equals(back_arrow)) {
                boolean empty = true;
                for(int chosenOccurrence : chosenOccurrences) {
                    if(chosenOccurrence != 0) {
                        empty = false;
                        break;
                    }
                }
                
                // If the back arrow is clicked and no trains are selected, exit the screen without buying or trading
                if(empty) {
                    Driver.cardLayout.show(Driver.cards, "Container");
                }
                else {
                    // Instruct the player to remove their card selections before canceling the transaction
                    JFrame frame = new JFrame();
                    JOptionPane.showMessageDialog(frame, "Deselect cards before leaving.");
                }
            }
            
            List<JLabel> cardsList = Arrays.asList(cards);
            if(cardsList.contains(e.getSource())) {
                int index = cardsList.indexOf(e.getSource());
                if(RightPanel.occurrences[index] > 0) {
                    // When a train card is clicked, decrement a Train Card and add it to the array that will be traded
                    // for a locomotive
                    player.getTrainCards().remove(trainCardsArray[index]);
                    RightPanel.occurrences[index]--;
                    
                    chosenCards.add(trainCardsArray[index]);
                    chosenOccurrences[index]++;
                    
                    int occurrences = Collections.frequency(chosenCards, trainCardsArray[index]);
                    cards2[index].setText(String.valueOf(occurrences));
                    repaint();
                }
                else {
                    // If there is no more of that train color available, display a message to the player.
                    JFrame frame = new JFrame();
                    JOptionPane.showMessageDialog(frame, "You don't have enough of that card.");
                }
            }
            
            List<JLabel> cards2List = Arrays.asList(cards2);
            if(cards2List.contains(e.getSource())) {
                int index = cards2List.indexOf(e.getSource());
                if(chosenOccurrences[index] > 0) {
                    // If the array that contains the chosen cards is clicked on, decrement that card and add it back
                    // into the player's hand.
                    chosenCards.remove(trainCardsArray[index]);
                    chosenOccurrences[index]--;
                    
                    RightPanel.occurrences[index]++;
                    player.addTrainCard(trainCardsArray[index]);
                    
                    int occurrences = Collections.frequency(chosenCards, trainCardsArray[index]);
                    cards2[index].setText(String.valueOf(occurrences));
                    repaint();
                }
                else {
                    // If there is no more of that train color available, display a message to the player.
                    JFrame frame = new JFrame();
                    JOptionPane.showMessageDialog(frame, "You don't have any card to deselect.");
                }
            }
            if(e.getSource().equals(completeConversion)) {
                //attempt to complete the trade
                boolean confirm = canConvert();
                if(confirm) {
                    for(int i = 1, length = cards.length; i < length; i++) {
                        cards2[i].setText(String.valueOf(0));
                    }
                    
                    repaint();
                    // Increment the tradedLocomotives value to keep track of locomotives that were generated by
                    // purchase and not drawn from the deck.
                    tradedLocomotives++;
                    player.addTrainCard(TrainCard.LOCOMOTIVE);
                    Driver.cardLayout.show(Driver.cards, "Container");
                    chosenOccurrences = new int[9];
                    GameLoop.discardedTrainCards.addAll(chosenCards);
                    chosenCards.clear();
                }
                else {
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
        public void mouseEntered(MouseEvent e) {
            if(Arrays.asList(cards).contains(e.getSource())) {
                setCursor(new Cursor(Cursor.HAND_CURSOR));
            }
        }
        
        /**
         * Make the regular cursor appear when the mouse is not hovering over a clickable object.
         *
         * @param e instance of MouseEvent
         */
        @Override
        public void mouseExited(MouseEvent e) {
            if(Arrays.asList(cards).contains(e.getSource())) {
                setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            }
        }
    }
}
