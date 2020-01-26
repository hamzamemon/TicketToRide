import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;

/**
 * Runs the entire program and start the Ticket to Ride UK Simulation
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
    public static void main(String[] args) {
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
        frame.setUndecorated(true);
        
        frame.add(cards);
        frame.setResizable(false);
        frame.setVisible(true);
    }
}
