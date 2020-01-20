import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Arrays;
import java.util.LinkedList;
/**
 * Class that creates train bottom panel. Bottom panel is the panel that allows the user 
 * to draw a locomotive or draw a train card from the deck.
 * 
 * @author Josh Dratler
 * @author Hamza Memon
 * @author Patrick Milano
 * @author Jhoan Esteban Osorno
 * @author Brian Smith
 * @version (1.0)
 * 
 */
public class BottomPanel extends JPanel {

    private Player player;

    protected static JLabel trainDeck;
    protected static JLabel[] trainCards = new JLabel[5];
    protected static JLabel buyTech;

    protected static JLabel endTurnEarly;
    private TrainCard[] trainCardsList = new TrainCard[5];
    protected boolean hasPickedCards;
    private boolean pickedRegular;
    protected static int shuffled = 0;
    private int clickCount;
    private boolean lastClickOpenHand = false;
    private ImageIcon background = ResourceLoader.loadImage("bottomBG.jpg");
    /**
     * Constructor for bottom panel. Creates new panel on bottom of screen
     */
    public BottomPanel(){
        setPreferredSize(new Dimension(1280, 174));
        setLayout(new FlowLayout(FlowLayout.LEFT, 20, 20));
    }

    /**
     * Method to setup bottom panel. Deals out 5 train cards and one locomotive
     * from the deck for the user to choose from.
     */
    public void setup(){

        trainDeck = new JLabel(ResourceLoader.loadImage("train card.jpg"));
        trainDeck.addMouseListener(new Handler());
        add(trainDeck);

        for(int i = 0; i < trainCards.length; i++){
            TrainCard card = GameLoop.trainCardsDeck.remove();
            trainCardsList[i] = card;
            trainCards[i] = new JLabel(card.getIcon());
            trainCards[i].addMouseListener(new Handler());
            trainCards[i].addMouseMotionListener(new Handler());
            add(trainCards[i]);
        }
        buyTech = new JLabel();
        endTurnEarly = new JLabel();
        Icon buyTechIcon = ResourceLoader.loadImage("buyTechIcon.png");
        Icon endTurnEarlyIcon = ResourceLoader.loadImage("endTurnEarlyButton.jpg");
        buyTech.setIcon(buyTechIcon);
        endTurnEarly.setIcon(endTurnEarlyIcon);
        buyTech.addMouseListener(new Handler());
        endTurnEarly.addMouseListener(new Handler());
        add(endTurnEarly);
        endTurnEarly.setVisible(false);
        add(buyTech);
    }

    /**
     * Override method to paintComponent. Paints the background.
     * 
     * @param g instance of Graphics.
     */
    @Override
    protected void paintComponent(Graphics g){
        super.paintComponent(g);
        background.paintIcon(this, g, 0, 0);
    }

    /**
     * Method to set new player
     * 
     * @param player new player to be set.
     */
    public void setPlayer(Player player){
        this.player = player;
    }

