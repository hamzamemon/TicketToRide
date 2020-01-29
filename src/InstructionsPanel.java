import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Allows the user to view the instructions for both the UK version of Ticket to Ride, and the base game rules.
 *
 * @author Josh Dratler
 * @author Hamza Memon
 * @author Patrick Milano
 * @author Jhoan Esteban Osorno
 * @author Brian Smith
 * @version (1.0)
 */
public class InstructionsPanel extends JPanel {
    
    protected ImageIcon[] manual = new ImageIcon[7];
    private JLabel backArrow = new JLabel();
    private JLabel forwardArrow = new JLabel();
    private JLabel returnButton = new JLabel();
    private int fileIndex = 0;
    private ImageIcon background = ResourceLoader.loadImage("instructions_background.jpg");
    
    /**
     * Constructs the instructions panel.
     *
     * Initializes the dimensions of the panel and retrieves the background, arrow, and return button assets.
     */
    public InstructionsPanel() {
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(1280, 1024));
        
        for(int i = 1, length = manual.length; i <= length; i++) {
            manual[i - 1] = ResourceLoader.loadImage("instructionManual/p" + i + ".png");
        }
        
        // Creates a backwards arrow that allows you to flip the pages
        Icon backArrowIcon = ResourceLoader.loadImage("back_arrow.png");
        backArrow.setIcon(backArrowIcon);
        backArrow.addMouseListener(new Handler());
        add(backArrow, BorderLayout.WEST);
        
        Icon forwardArrowIcon = ResourceLoader.loadImage("forward_arrow.png");
        forwardArrow.setIcon(forwardArrowIcon);
        forwardArrow.addMouseListener(new Handler());
        add(forwardArrow, BorderLayout.EAST);
        
        Icon returnIcon = ResourceLoader.loadImage("returnButton.png");
        returnButton.setIcon(returnIcon);
        returnButton.addMouseListener(new Handler());
        add(returnButton, BorderLayout.NORTH);
        
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 30, 50));
        panel.setOpaque(false);
        add(panel, BorderLayout.CENTER);
    }
    
    /**
     * Draws the current page selected and the background for the manual
     *
     * @param g instance of Graphics.
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        background.paintIcon(this, g, 0, 0);
        manual[fileIndex].paintIcon(this, g, 280, 12);
    }
    
    /**
     * Keeps track of user actions, such as clicking the arrow buttons to change the pages, or hitting the return button
     * to go back to the previous panel
     */
    private class Handler extends MouseAdapter {
        
        /**
         * Finds out what button is pressed and what to do based on what is pressed
         *
         * @param e the instance of a MouseEvent action
         */
        @Override
        public void mouseClicked(MouseEvent e) {
            if(e.getSource().equals(backArrow)) {
                fileIndex--;
                if(fileIndex < 0) {
                    fileIndex = 0;
                }
            }
            else if(e.getSource().equals(forwardArrow)) {
                fileIndex++;
                if(fileIndex == manual.length) {
                    fileIndex--;
                }
            }
            else if(e.getSource().equals(returnButton)) {
                Driver.cardLayout.previous(Driver.cards);
            }
            
            repaint();
        }
        
        /**
         * Changes the mouse cursor to a hand when hovering over a JLabel.
         *
         * @param e instance of MouseEvent
         */
        @Override
        public void mouseEntered(MouseEvent e) {
            setCursor(new Cursor(Cursor.HAND_CURSOR));
        }
        
        /**
         * Changes the mouse cursor back to a regular cursor after leaving a JLabel.
         *
         * @param e instance of MouseEvent
         */
        @Override
        public void mouseExited(MouseEvent e) {
            setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        }
    }
}
