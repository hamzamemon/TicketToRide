import javax.swing.ImageIcon;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

/**
 * Creates the middle panel, where the map is stored and where the player may select a route to purchase.
 *
 * @author Josh Dratler
 * @author Hamza Memon
 * @author Patrick Milano
 * @author Jhoan Esteban Osorno
 * @author Brian Smith
 * @version (1.0)
 */
public class MiddlePanel extends JPanel {
    
    private boolean placedTrain;
    protected static boolean canHover = true;
    private ImageIcon map = ResourceLoader.loadImage("final_map.jpg");
    private City[] cities = City.values();
    private Rectangle[] cityRects = new Rectangle[cities.length];
    private Player player;
    private Route[] routes = Route.values();
    private Route routeOnHover = routes[0];
    
    /**
     * Constructs the middle panel. Background is initialized to the Ticket to Ride UK map.
     */
    public MiddlePanel() {
        setPreferredSize(new Dimension(568, 850));
        
        addMouseMotionListener(new Handler());
        addMouseListener(new Handler());
        
        for(int i = 0, length = cities.length; i < length; i++) {
            cityRects[i] = new Rectangle(cities[i].getX(), cities[i].getY(), 10, 10);
        }
        
        Route.setUpDoubleRoutes();
    }
    
    /**
     * Fills in claimed routes the color of the player that claimed them, and draws a polygon the color of the route
     * when the mouse is hovering over that route.
     *
     * @param g instance of Graphics class
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        map.paintIcon(this, g, 0, 0);
        
        if(!player.getTechnologies().contains(Technology.RIGHT_OF_WAY)) {
            for(Route route : routes) {
                if(route.isClaimed()) {
                    g.setColor(route.getPlayer().getColor());
                    g.fillPolygon(route.getPolygon());
                }
            }
        }
        
        if(canHover) {
            g.setColor(routeOnHover.getColor());
            g.fillPolygon(routeOnHover.getPolygon());
        }
    }
    
    /**
     * Sets the player we are currently looking at
     *
     * @param player the player we want to now look at
     */
    public void setPlayer(Player player) {
        this.player = player;
    }
    
    /**
     * Allows the player to claim a route.
     *
     * @param route route to be claimed
     */
    public void claimPath(Route route) {
        if(GameLoop.players.length == 2) {
            Route route2 = route.getDoubleRoute();
            if(route2 != null) {
                route2.setClaim();
                route2.setPlayer(player);
                player.addRoute(route2);
            }
        }
        
        route.setClaim();
        route.setPlayer(player);
        player.addRoute(route);
        player.addPoints(route.getPoints());
        
        ArrayList<Technology> playerTechnologies = player.getTechnologies();
        if(playerTechnologies.contains(Technology.STEAM_TURBINE)) {
            if(playerTechnologies.contains(Technology.BOILER_LAGGING)) {
                player.addPoints(3);
            }
            else {
                player.addPoints(2);
            }
        }
        else {
            if(playerTechnologies.contains(Technology.BOILER_LAGGING)) {
                player.addPoints(1);
            }
        }
        
        player.subtractTrains(route.getLength());
    }
    
    /**
     * Checks if the train has been placed
     *
     * @return if train has been placed
     */
    public boolean isPlacedTrain() {
        return placedTrain;
    }
    
    /**
     * Sets the train to placed
     *
     * @param placedTrain sets value if train has been placed
     */
    public void setPlacedTrain(boolean placedTrain) {
        this.placedTrain = placedTrain;
    }
    
    private class Handler extends MouseAdapter {
        /**
         * Allows the player to click on routes
         *
         * @param e mouseEvent object
         */
        @Override
        public void mouseClicked(MouseEvent e) {
            if(!canHover) {
                return;
            }
            for(Route route : routes) {
                ArrayList<Technology> playerTechnologies = player.getTechnologies();
                if(playerTechnologies.contains(Technology.RIGHT_OF_WAY)) {
                    if(route.isClaimed() && !player.getRoutesOwned().contains(route)) {
                        if(route.getEnd() == City.NEWYORK) {
                            if(route.getPolygon().contains(e.getPoint())) {
                                if(player.getNumTrains() < route.getLength()) {
                                    System.out.println("YOU CANNOT BUY THIS PATH");
                                    continue;
                                }
                                
                                BuyRoutePanel.setRoute(route);
                                Driver.cardLayout.show(Driver.cards, "BuyRoutePanel");
                                break;
                            }
                        }
                        else {
                            if(!checkTechnologyCards(route)) {
                                continue;
                            }
                            
                            Color color = route.getColor();
                            if(!color.equals(Color.GRAY)) {
                                int index = 0;
                                for(int x = 0, length = RightPanel.trainCardsArray.length; x < length; x++) {
                                    if(RightPanel.trainCardsArray[x].getColor().equals(color)) {
                                        index = x;
                                    }
                                }
                                if(RightPanel.occurrences[index] + RightPanel.occurrences[0] < route.getLength()) {
                                    continue;
                                }
                            }
                            if(route.getPolygon().contains(e.getPoint())) {
                                if(player.getNumTrains() < route.getLength()) {
                                    System.out.println("YOU CANNOT BUY THIS PATH");
                                    continue;
                                }
                                
                                BuyRoutePanel.setRoute(route);
                                Driver.cardLayout.show(Driver.cards, "BuyRoutePanel");
                                break;
                            }
                        }
                    }
                }
                else {
                    if(!route.isClaimed()) {
                        if(route.getEnd() == City.NEWYORK) {
                            if(route.getPolygon().contains(e.getPoint())) {
                                if(player.getNumTrains() < route.getLength()) {
                                    System.out.println("YOU CANNOT BUY THIS PATH");
                                    continue;
                                }
                                BuyRoutePanel.setRoute(route);
                                Driver.cardLayout.show(Driver.cards, "BuyRoutePanel");
                                break;
                            }
                        }
                        else {
                            if(!checkTechnologyCards(route)) {
                                continue;
                            }
                            
                            Color color = route.getColor();
                            if(!color.equals(Color.GRAY)) {
                                int index = 0;
                                for(int x = 0, length = RightPanel.trainCardsArray.length; x < length; x++) {
                                    if(RightPanel.trainCardsArray[x].getColor().equals(color)) {
                                        index = x;
                                    }
                                }
                                if(RightPanel.occurrences[index] + RightPanel.occurrences[0] < route.getLength()) {
                                    // Checks if the train color card combined with locomotive can cover cost of route
                                    continue;
                                }
                            }
                            if(route.getPolygon().contains(e.getPoint())) {
                                if(player.getNumTrains() < route.getLength()) {
                                    System.out.println("YOU CANNOT BUY THIS PATH");
                                    continue;
                                }
                                
                                BuyRoutePanel.setRoute(route);
                                Driver.cardLayout.show(Driver.cards, "BuyRoutePanel");
                                break;
                            }
                        }
                    }
                }
            }
            
            repaint();
        }
        
