import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Arrays;
import java.util.LinkedList;

/**
 * Creates train bottom panel, which allows the user to draw Train Cards from the deck.
 *
 * @author Josh Dratler
 * @author Hamza Memon
 * @author Patrick Milano
 * @author Jhoan Esteban Osorno
 * @author Brian Smith
 * @version (1.0)
 */
public class BottomPanel extends JPanel {
    
    protected static JLabel trainDeck = null;
    protected static JLabel buyTech = null;
    protected static JLabel endTurnEarly = null;
    protected static int shuffled = 0;
    
    private Player player = null;
    private TrainCard[] trainCards = new TrainCard[5];
    protected static JLabel[] trainCardsLabel = new JLabel[5];
    
    private int clickCount = 0;
    private boolean lastClickOpenHand = false;
    protected boolean hasPickedCards = false;
    
    private ImageIcon background = ResourceLoader.loadImage("bottomBG.jpg");
    
    /**
     * Constructor for bottom panel. Creates new panel on bottom of screen
     */
    public BottomPanel() {
        setPreferredSize(new Dimension(1280, 174));
        setLayout(new FlowLayout(FlowLayout.LEFT, 20, 20));
    }
    
    /**
     * Deals out 5 train cards and one locomotive from the deck for the user to choose from.
     */
    public void setup() {
        trainDeck = new JLabel(ResourceLoader.loadImage("train card.jpg"));
        trainDeck.addMouseListener(new Handler());
        add(trainDeck);
        
        for(int i = 0, length = trainCardsLabel.length; i < length; i++) {
            TrainCard card = GameLoop.trainCardsDeck.remove();
            trainCards[i] = card;
            
            trainCardsLabel[i] = new JLabel(card.getIcon());
            trainCardsLabel[i].addMouseListener(new Handler());
            trainCardsLabel[i].addMouseMotionListener(new Handler());
            add(trainCardsLabel[i]);
        }
        
        buyTech = new JLabel(ResourceLoader.loadImage("buyTechIcon.png"));
        buyTech.addMouseListener(new Handler());
        add(buyTech);
        
        endTurnEarly = new JLabel(ResourceLoader.loadImage("endTurnEarlyButton.jpg"));
        endTurnEarly.addMouseListener(new Handler());
        endTurnEarly.setVisible(false);
        add(endTurnEarly);
    }
    
    /**
     * Override method to paintComponent. Paints the background.
     *
     * @param g instance of Graphics.
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        background.paintIcon(this, g, 0, 0);
    }
    
    /**
     * Method to set the player for the Panel
     *
     * @param player new player to be set.
     */
    public void setPlayer(Player player) {
        this.player = player;
    }
    
    /**
     * Method to get the number of clicks the user did
     *
     * @return the number of clicks the user did
     */
    public int getClickCount() {
        return clickCount;
    }
    
