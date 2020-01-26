import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.Arrays;
import java.util.List;

/**
 * Create the setup panel, where the user selects the number of players, chooses image for each player, selects each
 * player's color, and where each player gets to choose at least 3 destination cards.
 *
 * @author Josh Dratler
 * @author Hamza Memon
 * @author Patrick Milano
 * @author Jhoan Esteban Osorno
 * @author Brian Smith
 * @version (1.0)
 */
public class Setup extends JPanel {
    
    private static Player[] players;
    
    private ImageIcon background = ResourceLoader.loadImage("setup_background.jpg");
    
    private JButton twoPlayers = new JButton("Two Players");
    private JButton threePlayers = new JButton("Three Players");
    private JButton fourPlayers = new JButton("Four Players");
    private JLabel instructions = new JLabel();
    private JButton[] buttonArray = {twoPlayers, threePlayers, fourPlayers};
    
    /**
     * Constructs the setup panel. Buttons for number of players is initialized, and background is set
     */
    public Setup() {
        setLayout(new FlowLayout(FlowLayout.CENTER, 50, 512));
        setPreferredSize(new Dimension(1280, 1024));
        
        Font font = new Font("TimesRoman", Font.BOLD, 18);
        
        for(JButton label : buttonArray) {
            label.setForeground(Color.BLACK);
            label.addMouseListener(new Handler());
            label.setHorizontalTextPosition(JLabel.CENTER);
            label.setFont(font);
            add(label);
        }
        
        Icon instructionsIcon = ResourceLoader.loadImage("instructionsButton.png");
        instructions.setIcon(instructionsIcon);
        instructions.addMouseListener(new Handler());
        add(instructions);
    }
    
    /**
     * Retrieves the players array
     *
     * @return array of players.
     */
    public static Player[] getPlayers() {
        return players;
    }
    
    /**
     * Paints the background of the Ticket to Ride game box cover.
     *
     * @param g instance of Graphics
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        background.paintIcon(this, g, 0, 0);
    }
    
    /**
     * Allows us to extend mouseAdapter so mouseClicked, mouseEntered, and mouseExited can be used
     */
    private class Handler extends MouseAdapter {
        /**
         * Allows user to choose number of players, choose their image, and allows them to select their initial
         * destination cards.
         *
         * @param e instance of MouseEvent
         */
        @Override
        public void mouseClicked(MouseEvent e) {
            if(e.getSource().equals(instructions)) {
                Driver.cardLayout.next(Driver.cards);
            }
            else {
                instructions.setVisible(false);
                
                List<JButton> buttonList = Arrays.asList(buttonArray);
                int index = buttonList.indexOf(e.getSource());
                buttonList.forEach(i -> i.setVisible(false));
                
                File images = ResourceLoader.loadFile("images/playerImages/");
                File[] dir = images.listFiles();
                ImageIcon[] playerImagesArray = new ImageIcon[dir.length];
                
                for(int i = 0, length = playerImagesArray.length; i < length; i++) {
                    playerImagesArray[i] = new ImageIcon(dir[i].getAbsolutePath());
                }
                
                String[] possibleColors = {"BLUE", "RED", "GREEN", "BLACK"};
                JComboBox<String> trainBox = new JComboBox<>(possibleColors);
                JComboBox<ImageIcon> imageBox = new JComboBox<>(playerImagesArray);
                players = new Player[index + 2];
                
                for(int i = 0, playersLength = players.length; i < playersLength; i++) {
                    JOptionPane.showMessageDialog(null, imageBox, "Choose a picture", JOptionPane.INFORMATION_MESSAGE);
                    ImageIcon img = (ImageIcon) imageBox.getSelectedItem();
                    // Once user has selected an image, remove that image from the selection
                    imageBox.removeItem(img);
                    
                    JOptionPane.showMessageDialog(null, trainBox, "Choose a train color", JOptionPane.INFORMATION_MESSAGE);
                    String col = (String) trainBox.getSelectedItem();
                    //once a user has selected a color, remove that color from the selection
                    trainBox.removeItem(col);
                    
                    Color color = null;
                    try {
                        // Change color to written letters
                        color = (Color) Color.class.getField(col.toUpperCase()).get(null);
                    }
                    catch(NoSuchFieldException | IllegalAccessException e1) {
                        e1.printStackTrace();
                    }
                    
                    players[i] = new Player(i + 1, img, color);
                    
                    DestinationCard[] cardsArray = new DestinationCard[5];
                    for(int j = 0, length = cardsArray.length; j < length; j++) {
                        cardsArray[j] = LeftPanel.destinationCards.remove();
                    }
                    
                    ImageIcon[] imageArray = new ImageIcon[5];
                    for(int j = 0, length = imageArray.length; j < length; j++) {
                        imageArray[j] = cardsArray[j].getIcon();
                    }
                    
                    JComboBox<ImageIcon> destinationCardBox = new JComboBox<>(imageArray);
                    boolean choosing = true;
                    int cardsAdded = 0;
                    
                    while(choosing) {
                        // While the player is still choosing destination cards
                        JOptionPane.showMessageDialog(null, destinationCardBox, "Choose a destination card", JOptionPane.INFORMATION_MESSAGE);
                        
                        ImageIcon icon = (ImageIcon) destinationCardBox.getSelectedItem();
                        int destinationCardIndex = Arrays.asList(imageArray).indexOf(icon);
                        
                        DestinationCard card1 = cardsArray[destinationCardIndex];
                        players[i].addDestinationCard(card1);
                        
                        cardsAdded++;
                        destinationCardBox.removeItem(icon);
                        
                        if(cardsAdded == 5) {
                            // Can't have more than 5 destination cards
                            choosing = false;
                        }
                        else if(cardsAdded >= 3) {
                            // Player needs at least 3 cards, so now give them the option to not add anymore cards
                            int response = JOptionPane.showConfirmDialog(null, "Keep adding cards?", "Proceed with current hand?", JOptionPane.YES_NO_OPTION);
                            if(response == JOptionPane.NO_OPTION) {
                                // If player doesn't want to add anymore cards
                                choosing = false;
                            }
                        }
                    }
                    
                }
                
                setVisible(false);
                Driver.loop.setup();
                Driver.buy.setup();
                Driver.cardLayout.show(Driver.cards, "Container");
            }
        }
        
        /**
         * Used in this class to change to hand cursor when hovering over number of players
         *
         * @param e instance from a mouse event
         */
        @Override
        public void mouseEntered(MouseEvent e) {
            setCursor(new Cursor(Cursor.HAND_CURSOR));
        }
        
        /**
         * Used in this class to change back to regular cursor when done hovering over the number of players.
         *
         * @param e instance from mouse event
         */
        @Override
        public void mouseExited(MouseEvent e) {
            setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        }
    }
}
