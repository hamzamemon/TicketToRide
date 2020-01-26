import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Creates the right panel, where the user's train cards, locomotives, player image, destination cards, points, and
 * technology cards are displayed.
 *
 * @author Josh Dratler
 * @author Hamza Memon
 * @author Patrick Milano
 * @author Jhoan Esteban Osorno
 * @author Brian Smith
 * @version (1.0)
 */
public class RightPanel extends JPanel {
    
    protected static TrainCard[] trainCardsArray = TrainCard.values();
    protected static JLabel[] cards = new JLabel[9];
    protected static int[] occurrences = new int[9];
    private Font font = new Font("TimesRoman", Font.BOLD, 16);
    protected ImageIcon background = ResourceLoader.loadImage("rightBG.jpg");
    JLabel destinationLabel = new JLabel();
    JLabel tech = new JLabel();
    private Player player;
    private JLabel playerStuff;
    private ArrayList<DestinationCard> playerDestinationCards;
    private int destinationCardIndex;
    private ArrayList<Technology> playerTechCards;
    private int techCardIndex;
    
    /**
     * Draws and sets train cards, locomotives, player images, and how many points and how many trains they have.
     */
    public RightPanel() {
        setPreferredSize(new Dimension(356, 850));
        setLayout(new FlowLayout(FlowLayout.CENTER, 10, 20));
        
        for(int i = 0, length = trainCardsArray.length; i < length; i++) {
            TrainCard card = trainCardsArray[i];
            ImageIcon icon = card.getIcon();
            
            // Scale image down
            icon = new ImageIcon(icon.getImage().getScaledInstance(130, 73, 0));
            
            JLabel label = new JLabel(icon);
            label.setForeground(Color.YELLOW);
            label.setVerticalTextPosition(SwingConstants.TOP);
            label.setFont(font);
            label.setText("0");
            add(label);
            
            cards[i] = label;
        }
    }
    
    /**
     * Setups the right panel for the given player, setting the given player's train cards, locomotives, and image.
     */
    public void setup() {
        playerStuff = new JLabel(player.getIcon());
        playerStuff.setForeground(Color.YELLOW);
        playerStuff.setHorizontalTextPosition(SwingConstants.CENTER);
        playerStuff.setVerticalTextPosition(SwingConstants.BOTTOM);
        playerStuff.setFont(font);
        playerStuff.setText("Points: " + player.getPoints() + "\n   Number of trains: " + player.getNumTrains());
        destinationCardIndex = 0;
        add(playerStuff, 0);
        
        //tech.setPreferredSize(new Dimension(130, 100));
        tech.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        tech.addMouseListener(new Handler());
        tech.addMouseWheelListener(new Handler());
        add(tech);
        
        destinationLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        destinationLabel.addMouseListener(new Handler());
        destinationLabel.addMouseWheelListener(new Handler());
        add(destinationLabel);
    }
    
    /**
     * Paints the user's icon, destination cards (if there are any), technology cards (if there are any), and the
     * user's train cards.
     *
     * @param g instance of Graphics
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        background.paintIcon(this, g, 0, 0);
        
        playerStuff.setIcon(player.getIcon());
        playerStuff.setText("Points: " + player.getPoints() + ", # of Trains: " + player.getNumTrains());
        playerDestinationCards = player.getDestinationCards();
        playerTechCards = player.getTechnologies();
        
        if(!playerDestinationCards.isEmpty()) {
            ImageIcon icon = playerDestinationCards.get(destinationCardIndex).getIcon();
            destinationLabel.setIcon(icon);
        }
        
        if(playerTechCards.isEmpty()) {
            tech.setIcon(null);
        }
        else {
            ImageIcon icon = playerTechCards.get(techCardIndex).getIcon();
            icon = new ImageIcon(icon.getImage().getScaledInstance(150, 99, 0));
            tech.setIcon(icon);
        }
        
        for(int i = 0, length = cards.length; i < length; i++) {
            int number = Collections.frequency(player.getTrainCards(), trainCardsArray[i]);
            cards[i].setText(String.valueOf(number));
            occurrences[i] = number;
        }
    }
    
    /**
     * Sets the player that we are currently looking at
     *
     * @param player the player that we currently want to look at.
     */
    public void setPlayer(Player player) {
        this.player = player;
    }
    
    /**
     * Class to extend MouseAdapter, allowing us to use mouseClicked, mouseEntered, mouseExited, and mouseWheelMoved.
     */
    private class Handler extends MouseAdapter {
        
        /**
         * Allows the user to click through the destination cards.
         *
         * @param e instance of MouseEvent.
         */
        @Override
        public void mouseClicked(MouseEvent e) {
            if(e.getSource().equals(destinationLabel)) {
                if(e.getButton() == MouseEvent.BUTTON1) {
                    destinationCardIndex++;
                }
                if(e.getButton() == MouseEvent.BUTTON3) {
                    destinationCardIndex--;
                }
                if(destinationCardIndex >= playerDestinationCards.size() - 1) {
                    destinationCardIndex = 0;
                }
                if(destinationCardIndex < 0) {
                    destinationCardIndex = playerDestinationCards.size() - 1;
                }
            }
        }
        
        /**
         * Changes the mouse cursor to a hand when hovering over a destination card or technology card.
         *
         * @param e instance of MouseEvent
         */
        @Override
        public void mouseEntered(MouseEvent e) {
            setCursor(new Cursor(Cursor.HAND_CURSOR));
        }
        
        /**
         * Changes the mouse cursor back to a regular cursor after leaving a destination card or technology card.
         *
         * @param e instance of MouseEvent
         */
        @Override
        public void mouseExited(MouseEvent e) {
            setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        }
        
        /**
         * Allows the user to scroll through the destination cards or technology cards.
         *
         * @param e instance of mouseWheelEvent
         */
        @Override
        public void mouseWheelMoved(MouseWheelEvent e) {
            if(e.getSource().equals(destinationLabel)) {
                destinationCardIndex++;
                if(destinationCardIndex >= playerDestinationCards.size()) {
                    destinationCardIndex = 0;
                }
                if(destinationCardIndex < 0) {
                    destinationCardIndex = playerDestinationCards.size() - 1;
                }
            }
            else if(e.getSource().equals(tech)) {
                techCardIndex++;//if mouse is over technology card
                if(techCardIndex >= playerTechCards.size()) {
                    techCardIndex = 0;
                }
                if(destinationCardIndex < 0) {
                    techCardIndex = playerTechCards.size() - 1;
                }
            }
        }
    }
}
