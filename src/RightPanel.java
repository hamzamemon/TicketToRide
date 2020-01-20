import javax.swing.*;
import java.awt.*;
import java.util.Collections;
import java.awt.event.*;
import java.util.*;
/**
 * Class to create the Right Panel of the board. Right panel is where the users train cards, 
 * locomotives, image, destination cards, points, and technology cards are displayed. 
 * 
 * @author Josh Dratler
 * @author Hamza Memon
 * @author Patrick Milano
 * @author Jhoan Esteban Osorno
 * @author Brian Smith
 * @version (1.0)
 */
public class RightPanel extends JPanel {
    protected static TrainCard[] trainCardsArray; 
    protected static JLabel[] cards = new JLabel[9];

    private Player player;
    private JLabel playerStuff;
    private ArrayList<DestinationCard> playerDestCards;
    private int destCardIndex;
    protected static int[] occurrences = new int[9];
    protected boolean onHover;

    public Font font = new Font("TimesRoman", Font.BOLD, 16);

    JLabel dest = new JLabel();
    JLabel tech = new JLabel();

    protected ImageIcon background = ResourceLoader.loadImage("rightBG.jpg");

    private ArrayList<Technology> playerTechCards;
    private int techCardIndex;
    /**
     * Constructor for RightPanel. Draws and sets train cards, locomotives, player images, 
     * and tells the user how many points and how many trains they have.
     */
    public RightPanel(){
        setPreferredSize(new Dimension(356, 850));
        setLayout(new FlowLayout(FlowLayout.CENTER, 10, 20));

        trainCardsArray = TrainCard.values();

        for(int i = 0; i < trainCardsArray.length; i++){
            TrainCard card = trainCardsArray[i];
            ImageIcon icon = card.getIcon();
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
     * Method to "setup" the right panel for the given player. It sets the given players 
     * train cards, locomotives, and image.
     */
    public void setup(){
        playerStuff = new JLabel(player.getIcon());
        playerStuff.setForeground(Color.YELLOW);
        playerStuff.setHorizontalTextPosition(SwingConstants.CENTER);
        playerStuff.setVerticalTextPosition(SwingConstants.BOTTOM);
        playerStuff.setFont(font);
        playerStuff.setText("Points: " + player.getPoints() + "\n   Number of trains: " + player.getNumTrains());
        destCardIndex = 0;
        add(playerStuff, 0);

        //tech.setPreferredSize(new Dimension(130, 100));
        tech.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        tech.addMouseListener(new Handler());
        tech.addMouseWheelListener(new Handler());
        add(tech);

        dest.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        dest.addMouseListener(new Handler());
        dest.addMouseWheelListener(new Handler());
        add(dest);

    }

    /**
     * Override method of JPanel. Paints the users icon, destination cards (if there are any),
     * technology cards (if there are any), and the users train cards. 
     * 
     * @param g instance of Graphics 
     */
    @Override
    protected void paintComponent(Graphics g){
        super.paintComponent(g);

        background.paintIcon(this, g, 0, 0);

        //         playerStuff.setIcon(player.getIcon());
        //         playerStuff.setText("Points: " + player.getPoints() + "\n   Number of trains: " + player.getNumTrains());
        playerStuff.setIcon(player.getIcon());
        playerStuff.setText("Points: " + player.getPoints() + ", # of Trains: " + player.getNumTrains());
        playerDestCards = player.getDestinationCards();
        playerTechCards = player.getTechnologies();

        if(!playerDestCards.isEmpty()){
            ImageIcon icon = playerDestCards.get(destCardIndex).getIcon();
            dest.setIcon(icon);
        }

        if(!playerTechCards.isEmpty()){
            ImageIcon icon = playerTechCards.get(techCardIndex).getIcon();
            icon = new ImageIcon(icon.getImage().getScaledInstance(150, 99, 0));
            tech.setIcon(icon);
        }
        else
        {
            tech.setIcon(null);
        }

        for(int i = 0; i < cards.length; i++){
            int number = Collections.frequency(player.getTrainCards(), trainCardsArray[i]);
            cards[i].setText(String.valueOf(number));
            occurrences[i] = number;
        }

    }

    /**
     * Method to set the player that we are currently looking at
     * 
     * @param player the player that we currently want to look at.
     */
    public void setPlayer(Player player){
        this.player = player;
    }
    /**
     * Class to extend mouseAdapter so we can use mouseClicked, mouseEntered, mouseExited, and
     * mouseWheelMoved.
     */
    private class Handler extends MouseAdapter {
        /**
         * Override method of mouseClicked. Used in this panel to allow the user to click through
         * the destination cards. 
         * 
         * @param e instance of MouseEvent.
         */
        @Override
        public void mouseClicked(MouseEvent e){
            if(e.getSource().equals(dest)){
                if (e.getButton() == MouseEvent.BUTTON1)
                    destCardIndex++;
                if (e.getButton() == MouseEvent.BUTTON3)
                    destCardIndex--;
                if(destCardIndex >= playerDestCards.size()-1){
                    destCardIndex = 0;
                }
                if(destCardIndex < 0)
                    destCardIndex = playerDestCards.size() - 1;
            }
            else if(e.getSource().equals(tech)){
                //                 if (e.getButton() == MouseEvent.BUTTON1)
                //                     techCardIndex++;
                //                 if (e.getButton() == MouseEvent.BUTTON3)
                //                     techCardIndex--;
                //                 if(techCardIndex >= playerTechCards.size()-1){
                //                     techCardIndex = 0;
                //                 }
                //                 if(destCardIndex < 0)
                //                     techCardIndex = playerTechCards.size() - 1;
            }
        }

        /**
         * Override method of MouseAdapter. Used in this panel to change the mouse cursor to a hand when hovering 
         * over a destination card or technology card
         * 
         * @param e instance of MouseEvent
         */
        @Override
        public void mouseEntered(MouseEvent e){
            setCursor(new Cursor(Cursor.HAND_CURSOR));
        }

        /**
         * Override method of MouseAdapter. Used in this panel to change the mouse cursor back to a regular cursor
         * after leaving a destination card or technology card
         * 
         * @param e instance of MouseEvent
         */

        @Override
        public void mouseExited(MouseEvent e){
            setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        }

        /**
         * Method that overrides mouseWheelMoved. Allows the user to scroll through
         * the destination cards or technology card
         * 
         * @param e instance of mouseWheelEvent
         */
        @Override
        public void mouseWheelMoved(MouseWheelEvent e){
            if(e.getSource().equals(dest)){
                destCardIndex++;//if mouse is over destination card 
                if(destCardIndex >= playerDestCards.size()){
                    destCardIndex = 0;
                }
                if(destCardIndex < 0)
                    destCardIndex = playerDestCards.size() - 1;
            }
            else if(e.getSource().equals(tech)){
                techCardIndex++;//if mouse is over technology card
                if(techCardIndex >= playerTechCards.size()){
                    techCardIndex = 0;
                }
                if(destCardIndex < 0)
                    techCardIndex = playerTechCards.size() - 1;
            }
        }
    }
}