    /**
     * Class to override the MouseAdapter class, so we can use mouseClicked,  mouseEntered, and mouseExited.
     */
    private class Handler extends MouseAdapter {
        /**
         * Override of mouseClick method. We use this method to allow the user to click on a card to draw it.
         *
         * @param e MouseEvent instance
         */
        @Override
        public void mouseClicked(MouseEvent e) {
            if(e.getSource().equals(buyTech)) {
                // Show the buy technology panel
                Driver.cardLayout.show(Driver.cards, "BuyTechnologyPanel");
            }
            else if(e.getSource().equals(endTurnEarly)) {
                // If user clicks to end the turn early
                clickCount = 0;
                lastClickOpenHand = false;
                hasPickedCards = true;
            }
            else if(e.getSource().equals(trainDeck)) {
                // If user clicked on the train deck
                clickCount++;
                
                //reshuffle the deck
                if(GameLoop.trainCardsDeck.isEmpty()) {
                    JFrame frame = new JFrame();
                    if(GameLoop.discardedTrainCards.isEmpty()) {
                        JOptionPane.showMessageDialog(frame, "There are no more cards to draw. Buy paths or technologies so that the discarded cards get reshuffled into the deck.");
                        return;
                    }
                    
                    if(shuffled == 0) {
                        JOptionPane.showMessageDialog(frame, "The deck ran out. The discarded cards were reshuffled and put back into the deck. The technology cards 'Risky Contracts' and 'Equalising Beam' are no longer available.");
                    }
                    else {
                        JOptionPane.showMessageDialog(frame, "The deck ran out. The discarded cards were reshuffled and put back into the deck.");
                    }
                    
                    shuffled++;
                    GameLoop.trainCardsDeck = new LinkedList<>(GameLoop.discardedTrainCards);
                    GameLoop.discardedTrainCards.clear();
                }
                
                TrainCard card = GameLoop.trainCardsDeck.remove();
                player.addTrainCard(card);
                
                // Handle a case where the player has water tenders
                if(player.getTechnologies().contains(Technology.WATER_TENDERS) && !lastClickOpenHand) {
                    if(clickCount == 3) {
                        clickCount = 0;
                        lastClickOpenHand = false;
                        hasPickedCards = true;
                    }
                }
                else {
                    if(clickCount == 2) {
                        clickCount = 0;
                        lastClickOpenHand = false;
                        hasPickedCards = true;
                    }
                }
            }
            
            if(Arrays.asList(trainCardsLabel).contains(e.getSource())) {
                lastClickOpenHand = true;
                
                if(player.getTechnologies().contains(Technology.WATER_TENDERS) && clickCount == 2) {
                    JFrame frame = new JFrame();
                    JOptionPane.showMessageDialog(frame, "You must draw another card from the deck. You cannot draw a third card from the open hand.");
                    lastClickOpenHand = false;
                    return;
                }
                
                int index = Arrays.asList(trainCardsLabel).indexOf(e.getSource());
                if(e.getSource().equals(trainCardsLabel[index])) {
                    clickCount++;
                    
                    // Reshuffle the deck
                    if(GameLoop.trainCardsDeck.isEmpty()) {
                        JFrame frame = new JFrame();
                        if(GameLoop.discardedTrainCards.isEmpty()) {
                            JOptionPane.showMessageDialog(frame, "There are no more cards to draw. Buy paths or technologies so that the discarded cards get reshuffled into the deck.");
                            return;
                        }
                        
                        if(shuffled == 0) {
                            JOptionPane.showMessageDialog(frame, "The deck ran out. The discarded cards were reshuffled and put back into the deck. The technology cards 'Risky Contracts' and 'Equalising Beam' are no longer available.");
                        }
                        else {
                            JOptionPane.showMessageDialog(frame, "The deck ran out. The discarded cards were reshuffled and put back into the deck.");
                        }
                        
                        shuffled++;
                        GameLoop.trainCardsDeck = new LinkedList<>(GameLoop.discardedTrainCards);
                        GameLoop.discardedTrainCards.clear();
                    }
                    if(clickCount == 1 && trainCards[index] == TrainCard.LOCOMOTIVE) {
                        clickCount = 0;
                        lastClickOpenHand = false;
                        hasPickedCards = true;
                    }
                    if(clickCount == 2 && trainCards[index] == TrainCard.LOCOMOTIVE) {
                        JFrame frame = new JFrame();
                        JOptionPane.showMessageDialog(frame, "You have already selected a train. You cannot select a locomotive.");
                        clickCount--;
                    }
                    else {
                        player.addTrainCard(trainCards[index]);
                        TrainCard card = GameLoop.trainCardsDeck.remove();
                        trainCards[index] = card;
                        trainCardsLabel[index].setIcon(card.getIcon());
                    }
                    if(clickCount == 2) {
                        clickCount = 0;
                        lastClickOpenHand = false;
                        hasPickedCards = true;
                    }
                }
            }
        }
        
        /**
         * Method to change the cursor to hand cursor
         *
         * @param e instance of MouseEvent
         */
        @Override
        public void mouseEntered(MouseEvent e) {
            setCursor(new Cursor(Cursor.HAND_CURSOR));
        }
        
        /**
         * Method to change back to default cursor
         *
         * @param e instance of MouseEvent
         */
        @Override
        public void mouseExited(MouseEvent e) {
            setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        }
    }
}
