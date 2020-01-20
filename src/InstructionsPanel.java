import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
/**
 * A JPanel that allows the user to view the isntructions for both the UK expansion
 * for Ticket to Ride, and the base game rules for Ticket to Ride
 * 
 * @author Jhoan Osorno
 * @author Pat Milano
 * @author Brian Smith
 * @author Hamza Memon
 * @author Josh Dratler
 * @version (1.0)
 */
public class InstructionsPanel extends JPanel
{
    private JLabel backArrow;
    private JLabel forwardArrow;
    private JLabel returnButton;//button to return to top screen
    private JPanel panel;
    private int fileLimdex;
    protected ImageIcon[] manual;
    private ImageIcon background;
    

    /**
     * Constructor for the InstructionPanel object. Initializes the dimensions
     * of the panel and retrieves the background, arrow, and return button assets
     */
    public InstructionsPanel(){        
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(1280, 1024));
                
        background = ResourceLoader.loadImage("instructions_background.jpg");
        
        manual = new ImageIcon[7];
        for (int i = 1; i <= manual.length; i++) {
            manual[i-1] = ResourceLoader.loadImage("instructionManual/p" + i + ".png");
        }
        fileLimdex = 0;
        // creates a backwards arrow that allows you to flip the pages
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
        
        returnButton = new JLabel();
        Icon returnIcon = ResourceLoader.loadImage("returnButton.png");
        returnButton.setIcon(returnIcon);
        returnButton.addMouseListener(new Handler());
        add(returnButton, BorderLayout.NORTH);

        panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 30, 50));
        panel.setOpaque(false);

        add(panel, BorderLayout.CENTER);
    }

    /**
     * Paint method that draws the current page selected and the background
     * for the manual
     * @param g The graphics object which carries out paint operations
     */
    @Override
    protected void paintComponent(Graphics g){
        super.paintComponent(g);
        
        background.paintIcon(this,g,0,0);
        manual[fileLimdex].paintIcon(this, g, 280, 12);
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
                fileLimdex--;
                if (fileLimdex < 0) {
                    fileLimdex = 0;
                }
            }
            else if (e.getSource().equals(forwardArrow)) {
                fileLimdex++;
                if (fileLimdex == manual.length) {
                    fileLimdex--;
                }
            }
            else if (e.getSource().equals(returnButton)) {
                Driver.cardLayout.previous(Driver.cards);                
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
