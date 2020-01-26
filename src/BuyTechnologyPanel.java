import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Technologies change the way that points are added to the players, the ways routes are purchased, the way
 * locomotives are purchased, and they also unlock routes that were previously unavailable to the player.
 * <p>
 * To buy a technology, the player clicks on the Buy Technology button that is on the BottomPanel. The currency for
 * technologies is paid in locomotive cards, or an appropriate amount of regular train cards that the player can use
 * as well.
 * <p>
 * When a technology is purchased, the cards used as currency are removed from the player's hand and in most cases,
 * added to the discarded pile, Then, the player obtains the technology, it remains in their hand for the rest of the
 * game unless otherwise specified.
 */
public class BuyTechnologyPanel extends JPanel {
    
    private Player player;
    private JLabel playerStuffLabel = new JLabel();
    
    private Technology[] techs = Technology.values();
    
    private ImageIcon backIcon = ResourceLoader.loadImage("back_arrow.png");
    private JLabel back_arrow = new JLabel(backIcon);
    
    private JLabel[] labels = new JLabel[techs.length];
    //background
    private ImageIcon shelves = ResourceLoader.loadImage("shelves.jpg");
    
    /**
     * Initializes the fields of BuyTechnologyPanel, and also constructs the panel itself.
     * Makes the panel visible.
     */
    public BuyTechnologyPanel() {
        setPreferredSize(new Dimension(1280, 1024));
        setLayout(new BorderLayout());
        
        back_arrow.addMouseListener(new Handler());
        add(back_arrow, BorderLayout.LINE_START);
        
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 90));
        panel.setPreferredSize(new Dimension(900, 900));
        panel.setOpaque(false);
        
        for(int i = 0, length = techs.length; i < length; i++) {
            Technology tech = techs[i];
            
            labels[i] = new JLabel(tech.getIcon());
            labels[i].addMouseListener(new Handler());
            panel.add(labels[i]);
        }
        
        add(panel, BorderLayout.CENTER);
    }
    
    /**
     * Makes the structure of the panel and assists with initialization of the JPanel.
     */
    public void setup() {
        playerStuffLabel.setHorizontalTextPosition(SwingConstants.CENTER);
        playerStuffLabel.setVerticalTextPosition(SwingConstants.BOTTOM);
        
        JPanel playerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 90));
        playerPanel.setPreferredSize(new Dimension(200, 1024));
        
        playerPanel.setOpaque(false);
        playerPanel.add(playerStuffLabel);
        add(playerPanel, BorderLayout.EAST);
    }
    
    /**
     * Display and update the display of the technology cards as they are purchased.
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        shelves.paintIcon(this, g, 0, 0);
        
        int numLoco = Collections.frequency(player.getTrainCards(), TrainCard.LOCOMOTIVE);
        playerStuffLabel.setIcon(player.getIcon());
        playerStuffLabel.setText("Number of locomotives: " + numLoco);
        
        ArrayList<Technology> playerTechs = player.getTechnologies();
        // Prints the number of train cards available for each color
        for(int i = 0, length = labels.length; i < length; i++) {
            if(playerTechs.contains(techs[i]) || techs[i].getCost() > numLoco || techs[i].getQuantity() == 0) {
                labels[i].setVisible(false);
            }
        }
        // If the deck has been shuffled, then Risky Contracts and Equalising Beam are removed from availability if
        // they have not yet been purchased
        if(BottomPanel.shuffled > 0) {
            labels[9].setVisible(false);
            labels[4].setVisible(false);
        }
    }
    
    /**
     * Sets the current player
     */
    public void setPlayer(Player player) {
        this.player = player;
    }
    
    /**
     * Handles all MouseEvents. MouseAdapter is used in order to selectively override the methods of MouseListener.
     */
    private class Handler extends MouseAdapter {
        
        /**
         * Handles clicking on various objects on the current panel, including the back arrow, any trainCard, and the
         * confirmPurchase button. Also covers the logic of decrementing and incrementing respective quantities when
         * trainCards and RightPanel.occurrences are clicked on.
         */
        @Override
        public void mouseClicked(MouseEvent e) {
            if(e.getSource().equals(back_arrow)) {
                // Of clicking on the back arrow, escape this JPanel and return to the game board screen
                for(JLabel label : labels) {
                    label.setVisible(true);
                }
            }
            else {
                for(Technology tech : techs) {
                    JLabel label = (JLabel) e.getSource();
                    if(label.getIcon().equals(tech.getIcon())) {
                        player.addTechnologyCard(tech);
                        for(int i = 0; i < tech.getCost(); i++) {
                            player.getTrainCards().remove(TrainCard.LOCOMOTIVE);
                            
                            if(ConvertPanel.tradedLocomotives < RightPanel.occurrences[0]) {
                                GameLoop.discardedTrainCards.add(TrainCard.LOCOMOTIVE);
                            }
                            else {
                                ConvertPanel.tradedLocomotives--;
                            }
                            
                            // Occurrences deals with cards that we have selected for purchase
                            RightPanel.occurrences[0]--;
                        }
                        
                        tech.subtractOne();
                        break;
                    }
                }
                
                BottomPanel.buyTech.setVisible(false);
            }
            
            Driver.cardLayout.show(Driver.cards, "Container");
        }
        
        /**
         * Make a hand cursor appear when the mouse hovers over a clickable object.
         *
         * @param e MouseEvent instance
         */
        @Override
        public void mouseEntered(MouseEvent e) {
            setCursor(new Cursor(Cursor.HAND_CURSOR));
        }
        
        /**
         * Make the regular cursor appear when the mouse is not hovering over a clickable object.
         *
         * @param e MouseEvent instance
         */
        @Override
        public void mouseExited(MouseEvent e) {
            setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        }
    }
}