        private boolean checkTechnologyCards(Route route) {
            boolean isValidTechnology = true;
            
            // Check country Concession technology cards
            ArrayList<Technology> playerTechnologies = player.getTechnologies();
            String country = route.getCountry();
            if("W".equals(country)) {
                if(!playerTechnologies.contains(Technology.WALES_CONCESSION)) {
                    isValidTechnology = false;
                }
            }
            else if("WI".equals(country)) {
                if(!playerTechnologies.contains(Technology.WALES_CONCESSION)) {
                    isValidTechnology = false;
                }
                if(!playerTechnologies.contains(Technology.IRELAND_FRANCE_CONCESSION)) {
                    isValidTechnology = false;
                }
            }
            else if("I".equals(country)) {
                if(!playerTechnologies.contains(Technology.IRELAND_FRANCE_CONCESSION)) {
                    isValidTechnology = false;
                }
            }
            else if("S".equals(country)) {
                if(!playerTechnologies.contains(Technology.SCOTLAND_CONCESSION)) {
                    isValidTechnology = false;
                }
            }
            else if("SI".equals(country)) {
                if(!playerTechnologies.contains(Technology.SCOTLAND_CONCESSION)) {
                    isValidTechnology = false;
                }
                if(!playerTechnologies.contains(Technology.IRELAND_FRANCE_CONCESSION)) {
                    isValidTechnology = false;
                }
            }
            
            // Check route length technology cards
            else if(route.getLength() == 3) {
                if(!playerTechnologies.contains(Technology.MECHANICAL_STOKER)) {
                    isValidTechnology = false;
                }
            }
            else if(route.getLength() > 3) {
                if(!playerTechnologies.contains(Technology.SUPERHEATED_STEAM_BOILER)) {
                    isValidTechnology = false;
                }
            }
            else if(route.getFerry() != 0) {
                if(!playerTechnologies.contains(Technology.PROPELLERS)) {
                    isValidTechnology = false;
                }
            }
            
            return isValidTechnology;
        }
        
        /**
         * Allows us to hover over routes
         *
         * @param e a MouseEvent
         */
        @Override
        public void mouseMoved(MouseEvent e) {
            for(int i = 0, length = cities.length; i < length; i++) {
                City city = cities[i];
                String country = city.getCountry();
                ArrayList<Technology> playerTechnologies = player.getTechnologies();
                if("W".equals(country)) {
                    if(!playerTechnologies.contains(Technology.WALES_CONCESSION)) {
                        continue;
                    }
                }
                if("S".equals(country)) {
                    if(!playerTechnologies.contains(Technology.SCOTLAND_CONCESSION)) {
                        continue;
                    }
                }
                if("I".equals(country)) {
                    if(!playerTechnologies.contains(Technology.IRELAND_FRANCE_CONCESSION)) {
                        continue;
                    }
                }
                
                // If you're hovering over that city
                if(cityRects[i].contains(e.getPoint())) {
                    setToolTipText(String.valueOf(city));
                    break;
                }
            }
            
            for(Route route : routes) {
                if(route.isClaimed()) {
                    continue;
                }
                if(player.getNumTrains() < route.getLength()) {
                    continue;
                }
                if(route.getEnd() == City.NEWYORK) {
                    if(route.getPolygon().contains(e.getPoint())) {
                        routeOnHover = route;
                        break;
                    }
                }
                else {
                    if(!checkTechnologyCards(route)) {
                        continue;
                    }
                    
                    int index = 0;
                    
                    Color color = route.getColor();
                    if(color.equals(Color.GRAY)) {
                        for(int i = 0, length = RightPanel.occurrences.length; i < length; i++) {
                            if(index < RightPanel.occurrences[i]) {
                                index = RightPanel.occurrences[i];
                            }
                        }
                        if(index + RightPanel.occurrences[0] < route.getLength()) {
                            continue;
                        }
                    }
                    else {
                        for(int x = 0, length = RightPanel.trainCardsArray.length; x < length; x++) {
                            if(RightPanel.trainCardsArray[x].getColor().equals(color)) {
                                index = x;
                            }
                        }
                        if(RightPanel.occurrences[index] + RightPanel.occurrences[0] < route.getLength()) {
                            continue;
                        }
                    }
                    
                    // Show hover over route
                    if(route.getPolygon().contains(e.getPoint())) {
                        routeOnHover = route;
                        repaint();
                        break;
                    }
                }
            }
        }
    }
}
