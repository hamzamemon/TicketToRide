import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
/**
 * Class used to run the entire program and start the Ticket to Ride UK Simulation
 * 
 * @author Josh Dratler
 * @author Hamza Memon
 * @author Patrick Milano
 * @author Jhoan Esteban Osorno
 * @author Brian Smith
 * @version (1.0)
 */
public class Driver {

    protected static JFrame frame;
    protected static JPanel cards = new JPanel(new CardLayout());
    protected static CardLayout cardLayout;
    protected static JPanel container = new JPanel();
    protected static GameLoop loop = new GameLoop();
    protected static BuyTechnologyPanel buy = new BuyTechnologyPanel();
    protected static EndingPanel ending = new EndingPanel();
    /**
     * Main method to start the simulation
     * 
     * @param args command line arguments
     */
    public static void main(String[] args){
        frame = new JFrame("Title");

        frame.setLayout(new FlowLayout());
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize(new Dimension(1280, 1024));
        frame.setLocationRelativeTo(null);

        container.setLayout(new BorderLayout());
        container.add(GameLoop.leftPanel, BorderLayout.WEST);
        container.add(GameLoop.middlePanel, BorderLayout.CENTER);
        container.add(GameLoop.rightPanel, BorderLayout.EAST);
        container.add(GameLoop.bottomPanel, BorderLayout.SOUTH);

        cards.add(new Setup(), "Setup");
        cards.add(new InstructionsPanel(), "Instructions Manual");
        cards.add(container, "Container");
        cards.add(new InstructionsPanel(), "Instructions Part II");
        cards.add(buy, "BuyTechnologyPanel");
        cards.add(new BuyRoutePanel(), "BuyRoutePanel");
        cards.add(new ConvertPanel(), "ConvertPanel");
        cards.add(ending, "Ending");

        cardLayout = (CardLayout) cards.getLayout();

        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.addKeyListener(new KeyHandler());
        frame.setUndecorated(true);

        frame.add(cards);
        frame.setResizable(false);
        frame.setVisible(true);
    }
    /**
     * Class to extend KeyAdapter so we can use keyhandler method.
     */
    private static class KeyHandler extends KeyAdapter {
        @Override
        /**
         * Override of keyPressed
         * 
         * @param e istnace of keyEvent
         */
        public void keyPressed(KeyEvent e){
            if(e.getKeyCode() == KeyEvent.VK_ESCAPE){
                frame.dispose();
                frame.setExtendedState(JFrame.NORMAL);
                frame.setUndecorated(false);
                frame.pack();
                frame.setVisible(true);
            }

            else if(e.getKeyCode() == KeyEvent.VK_F11){
                frame.dispose();
                frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
                frame.setUndecorated(true);
                frame.pack();
                frame.setVisible(true);
            }
        }
    }
}