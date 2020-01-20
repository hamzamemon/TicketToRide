import java.util.*;
import javax.swing.*;
/**
 * Class that runs the game. Runs through a players turn, and 
 * checks to see if the player has won and will tell which player
 * has won the game
 * 
 * @author Josh Dratler
 * @author Hamza Memon
 * @author Patrick Milano
 * @author Jhoan Esteban Osorno
 * @author Brian Smith
 * @version (1.0)
 */
public class GameLoop implements Runnable {

    protected static Player[] players;

    protected static LeftPanel leftPanel = new LeftPanel();
    protected static MiddlePanel middlePanel = new MiddlePanel();
    protected static RightPanel rightPanel = new RightPanel();
    protected static BottomPanel bottomPanel = new BottomPanel();

    static Queue<TrainCard> trainCardsDeck = new LinkedList<>();
    static Queue<TrainCard> discardedTrainCards = new LinkedList<>();

    private boolean playing = true;

    private Thread thread = new Thread(this);
    private Graph graph = new Graph();

    /**
     * Method to setup the board. Sets the players, shuffles the cards, 
     * and sets the panels to each given player. 
     */
    public void setup(){
        players = Setup.getPlayers();

        TrainCard[] trainCardsArray = TrainCard.values();

        for(int i = 0; i < 8; i++){
            trainCardsDeck.add(TrainCard.LOCOMOTIVE);
        }

        for(int i = 0; i < 9; i++){
            for(int j = 0; j < 12; j++){
                trainCardsDeck.add(trainCardsArray[i]);
            }
        }
        //removing the traincard the player has taken during setup
        for(Player player : players){
            TrainCard card = trainCardsDeck.remove();
            player.addTrainCard(card);
        }

        Collections.shuffle((List<?>) trainCardsDeck);
        //shuffles the train cards
        for(Player player : players){
            for(int i = 0; i < 4; i++){
                TrainCard card = trainCardsDeck.remove();
                player.addTrainCard(card);
            }
        }
        //sets all the panels to the proper player
        thread.start();
        rightPanel.setPlayer(players[0]);
        leftPanel.setPlayer(players[0]);
        bottomPanel.setPlayer(players[0]);
        Driver.buy.setPlayer(players[0]);
        BuyRoutePanel.setPlayer(players[0]);
        ConvertPanel.setPlayer(players[0]);
        graph.setPlayer(players[0]);
        rightPanel.setup();
        bottomPanel.setup();
    }

    /**
     * Method to run the game. Loop keeps going until the game ends.
     */
    @Override
    public void run(){
        while(playing){
            Player player = players[0];
            if(player.getNumTrains() <= 2){
                playing = false;
                //get the extra points for certain technology
                endOfTheGamePoints();
                // determine the winner
                Player winner = determineWinner();
                Driver.ending.setWinner(winner);
                Driver.ending.setup();

                Driver.cardLayout.show(Driver.cards, "Ending");

            }
            //if no one won
            //set the current player
            else
            {
                leftPanel.setPlayer(player);
                rightPanel.setPlayer(player);
                middlePanel.setPlayer(player);
                bottomPanel.setPlayer(player);
                Driver.buy.setPlayer(player);
                BuyRoutePanel.setPlayer(player);
                ConvertPanel.setPlayer(player);
                graph.setPlayer(player);
                //allows the player to play their turn
                makeTurn();
                graph.dfs();

                try{
                    Thread.sleep(100);
                }
                catch(InterruptedException e){
                    e.printStackTrace();
                }
                //next player
                Collections.rotate(Arrays.asList(players), - 1);
            }
        }
    }

    /**
     * Method to simulate a turn of a player 
     */
    private void makeTurn(){
        // while player has not done something for their turn
        // as in hasnt drawn a traincard, hasnt drawn a destination card,
        // and hasnt placed a train.
        while(!bottomPanel.hasPickedCards && !leftPanel.pickedDestCard
        && !middlePanel.placedTrain){
            if(bottomPanel.getClickCount() == 1){
                // hide the tech and dest buttons
                leftPanel.deckLabel.setVisible(false);
                bottomPanel.buyTech.setVisible(false);
                bottomPanel.endTurnEarly.setVisible(true);
                middlePanel.canHover = false;
            }
            //if there is an opportunity for a player to buy a second path
            if(BuyRoutePanel.secondPath > 0)
            {
                leftPanel.deckLabel.setVisible(false);    
                bottomPanel.buyTech.setVisible(false);
                bottomPanel.trainDeck.setVisible(false);
                bottomPanel.buyTech.setVisible(false);

                for (JLabel label: bottomPanel.trainCards)
                {
                    label.setVisible(false);
                }
            }

            leftPanel.repaint();
            rightPanel.repaint();
            bottomPanel.repaint();
            middlePanel.repaint();
        }
        //set the turn up for the next player
        resetTurn();
    }

    /**
     *  Method to calculate extra points added for a player having
     *  certain technology cards.  
     */
    private void endOfTheGamePoints(){
        for(Player player: players)
        {
            if(player.getTechnologies().contains(Technology.DOUBLE_HEADING)){
                // 2 points for every ticket that the player completed
                player.addPoints(2*player.getCompletedDestinations().size());
            }
            if(player.getTechnologies().contains(Technology.RISKY_CONTRACTS)){
                // figure out which player has the most points
                int max = 0;
                Player mostCompleted = null;
                for (int i = 0; i < players.length; i++)
                {
                    if (max < players[i].getCompletedDestinations().size())
                    {
                        max = players[i].getCompletedDestinations().size();
                        mostCompleted = players[i];
                    }
                }
                // if they player has the most completed destinations
                // they get 20 points if not lose 20 points
                if (mostCompleted.getNumber()== player.getNumber())
                    player.addPoints(20);
                else
                    player.addPoints(-20);
            }
            if(player.getTechnologies().contains(Technology.EQUALISING_BEAM)){
                // 15 points if player has longest route
                player.addPoints(15);
            }
            // if the player still has destinations card that they didnt
            // complete they will lose points
            if(player.getDestinationCards().size() > 0)
            {
                for (DestinationCard card: player.getDestinationCards())
                {
                    player.addPoints((-1)*card.getValue());
                }
            }
        }
    }

    /**
     * Method that goes through and determines which player has won the game.
     * (which is whichever player has the most points)
     * 
     * @return the winning player
     */
    private Player determineWinner(){
        int maxPoints = Integer.MIN_VALUE;
        Player winner = null;
        //go through every player and figure out which one has the most points
        for(Player player : players){
            if(maxPoints < player.getPoints()){
                maxPoints = player.getPoints();
                winner = player;
            }
        }
        return winner;
    }

    /**
     * Method that resets the turn so it can start fresh for the next player
     */
    protected void resetTurn(){
        bottomPanel.hasPickedCards = false;
        bottomPanel.endTurnEarly.setVisible(false);
        leftPanel.pickedDestCard = false;
        middlePanel.placedTrain = false;
        middlePanel.canHover = true;
        leftPanel.deckLabel.setVisible(true);
        bottomPanel.buyTech.setVisible(true);
        leftPanel.drawCards = false;
        bottomPanel.trainDeck.setVisible(true);
        leftPanel.instructions.setVisible(true);
        leftPanel.convertLoco.setVisible(true);
        for (int i = 0; i < bottomPanel.trainCards.length; i++)
        {
            bottomPanel.trainCards[i].setVisible(true);
        }
    }
}