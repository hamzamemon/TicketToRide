import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.Arrays;
import java.awt.event.*;
import java.util.Queue;
import java.util.Collections;
import java.util.LinkedList;
/**
 * Class to create Setup Panel. Setup panel is where the user selects the number of players, 
 * chooses each image for each player, selects each players color, and where each player gets 
 * to choose at least 3 destination cards.
 * 
 * @author Josh Dratler
 * @author Hamza Memon
 * @author Patrick Milano
 * @author Jhoan Esteban Osorno
 * @author Brian Smith
 * @version (1.0) 
 * 
 */
public class Setup extends JPanel {

    private static Player[] players;

    private ImageIcon background;

    private JButton twoPlayers = new JButton("Two Players");
    private JButton threePlayers = new JButton("Three Players");
    private JButton fourPlayers = new JButton("Four Players");
    private JLabel instructions = new JLabel();
    private JButton[] buttonArray = {twoPlayers, threePlayers, fourPlayers};

    private Queue<DestinationCard> destCards = new LinkedList<>();

    private JLabel[] destCardsChoices = new JLabel[3];
    private JButton confirmDestCards = new JButton("Proceed with your selected cards?");
    /**
     * Constructor for setup panel. Buttons for number of players is initialized, and 
     * background is set
     * 
     */
    public Setup(){
        setLayout(new FlowLayout(FlowLayout.CENTER, 50, 512));
        setPreferredSize(new Dimension(1280, 1024));

        background = ResourceLoader.loadImage("setup_background.jpg");

        Font font = new Font("TimeRoman", Font.BOLD, 18);

        for(JButton label : buttonArray){
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
     * Override method of JPanel. Paints the background the ticket to ride game box cover.
     * 
     * @param g instance of Graphics
     */
    @Override
    protected void paintComponent(Graphics g){
        super.paintComponent(g);
        background.paintIcon(this, g, 0, 0);
    }
    /**
     * Class that allows us to extend mouseAdapter so mouseClicked, 
     * mouseEntered, and mouseExited can be used
     */
    private class Handler extends MouseAdapter
    {
        /**
         * Override of a mouse adapter method. Allows user to choose
         * number of players, choose their image, and allows them to 
         * select their initial destination cards.
         * 
         * @param e instance of MouseEvent
         */
        @Override
        public void mouseClicked(MouseEvent e){
            if (e.getSource().equals(instructions))
            {//if user clicked on instructions
                Driver.cardLayout.next(Driver.cards);
            }
            else
            {
                instructions.setVisible(false);
                int index = Arrays.asList(buttonArray).indexOf(e.getSource());

                Arrays.asList(buttonArray).forEach((i) -> i.setVisible(false));

                File images = ResourceLoader.loadFile("images/playerImages/");
                File[] dir = images.listFiles();
                ImageIcon[] imageArray = new ImageIcon[dir.length];

                for(int i = 0; i < imageArray.length; i++){
                    imageArray[i] = new ImageIcon(dir[i].getAbsolutePath());
                }

                String[] possibleColors = {"BLUE", "RED", "GREEN", "BLACK"};
                JComboBox<String> trainBox = new JComboBox<>(possibleColors);
                JComboBox<ImageIcon> imageBox = new JComboBox<>(imageArray);
                //Color col1 = null;
                players = new Player[index + 2];

                for(int i = 0; i < players.length; i++){
                    JOptionPane.showMessageDialog(null, imageBox, "Choose a picture", JOptionPane.INFORMATION_MESSAGE);
                    ImageIcon img = (ImageIcon) imageBox.getSelectedItem();
                    imageBox.removeItem(img);//once user has selected an image, remove that image from the selection

                    JOptionPane.showMessageDialog(null, trainBox, "Choose a train color", JOptionPane.INFORMATION_MESSAGE);
                    String col = (String) trainBox.getSelectedItem();
                    trainBox.removeItem(col);//once a user has selected a color, remove that color from the selection
                    Color color = null;
                    try
                    {
                        //change color to written letters
                        color = (Color) Color.class.getField(col.toUpperCase()).get(null);
                    }
                    catch (NoSuchFieldException | IllegalAccessException e1)
                    {
                        e1.printStackTrace();
                    }

                    players[i] = new Player(i + 1, img, color);

                    DestinationCard[] cardsArray = new DestinationCard[5];
                    for(int j = 0; j < cardsArray.length; j++){
                        cardsArray[j] = LeftPanel.destCards.remove();
                    }
                    ImageIcon[] limageArray = new ImageIcon[5];
                    for(int j = 0; j < limageArray.length; j++){
                        limageArray[j] = cardsArray[j].getIcon();
                    }
                    JComboBox<ImageIcon> destCardBox = new JComboBox<>(limageArray);
                    //selection boxes for images for user to select.
                    boolean choosing = true;
                    int cardsAdded = 0;

                    while(choosing){//while the player is still choosing destination cards
                        JOptionPane.showMessageDialog(null, destCardBox, "Choose a destination card", JOptionPane.INFORMATION_MESSAGE);

                        ImageIcon icon = (ImageIcon) destCardBox.getSelectedItem();
                        int limdex = Arrays.asList(limageArray).indexOf(icon);

                        DestinationCard card1 = cardsArray[limdex];
                        players[i].addDestCard(card1);

                        cardsAdded++;
                        destCardBox.removeItem(icon);

                        if(cardsAdded == 5){//cant have more than 5 destination cards
                            choosing = false;
                        }
                        else if(cardsAdded >= 3){//player needs at least 3 cards, so now give them the option to not add anymore cards
                            int response = JOptionPane.showConfirmDialog(null, "Keep adding cards?", "Proceed with current hand?", JOptionPane.YES_NO_OPTION);
                            if(response == JOptionPane.NO_OPTION){
                                choosing = false; //if player doesnt want to add anymore cards
                            }
                        }
                    }

                }
                setVisible(false);
                Driver.loop.setup();
                Driver.buy.setup();

                Driver.cardLayout.show(Driver.cards, "Container");
            }
            ////Driver.loop.run();
        }

        /**
         * Override method of mouselistener. Used in this class to change 
         * to hand cursor when hovering over number of players
         * 
         * @param e instance from a mouse event
         */
        @Override
        public void mouseEntered(MouseEvent e){
            setCursor(new Cursor(Cursor.HAND_CURSOR));
        }

        /**
         * Override method of mouselistener. Used in this class to change
         * back to regular cursor when done hovering over the number of players.
         * 
         * 
         * @param e instance from mouse event 
         */
        @Override
        public void mouseExited(MouseEvent e){
            setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        }
    }
    /**
     * Method to get the players array
     * 
     * @return array of players.
     */
    public static Player[] getPlayers(){
        return players;
    }
}