import javax.swing.JLabel;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * Simulation that runs the game by running through a player's turn, and checks to see if the player has won.
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
    public void setup() {
        players = Setup.getPlayers();
        
        TrainCard[] trainCardsArray = TrainCard.values();
        
        for(int i = 0; i < 8; i++) {
            trainCardsDeck.add(TrainCard.LOCOMOTIVE);
        }
        
        for(int i = 0; i < 9; i++) {
            for(int j = 0; j < 12; j++) {
                trainCardsDeck.add(trainCardsArray[i]);
            }
        }
        
        // Removing the Train Card the player has taken during setup
        for(Player player : players) {
            TrainCard card = trainCardsDeck.remove();
            player.addTrainCard(card);
        }
        
        // Shuffles the train cards
        Collections.shuffle((List<?>) trainCardsDeck);
        
        for(Player player : players) {
            for(int i = 0; i < 4; i++) {
                TrainCard card = trainCardsDeck.remove();
                player.addTrainCard(card);
            }
        }
        
        // Sets all the panels to the proper player
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
     * Loop keeps going until the game ends.
     */
    @Override
    public void run() {
        while(playing) {
            Player player = players[0];
            if(player.getNumTrains() <= 2) {
                playing = false;
                
                //get the extra points for certain technology
                endOfTheGamePoints();
                
                // determine the winner
                Player winner = determineWinner();
                Driver.ending.setWinner(winner);
                Driver.ending.setup();
                
                Driver.cardLayout.show(Driver.cards, "Ending");
                
            }
            // If no one won, sets the current player for all Panels
            else {
                leftPanel.setPlayer(player);
                rightPanel.setPlayer(player);
                middlePanel.setPlayer(player);
                bottomPanel.setPlayer(player);
                Driver.buy.setPlayer(player);
                BuyRoutePanel.setPlayer(player);
                ConvertPanel.setPlayer(player);
                graph.setPlayer(player);
                makeTurn();
                graph.dfs();
                
                try {
                    Thread.sleep(100);
                }
                catch(InterruptedException e) {
                    e.printStackTrace();
                }
                
                // Next player
                Collections.rotate(Arrays.asList(players), -1);
            }
        }
    }
    
    /**
     * Simulates a turn for a player
     */
    private void makeTurn() {
        // While player has not done something for their turn
        while(!bottomPanel.hasPickedCards && !leftPanel.pickedDestinationCard && !middlePanel.isPlacedTrain()) {
            if(bottomPanel.getClickCount() == 1) {
                // Hides the tech and destination buttons
                leftPanel.deckLabel.setVisible(false);
                bottomPanel.buyTech.setVisible(false);
                bottomPanel.endTurnEarly.setVisible(true);
                middlePanel.canHover = false;
            }
            // If there is an opportunity for a player to buy a second path
            if(BuyRoutePanel.secondPath > 0) {
                leftPanel.deckLabel.setVisible(false);
                bottomPanel.buyTech.setVisible(false);
                bottomPanel.trainDeck.setVisible(false);
                bottomPanel.buyTech.setVisible(false);
                
                for(JLabel label : bottomPanel.trainCardsLabel) {
                    label.setVisible(false);
                }
            }
            
            leftPanel.repaint();
            rightPanel.repaint();
            bottomPanel.repaint();
            middlePanel.repaint();
        }
        
        // Set the turn up for the next player
        resetTurn();
    }
    
    /**
     * Calculates extra points added for a player having certain technology cards.
     */
    private void endOfTheGamePoints() {
        for(Player player : players) {
            ArrayList<Technology> playerTechnologies = player.getTechnologies();
            
            if(playerTechnologies.contains(Technology.DOUBLE_HEADING)) {
                // 2 points for every ticket that the player completed
                player.addPoints(2 * player.getCompletedDestinations().size());
            }
            if(playerTechnologies.contains(Technology.RISKY_CONTRACTS)) {
                // Figure out which player has the most points
                int max = 0;
                Player mostCompleted = null;
                for(Player player1 : players) {
                    ArrayList<DestinationCard> playerCompletedDestinations = player1.getCompletedDestinations();
                    if(max < playerCompletedDestinations.size()) {
                        max = playerCompletedDestinations.size();
                        mostCompleted = player1;
                    }
                }
                
                // If they player has the most completed destinations, they get 20 points but if not, lose 20 points
                if(mostCompleted.getNumber() == player.getNumber()) {
                    player.addPoints(20);
                }
                else {
                    player.addPoints(-20);
                }
            }
            if(playerTechnologies.contains(Technology.EQUALISING_BEAM)) {
                // 15 points if player has longest route
                player.addPoints(15);
            }
            // If the player still has destinations card that they didn't complete, they will lose points
            ArrayList<DestinationCard> playerDestinationCards = player.getDestinationCards();
            if(!playerDestinationCards.isEmpty()) {
                for(DestinationCard card : playerDestinationCards) {
                    player.addPoints(-1 * card.getValue());
                }
            }
        }
    }
    
    /**
     * Determines which player has won the game (which is whichever player has the most points).
     *
     * @return the winning player
     */
    private Player determineWinner() {
        int maxPoints = Integer.MIN_VALUE;
        Player winner = null;
        // Go through every player and figure out which one has the most points
        for(Player player : players) {
            if(maxPoints < player.getPoints()) {
                maxPoints = player.getPoints();
                winner = player;
            }
        }
        
        return winner;
    }
    
    /**
     * Resets the turn so it can start fresh for the next player
     */
    protected void resetTurn() {
        bottomPanel.hasPickedCards = false;
        bottomPanel.endTurnEarly.setVisible(false);
        leftPanel.pickedDestinationCard = false;
        middlePanel.setPlacedTrain(false);
        middlePanel.canHover = true;
        leftPanel.deckLabel.setVisible(true);
        bottomPanel.buyTech.setVisible(true);
        leftPanel.setDrawCards(false);
        bottomPanel.trainDeck.setVisible(true);
        leftPanel.instructions.setVisible(true);
        leftPanel.convertLoco.setVisible(true);
        for(int i = 0, length = bottomPanel.trainCardsLabel.length; i < length; i++) {
            bottomPanel.trainCardsLabel[i].setVisible(true);
        }
    }
}
