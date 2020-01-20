import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Collections;
/**
 * Panel that displays the winners of the game. The winner is displayed at the top, and
 * the user may cycle through the rest of the players and view their stats
 * 
 * @author Jhoan Osorno
 * @author Pat Milano
 * @author Brian Smith
 * @author Hamza Memon
 * @author Josh Dratler
 * @version 1.0
 */
public class EndingPanel extends JPanel
{
    private JLabel winnerResults;    
    private Player winner; 
    public Font font = new Font("TimesRoman", Font.BOLD, 16);    
    private JLabel playerResults;
    private ArrayList<Player> otherPlayers = new ArrayList<Player>();
    private int playerLimdex;
    
    private JLabel backArrow;
    private JLabel forwardArrow;
    
    private ImageIcon background;
    private ImageIcon firstPlace;   
    private ImageIcon secondPlace;    
    private ImageIcon thirdPlace;   
    private ImageIcon fourthPlace;
    
    private ImageIcon[] places;
    /**
     * Constructor for the EndingPanel object, sets the dimensions of the Panel and retrieves 
     * the arrow assets
     */
    public EndingPanel()
    {       
        setPreferredSize(new Dimension(1280, 1024));
        setLayout(new BorderLayout());
        backArrow = new JLabel();
        Icon backArrowIcon = ResourceLoader.loadImage("back_arrow.png");
        backArrow.setIcon(backArrowIcon);
        backArrow.addMouseListener(new Handler());
        add(backArrow, BorderLayout.WEST);

        forwardArrow = new JLabel();
        Icon forwardArrowIcon = ResourceLoader.loadImage("forward_arrow.png");
        forwardArrow.setIcon(forwardArrowIcon);
        forwardArrow.addMouseListener(new Handler());
        add(forwardArrow, BorderLayout.EAST);
        
        background = ResourceLoader.loadImage("ending_background.jpg");
        firstPlace = ResourceLoader.loadImage("1stplace.png");
        secondPlace = ResourceLoader.loadImage("2ndplace.png");
        thirdPlace = ResourceLoader.loadImage("3rdplace.png");
        fourthPlace = ResourceLoader.loadImage("4thplace.png");
    }

    /**
     * Method called when the EndingPanel is ready to be displayed, that decides who is the winner
     * and who are the other players of the game
     */
    public void setup() {
        System.out.println("Winner: " + winner.getNumber());
        
        winnerResults = new JLabel(winner.getIcon());
        winnerResults.setForeground(Color.YELLOW);
        winnerResults.setHorizontalTextPosition(SwingConstants.CENTER);
        winnerResults.setVerticalTextPosition(SwingConstants.BOTTOM);
        winnerResults.setFont(font);
        winnerResults.setOpaque(false);
        add(winnerResults, BorderLayout.NORTH);
        
        places = new ImageIcon[3];
        places[0] = secondPlace;
        places[1] = thirdPlace;
        places[2] = fourthPlace;
        for (int i = 0; i < GameLoop.players.length; i++) {
            if (GameLoop.players[i] != this.winner) {
                otherPlayers.add(GameLoop.players[i]);
            }
        }
        for (int i = 0; i < otherPlayers.size()-1; i++) {
            if (otherPlayers.get(i).getPoints() < otherPlayers.get(i + 1).getPoints()) {
                Collections.swap(otherPlayers,i,i+1);
            }
        }
        playerResults = new JLabel(otherPlayers.get(0).getIcon());
        playerResults.setForeground(Color.BLACK);
        playerResults.setHorizontalTextPosition(SwingConstants.CENTER);
        playerResults.setVerticalTextPosition(SwingConstants.BOTTOM);
        playerResults.setFont(font);
        playerResults.setOpaque(false);
        add(playerResults, BorderLayout.CENTER);
        playerLimdex = 0;
    }

    /**
     * Method that handles the painting of the panel. Shows the icons of the players involved, and
     * the information regarding their stats by the end of the game
     * @param g the Graphics object that handles painting
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        background.paintIcon(this,g,0,0);
        firstPlace.paintIcon(this,g,610,180);
        winnerResults.setIcon(winner.getIcon());
        winnerResults.setText("<html>Points: " + winner.getPoints() + 
                "<br/> Number of trains: " + winner.getNumTrains() +
                "<br/> Number of routes claimed: " + winner.getRoutesOwned().size() + "</html>");
                
        playerResults.setIcon(otherPlayers.get(playerLimdex).getIcon());
        playerResults.setText("<html>Points: " + otherPlayers.get(playerLimdex).getPoints() + 
         "<br/>   Number of trains: " + otherPlayers.get(playerLimdex).getNumTrains()+
         "<br/> Number of routes claimed: " + otherPlayers.get(playerLimdex).getRoutesOwned().size()+"</html>");
         
        places[playerLimdex].paintIcon(this,g,610,680);
    }

    /**
     * Setter method for the winner of the game
     * @param player The winner of the game
     */
    public void setWinner(Player player){
        winner = player;
    }
    
    /**
     * Mouse listener that keeps track of user actions such as clicking the
     * arrow buttons to change the pages, or hitting the return button
     * to go back to the previous panel
     */
    private class Handler extends MouseAdapter { 
        /**
         * Mouse action upon detecting a click. Finds out what button is pressed
         * and what to do based on what is pressed
         * 
         * @param e the instance of a mouse action
         */
        @Override
        public void mouseClicked(MouseEvent e) {
            if (e.getSource().equals(backArrow)) {
                playerLimdex--;
                if (playerLimdex < 0) {
                    playerLimdex = 0;
                }
            }
            else if (e.getSource().equals(forwardArrow)) {
                playerLimdex++;
                if (playerLimdex == otherPlayers.size()) {
                    playerLimdex--;
                }
            }
            repaint();
        }
        
        /**
         * Mouse action upon hovering over a JLabel. Changes the user's cursor
         * to show that you can click on the JLabel being hovered over
         * 
         * @param e the instance of a mouse action
         */
        @Override
        public void mouseEntered(MouseEvent e){
            setCursor(new Cursor(Cursor.HAND_CURSOR));
        }
        
        /**
         * Mouse action upon the mouse leaving a JLabel. Changes the user's cursor
         * to show that you can no longer click on the previously hovered over JLabel
         * 
         * @param e the instance of a mouse action
         */
        @Override
        public void mouseExited(MouseEvent e){
            setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        }
    }
}
