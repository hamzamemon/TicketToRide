import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * Creates the left panel, which contains a button to get to the instructions, button to convert trains to
 * locomotives, and a card deck to click on where a player may click on a destination cards to claim for their turn.
 *
 * @author Josh Dratler
 * @author Hamza Memon
 * @author Patrick Milano
 * @author Jhoan Esteban Osorno
 * @author Brian Smith
 * @version (1.0)
 */
public class LeftPanel extends JPanel {
    
    protected static JLabel deckLabel;
    protected static Queue<DestinationCard> destinationCards = new LinkedList<>();
    protected boolean pickedDestinationCard;
    private boolean drawCards;
    protected JLabel instructions = new JLabel();
    protected JLabel convertLoco = new JLabel();
    private DestinationCard[] cardsArray = new DestinationCard[3];
    private JLabel[] destinationCardsChoices = new JLabel[3];
    private JButton confirmDestinationCards = new JButton("Confirm choices?");
    private boolean[] selected = new boolean[3];
    
    private Player player;
    private Border border;
    
    private ImageIcon background = ResourceLoader.loadImage("leftBG.jpg");
    
    /**
     * Constructor to make new instance of LeftPanel. Sets up the destination cards, instruction button,
     * convert trains to locomotive button, and shuffles the deck. Also creates a panel on the left of the screen
     */
    public LeftPanel() {
        setPreferredSize(new Dimension(356, 850));
        setLayout(new FlowLayout(FlowLayout.CENTER, 15, 20));
        
        DestinationCard[] destinationCard = DestinationCard.values();
        Collections.addAll(destinationCards, destinationCard);
        
        Collections.shuffle((List<?>) destinationCards);
        
        ImageIcon deck_img = ResourceLoader.loadImage("destination card.jpg");
        
        // Adds destination card
        Icon convertLocoIcon = ResourceLoader.loadImage("convertTrainsToLocomotive.jpg");
        convertLoco.setIcon(convertLocoIcon);
        convertLoco.addMouseListener(new Handler());
        add(convertLoco);
        
        // Adds instructions button
        Icon instructionsIcon = ResourceLoader.loadImage("instructionsButton.png");
        instructions.setIcon(instructionsIcon);
        instructions.addMouseListener(new Handler());
        add(instructions);
        
        deckLabel = new JLabel(deck_img);
        deckLabel.addMouseListener(new Handler());
        add(deckLabel);
        
        for(int i = 0, length = cardsArray.length; i < length; i++) {
            cardsArray[i] = destinationCards.remove();
        }
        
        for(int i = 0, length = destinationCardsChoices.length; i < length; i++) {
            destinationCardsChoices[i] = new JLabel(cardsArray[i].getIcon());
            destinationCardsChoices[i].setVisible(false);
            destinationCardsChoices[i].addMouseListener(new Handler());
            add(destinationCardsChoices[i]);
        }
        
        border = destinationCardsChoices[0].getBorder();
        
        confirmDestinationCards.setVisible(false);
        confirmDestinationCards.addActionListener(new Handler());
        add(confirmDestinationCards);
    }
    
    /**
     * Paints the background.
     *
     * @param g instance of Graphics class
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        background.paintIcon(this, g, 0, 0);
    }
    
    /**
     * Sets the current player
     *
     * @param player new player to be set
     */
    public void setPlayer(Player player) {
        this.player = player;
    }
    
    /**
     * Checks if the player has drawn a card
     *
     * @return player has drawn a card
     */
    public boolean isDrawCards() {
        return drawCards;
    }
    
    /**
     * Sets if player has drawn a card
     *
     * @param drawCards value to set drawn cards to
     */
    public void setDrawCards(boolean drawCards) {
        this.drawCards = drawCards;
    }
    
    private class Handler extends MouseAdapter implements ActionListener {
        
        /**
         * Used to either select instructions, convert trains to locomotives or to click to draw a destination card
         *
         * @param e instance of MouseEvent object.
         */
        @Override
        public void mouseClicked(MouseEvent e) {
            if(e.getSource().equals(instructions)) {
                // If player clicked on instructions
                Driver.cardLayout.next(Driver.cards);
            }
            else if(e.getSource().equals(convertLoco)) {
                // If player clicked to convert to locomotive
                Driver.cardLayout.show(Driver.cards, "ConvertPanel");
            }
            else {
                // If player clicked on destination card icon
                drawCards = true;
                
                if(e.getSource().equals(deckLabel)) {
                    deckLabel.setVisible(false);
                    confirmDestinationCards.setVisible(!confirmDestinationCards.isVisible());
                    BottomPanel.buyTech.setVisible(false);
                    instructions.setVisible(false);
                    convertLoco.setVisible(false);
                    
                    MiddlePanel.canHover = !MiddlePanel.canHover;
                    BottomPanel.trainDeck.setVisible(!BottomPanel.trainDeck.isVisible());
                    
                    for(int i = 0, length = BottomPanel.trainCardsLabel.length; i < length; i++) {
                        BottomPanel.trainCardsLabel[i].setVisible(!BottomPanel.trainCardsLabel[i].isVisible());
                    }
                    for(JLabel choice : destinationCardsChoices) {
                        choice.setVisible(!choice.isVisible());
                    }
                }
                else {
                    JLabel label = (JLabel) e.getSource();
                    int index = Arrays.asList(destinationCardsChoices).indexOf(label);
                    
                    if(selected[index]) {
                        label.setBorder(border);
                    }
                    else {
                        label.setBorder(BorderFactory.createLineBorder(Color.YELLOW, 5));
                    }
                    
                    selected[index] = !selected[index];
                }
            }
        }
        
        /**
         * Used here to find the three destination cards to display
         *
         * @param e instance of ActionEvent
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            boolean isSelected = false;
            for(boolean b : selected) {
                if(b) {
                    isSelected = true;
                    break;
                }
            }
            if(!isSelected) {
                return;
            }
            
            for(JLabel label : destinationCardsChoices) {
                label.setBorder(border);
            }
            
            for(int i = 0, length = selected.length; i < length; i++) {
                if(selected[i]) {
                    selected[i] = false;
                    destinationCards.remove(cardsArray[i]);
                    player.addDestinationCard(cardsArray[i]);
                }
                
                else {
                    destinationCards.add(cardsArray[i]);
                }
                
                if(destinationCards.isEmpty()) {
                    // If there are no destination cards
                    destinationCardsChoices[i].setIcon(null);
                }
                
                else {
                    cardsArray[i] = destinationCards.remove();
                    destinationCardsChoices[i].setIcon(cardsArray[i].getIcon());
                }
            }
            
            pickedDestinationCard = true;
            confirmDestinationCards.setVisible(false);
            Arrays.asList(destinationCardsChoices).forEach(i -> i.setVisible(false));
        }
        
        /**
         * Changes the mouse cursor to a hand when hovering over a card.
         *
         * @param e instance of MouseEvent
         */
        @Override
        public void mouseEntered(MouseEvent e) {
            setCursor(new Cursor(Cursor.HAND_CURSOR));
        }
        
        /**
         * Changes the mouse cursor back to a regular cursor after leaving a card.
         *
         * @param e instance of MouseEvent
         */
        @Override
        public void mouseExited(MouseEvent e) {
            setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        }
    }
}
