import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Queue;
/**
 * Class to create left panel. Left panel contains a button to get to the instructions, 
 * button to convert a train to a locomotive, and a card deck to click on where a player
 * may click on a destination cards to claim for their turn.
 *  @author Josh Dratler
 * @author Hamza Memon
 * @author Patrick Milano
 * @author Jhoan Esteban Osorno
 * @author Brian Smith
 * @version (1.0)
 * 
 */
public class LeftPanel extends JPanel {

    protected boolean pickedDestCard;
    protected static JLabel deckLabel;
    protected boolean drawCards;

    protected static Queue<DestinationCard> destCards = new LinkedList<>();
    private DestinationCard[] cardsArray = new DestinationCard[3];

    private JLabel[] destCardsChoices = new JLabel[3];
    private JButton confirmDestCards = new JButton("Confirm choices?");
    protected JLabel instructions = new JLabel();
    protected JLabel convertLoco = new JLabel();

    private boolean[] selected = new boolean[3];

    private Player player;
    private Border border;

    private ImageIcon background = ResourceLoader.loadImage("leftBG.jpg");
    /**
     * Constructor to make new instance of LeftPanel. Sets up the destination cards, instruction button,
     * convert trains to locomotive button, and shuffles the deck. Also creates a panel on the left of the screen
     */
    public LeftPanel(){
        setPreferredSize(new Dimension(356, 850));
        setLayout(new FlowLayout(FlowLayout.CENTER, 15, 20));

        DestinationCard[] destinationCard = DestinationCard.values();
        Collections.addAll(destCards, destinationCard);

        Collections.shuffle((java.util.List<?>) destCards);

        ImageIcon deck_img = ResourceLoader.loadImage("destination card.jpg");
        //adds destination card
        Icon convertLocoIcon = ResourceLoader.loadImage("convertTrainsToLocomotive.jpg");
        convertLoco.setIcon(convertLocoIcon);//adds convert trains to locomotive button
        convertLoco.addMouseListener(new Handler());
        add(convertLoco);

        Icon instructionsIcon = ResourceLoader.loadImage("instructionsButton.png");
        instructions.setIcon(instructionsIcon);//add instructions button
        instructions.addMouseListener(new Handler());
        add(instructions); 

        deckLabel = new JLabel(deck_img);
        deckLabel.addMouseListener(new Handler());
        add(deckLabel);

        for(int i = 0; i < cardsArray.length; i++){
            cardsArray[i] = destCards.remove();
        }

        for(int i = 0; i < destCardsChoices.length; i++){
            destCardsChoices[i] = new JLabel(cardsArray[i].getIcon());
            destCardsChoices[i].setVisible(false);
            destCardsChoices[i].addMouseListener(new Handler());
            add(destCardsChoices[i]);
        }

        border = destCardsChoices[0].getBorder();

        confirmDestCards.setVisible(false);
        confirmDestCards.addActionListener(new Handler());
        add(confirmDestCards);

    }

    /**
     * Method to override JPanel paint component. Paints the background.
     * 
     * @param g instance of Graphics class
     */
    @Override
    protected void paintComponent(Graphics g){
        super.paintComponent(g);
        background.paintIcon(this, g, 0, 0);
    }

    /**
     * Method to set the current player
     * 
     * @param new player to be set
     */
    public void setPlayer(Player player){
        this.player = player;
    }

    private class Handler extends MouseAdapter implements ActionListener {
        /**
         * Override method from mouselistener. Used in this class to 
         * either select instructions, select to convert trains to locomotive,
         * or to click to draw a destination card
         * 
         * @param e instance of mouseevent object. 
         */
        @Override
        public void mouseClicked(MouseEvent e){
            if (e.getSource().equals(instructions))
            {//if player clicked on instructions
                Driver.cardLayout.next(Driver.cards);
            }
            else if (e.getSource().equals(convertLoco))
            {//if player clicked to convert to locomotive
                Driver.cardLayout.show(Driver.cards, "ConvertPanel");
            }
            else
            {   //if player clicked on destination card icon
                if(e.getSource().equals(deckLabel)){
                    deckLabel.setVisible(false);
                    drawCards = true;
                    confirmDestCards.setVisible(!confirmDestCards.isVisible());
                    BottomPanel.buyTech.setVisible(false);
                    instructions.setVisible(false);
                    convertLoco.setVisible(false);
                    MiddlePanel.canHover = !MiddlePanel.canHover;
                    BottomPanel.trainDeck.setVisible(!BottomPanel.trainDeck.isVisible());
                    for (int i = 0; i < BottomPanel.trainCards.length; i++)
                    {
                        BottomPanel.trainCards[i].setVisible(!BottomPanel.trainCards[i].isVisible());
                    }
                    for(JLabel choice : destCardsChoices){
                        choice.setVisible(!choice.isVisible());
                    }
                }
                else {
                    drawCards = true;
                    JLabel label = (JLabel) e.getSource();
                    int index = Arrays.asList(destCardsChoices).indexOf(label);

                    if(selected[index]){
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
         * Override method of actionPerformed. Used here to find the three destination cards to display
         * 
         * @param e instance of ActionEvent
         */
        @Override
        public void actionPerformed(ActionEvent e){
            if(selected[0] || selected[1] || selected[2])
            {
                for(JLabel label : destCardsChoices){
                    label.setBorder(border);
                }

                for(int i = 0; i < 3; i++){
                    if(selected[i]){
                        selected[i] = false;
                        destCards.remove(cardsArray[i]);
                        player.addDestCard(cardsArray[i]);
                    }

                    else {
                        destCards.add(cardsArray[i]);
                    }

                    if(destCards.isEmpty()){//if there are no destination cards
                        destCardsChoices[i].setIcon(null);
                    }

                    else {
                        cardsArray[i] = destCards.remove();
                        destCardsChoices[i].setIcon(cardsArray[i].getIcon());
                    }
                }

                pickedDestCard = true;
                confirmDestCards.setVisible(false);
                Arrays.asList(destCardsChoices).forEach((i) -> i.setVisible(false));
            }
        }

        /**
         * Override method of mouselistener. Used in this class to change 
         * to hand cursor
         * 
         * @param e instance from a mouse event
         */
        @Override
        public void mouseEntered(MouseEvent e){
            setCursor(new Cursor(Cursor.HAND_CURSOR));
        }

        /**
         * Override method of mouselistener. Used in this class to change
         * back to regular
         * 
         * 
         * @param e instance from mouse event 
         */
        @Override
        public void mouseExited(MouseEvent e){
            setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        }

    }
}