    /**
     * Method to get the number of clicks the user did.
     * 
     * @return the number of clicks the user did
     */
    public int getClickCount(){
        return clickCount;
    }
    /**
     * Class to override the MouseAdapter class, so we can use 
     * mouseclicked,  mouseentered, and mouseexited.
     */
    private class Handler extends MouseAdapter {
        /**
         * Override of mouseClick method. We use this method to allow the user to click on a card
         * to draw it.
         * 
         * @param e MouseEvent instance
         */
        @Override
        public void mouseClicked(MouseEvent e){
            if(e.getSource().equals(buyTech)){
                Driver.cardLayout.show(Driver.cards, "BuyTechnologyPanel");
            }//show the buy technology panel
            if (e.getSource().equals(endTurnEarly))
            {//if user clicks to end the turn early
                clickCount = 0;
                lastClickOpenHand = false;
                hasPickedCards = true;
            }
            int numCardsCanDraw = 2;
            //if user clicked on the traindeck
            if(e.getSource().equals(trainDeck)){

                clickCount++;

                //reshuffle the deck
                if(GameLoop.trainCardsDeck.isEmpty()){
                    if(GameLoop.discardedTrainCards.isEmpty())
                    {
                        JFrame frame = new JFrame();
                        JOptionPane.showMessageDialog(frame, "There are no more cards to draw. Buy paths or technologies so that the discarded cards get reshuffled into the deck.");
                        return;
                    }
                    else
                    {
                        JFrame frame = new JFrame();
                        if (shuffled == 0)
                            JOptionPane.showMessageDialog(frame, "The deck ran out. The discarded cards were reshuffled and put back into the deck. The technology cards 'Risky Contracts' and 'Equalising Beam' are no longer available.");
                        else
                            JOptionPane.showMessageDialog(frame, "The deck ran out. The discarded cards were reshuffled and put back into the deck.");
                        shuffled++;
                        GameLoop.trainCardsDeck = new LinkedList<>(GameLoop.discardedTrainCards);
                        GameLoop.discardedTrainCards.clear();
                    }
                }

                TrainCard card = GameLoop.trainCardsDeck.remove();
                /*int index = Arrays.asList(RightPanel.trainCardsArray).indexOf(card);
                animate(card, index);*/
                player.addTrainCard(card);

                // handle a case where the player has water tenders
                if(player.getTechnologies().contains(Technology.WATER_TENDERS) && !lastClickOpenHand){
                    if(clickCount == 3){
                        clickCount = 0;
                        lastClickOpenHand = false;
                        hasPickedCards = true;
                    }
                    numCardsCanDraw = 3;
                }
                else
                {
                    if(clickCount == 2){
                        clickCount = 0;
                        lastClickOpenHand = false;
                        hasPickedCards = true;
                    }
                }
            }
             
            if(Arrays.asList(trainCards).contains(e.getSource())){
                lastClickOpenHand = true;
                int index = Arrays.asList(trainCards).indexOf(e.getSource());
                if(player.getTechnologies().contains(Technology.WATER_TENDERS) && clickCount == 2)
                {
                    JFrame frame = new JFrame();
                    JOptionPane.showMessageDialog(frame, "You must draw another card from the deck. You cannot draw a third card from the open hand.");
                    lastClickOpenHand = false;
                    return;
                }
                if(e.getSource().equals(trainCards[index])){
                    clickCount++;
                    //reshuffle the deck
                    if(GameLoop.trainCardsDeck.isEmpty()){
                        if(GameLoop.discardedTrainCards.isEmpty())
                        {
                            JFrame frame = new JFrame();
                            JOptionPane.showMessageDialog(frame, "There are no more cards to draw. Buy paths or technologies so that the discarded cards get reshuffled into the deck.");
                            return;
                        }
                        else
                        {
                            JFrame frame = new JFrame();
                            if (shuffled == 0)
                                JOptionPane.showMessageDialog(frame, "The deck ran out. The discarded cards were reshuffled and put back into the deck. The technology cards 'Risky Contracts' and 'Equalising Beam' are no longer available.");
                            else
                                JOptionPane.showMessageDialog(frame, "The deck ran out. The discarded cards were reshuffled and put back into the deck.");
                            shuffled++;
                            GameLoop.trainCardsDeck = new LinkedList<>(GameLoop.discardedTrainCards);
                            GameLoop.discardedTrainCards.clear();
                        }
                    }
                    if(clickCount == 1 && trainCardsList[index] == TrainCard.LOCOMOTIVE){
                        clickCount = 0;
                        lastClickOpenHand = false;
                        hasPickedCards = true;
                    }

                    if(clickCount == 2 && !pickedRegular && trainCardsList[index] == TrainCard.LOCOMOTIVE){
                        JFrame frame = new JFrame();
                        JOptionPane.showMessageDialog(frame, "You have already selected a train. You cannot select a locomotive.");
                        clickCount--;
                    }
                    else {
                        player.addTrainCard(trainCardsList[index]);
                        TrainCard card = GameLoop.trainCardsDeck.remove();
                        trainCardsList[index] = card;
                        trainCards[index].setIcon(card.getIcon());
                    }
                    if(clickCount == 2){
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
        public void mouseEntered(MouseEvent e){
            setCursor(new Cursor(Cursor.HAND_CURSOR));
        }

        /**
         * Method to change back to default cursor
         * 
         * @param e instance of MouseEvent
         */
        @Override
        public void mouseExited(MouseEvent e){
            setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        }
    }
    /**
     * Method used to (animate) flip the traincard or locomotive once its been clicked on
     * 
     * @param card the train card to flip
     * @param index the train card index to be flipped
     */
    private void animate(TrainCard card, int index){
        ImageIcon icon = card.getIcon();

        int width = icon.getIconWidth();
        int height = icon.getIconHeight();

        Graphics2D g2d = (Graphics2D) getGraphics();
        g2d.rotate(0, width / 2, height / 2);
        icon.paintIcon(this, g2d, 20, 20);
        //redraw next card
        int endX = RightPanel.cards[index].getX();
        int endY = RightPanel.cards[index].getY();

        int curX = 20;
        int curY = 20;

        for(int i = 0; i < 40; i++){
            curX += (endX - 20) / 40;
            curY += (endY - 20) / 40;

            icon.paintIcon(this, g2d, curX, curY);
             
            try{
                Thread.sleep(250 / 40);
            }

            catch(InterruptedException e){
                e.printStackTrace();
            }

            repaint();
        }
    }
}