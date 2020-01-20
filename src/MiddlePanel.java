import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.*;
/**
 * Class that creates the middle pannel. The middle panel is where the map is stored,
 * and is where the player may select a route they would like to purchase.
 * 
 * @author Josh Dratler
 * @author Hamza Memon
 * @author Patrick Milano
 * @author Jhoan Esteban Osorno
 * @author Brian Smith
 * @version (1.0)
 */
public class MiddlePanel extends JPanel {

    protected static boolean placedTrain;
    private ImageIcon map;

    private City[] cities = City.values();
    private Rectangle[] cityRects = new Rectangle[cities.length];
    private Player player;
    private Route[] routes = Route.values();
    private  Route routeOnHover = routes[0];
    private Route routeOnClicked = routes[0];
    private City cityOnHover = cities[0];
    protected boolean clickedOnRoute;
    protected static boolean canHover = true;
    /**
     * Constructor for middle panel. Background is initialized to 
     * the Ticket to Ride UK map, and draws rectangels around all 
     * of the routes. 
     */
    public MiddlePanel(){
        setPreferredSize(new Dimension(568, 850));

        map = ResourceLoader.loadImage("final_map.jpg");

        addMouseMotionListener(new Handler());
        addMouseListener(new Handler());

        for(int i = 0; i < cities.length; i++){
            cityRects[i] = new Rectangle(cities[i].getX(), cities[i].getY(), 10, 10);
        }
    }

    /**
     * Method to override JPanel paint component. Fills in claimed routes the color 
     * of the player that claimed them, and draws a polygon the color of the route when
     * the mouse is hovering over that route. 
     * 
     * @param g instance of Graphics class
     */
    @Override
    protected void paintComponent(Graphics g){
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;
        map.paintIcon(this, g, 0, 0);

        if(!player.getTechnologies().contains(Technology.RIGHT_OF_WAY))
        {
            for(Route route : routes){
                if(route.isClaimed()){
                    g.setColor(route.getPlayer().getColor());
                    g.fillPolygon(route.getPolygon());
                }
            }
        }
        else
        {
            // MAKE THE CLAIMED ROUTES THAT ARENT YOURS HOVERABLE FOR RIGHT OF WAY

        }
        if(canHover)
        {
            //             g.setColor(Color.BLACK);
            //             BasicStroke stroke = new BasicStroke(3);
            //             g2d.setStroke(stroke);
            //             g2d.drawOval(cityOnHover.getX() - 12, cityOnHover.getY() - 12, 25, 25);
            g.setColor(routeOnHover.getColor());
            g.fillPolygon(routeOnHover.getPolygon());
        }
    }

    /**
     * Method to set the player
     * 
     * @param player the player we want to now look at
     */
    public void setPlayer(Player player){
        this.player = player;
    }

    /**
     * Method that allows us to claim a route. 
     * 
     * @param route route to be claimed
     */
    public void claimPath(Route route)
    {
        //clickedOnRoute = true;
        if (GameLoop.players.length == 2)
        {
            Route.setUpDoubleRoutes();
            Route route2 = route.getDoubleRoute();
            if (route2 != null)
            {
                route2.setClaim();
                route2.setPlayer(player);
                player.addRoute(route2);
            }
        }
        routeOnClicked = route;
        route.setClaim();
        route.setPlayer(player);
        player.addRoute(route);
        player.addPoints(route.getPoints());
        if(player.getTechnologies().contains(Technology.STEAM_TURBINE)){
            if(player.getTechnologies().contains(Technology.BOILER_LAGGING)){
                player.addPoints(3);
            }
            else
            {
                player.addPoints(2);
            }
        }
        else
        {
            if(player.getTechnologies().contains(Technology.BOILER_LAGGING)){
                player.addPoints(1);
            }
        }
        player.subtractTrains(route.getLength());
    }

    private class Handler extends MouseAdapter {
        /**
         * Method to override mouseClicked method.  Allows us to click on routes
         * 
         * @param e mouseEvent object
         */
        @Override
        public void mouseClicked(MouseEvent e){
            if (canHover == false)
            {
                return;
            }
            for(Route route : routes){
                if(!player.getTechnologies().contains(Technology.RIGHT_OF_WAY))
                {
                    if(!route.isClaimed())
                    {
                        if(route.getEnd() == City.NEWYORK){
                            if(route.getPolygon().contains(e.getPoint())){
                                if(player.getNumTrains() < route.getLength()){
                                    System.out.println("YOU CANNOT BUY THIS PATH");
                                    continue;
                                }
                                BuyRoutePanel.setRoute(route);
                                Driver.cardLayout.show(Driver.cards, "BuyRoutePanel");
                                break;
                            }
                        }
                        else {
                            if(route.getCountry().equals("W") || route.getCountry().equals("WI")){
                                if(!player.getTechnologies().contains(Technology.WALES_CONCESSION)){
                                    continue;//if user doesnt have WALES_CONCESSION Technology
                                }
                                if("WI".equals(route.getCountry())){
                                    if(!player.getTechnologies().contains(Technology.IRELAND_FRANCE_CONCESSION)){
                                        continue;//if user doesnt have IRELAND_FRANCE_CONCESSION
                                    }
                                }
                            }
                            else if(route.getCountry().equals("I")){
                                if(!player.getTechnologies().contains(Technology.IRELAND_FRANCE_CONCESSION)){
                                    continue;//if user doesnt have IRELAND_FRANCE_CONCESSION
                                }
                            }
                            else if(route.getCountry().equals("S") || route.getCountry().equals("SI")){
                                if(!player.getTechnologies().contains(Technology.SCOTLAND_CONCESSION)){
                                    continue;//if user doesnt have SCOTLAND_CONCESSION
                                }
                                if(route.getCountry().equals("SI")){
                                    if(!player.getTechnologies().contains(Technology.IRELAND_FRANCE_CONCESSION)){
                                        continue;//if user doesnt have IRELAND_FRANCE_CONCESSION
                                    }
                                }
                            }
                            if(route.getLength() == 3){
                                if(!player.getTechnologies().contains(Technology.MECHANICAL_STOKER)){
                                    continue;//if user doesnt have MECHANICAL_STOKER
                                }
                            }
                            if(route.getLength() > 3){
                                if(!player.getTechnologies().contains(Technology.SUPERHEATED_STEAM_BOILER)){
                                    continue;//if user doesnt have SUPERHEATER_STEAM_BOILER
                                }
                            }
                            if(route.getFerry() != 0){
                                if(!player.getTechnologies().contains(Technology.PROPELLERS)){
                                    continue;//if user doesnt have PROPELLERS
                                }
                            }
                            if (route.getColor() != Color.GRAY)
                            {
                                int index = 0; 
                                for (int x = 0; x < RightPanel.trainCardsArray.length ; x++)
                                {
                                    if (RightPanel.trainCardsArray[x].getColor() == route.getColor())
                                    {//find the train color card that matches the route
                                        index = x;
                                    }
                                }
                                if (RightPanel.occurrences[index]+ RightPanel.occurrences[0] < route.getLength())
                                {//check to see if the train color card combined with locomotive can cover cost of route
                                    continue;
                                }
                            }
                            if(route.getPolygon().contains(e.getPoint())){
                                // select cards from right panel
                                // if selected cards are correct
                                if(player.getNumTrains() < route.getLength()){
                                    // Pat make a button
                                    System.out.println("YOU CANNOT BUY THIS PATH");
                                    continue;
                                }
                                BuyRoutePanel.setRoute(route);
                                Driver.cardLayout.show(Driver.cards, "BuyRoutePanel");
                                //purchasePath(route);	
                                //claimPath(route);
                                break;
                            }
                        }
                    }
                }
                else 
                {
                    if (route.isClaimed() && !player.getRoutesOwned().contains(route))
                    {
                        if(route.getEnd() == City.NEWYORK){
                            if(route.getPolygon().contains(e.getPoint())){
                                if(player.getNumTrains() < route.getLength()){
                                    System.out.println("YOU CANNOT BUY THIS PATH");
                                    continue;
                                }
                                BuyRoutePanel.setRoute(route);
                                Driver.cardLayout.show(Driver.cards, "BuyRoutePanel");
                                break;
                            }
                        }
                        else {
                            if(route.getCountry().equals("W") || route.getCountry().equals("WI")){
                                if(!player.getTechnologies().contains(Technology.WALES_CONCESSION)){
                                    continue;
                                }
                                if("WI".equals(route.getCountry())){
                                    if(!player.getTechnologies().contains(Technology.IRELAND_FRANCE_CONCESSION)){
                                        continue;
                                    }
                                }
                            }
                            else if(route.getCountry().equals("I")){
                                if(!player.getTechnologies().contains(Technology.IRELAND_FRANCE_CONCESSION)){
                                    continue;
                                }
                            }
                            else if(route.getCountry().equals("S") || route.getCountry().equals("SI")){
                                if(!player.getTechnologies().contains(Technology.SCOTLAND_CONCESSION)){
                                    continue;
                                }
                                if(route.getCountry().equals("SI")){
                                    if(!player.getTechnologies().contains(Technology.IRELAND_FRANCE_CONCESSION)){
                                        continue;
                                    }
                                }
                            }
                            if(route.getLength() == 3){
                                if(!player.getTechnologies().contains(Technology.MECHANICAL_STOKER)){
                                    continue;
                                }
                            }
                            if(route.getLength() > 3){
                                if(!player.getTechnologies().contains(Technology.SUPERHEATED_STEAM_BOILER)){
                                    continue;
                                }
                            }
                            if(route.getFerry() != 0){
                                if(!player.getTechnologies().contains(Technology.PROPELLERS)){
                                    continue;
                                }
                            }
                            if (route.getColor() != Color.GRAY)
                            {
                                int index = 0; 
                                for (int x = 0; x < RightPanel.trainCardsArray.length ; x++)
                                {
                                    if (RightPanel.trainCardsArray[x].getColor() == route.getColor())
                                    {
                                        index = x;
                                    }
                                }
                                if (RightPanel.occurrences[index]+ RightPanel.occurrences[0] < route.getLength())
                                {
                                    continue;
                                }
                            }
                            if(route.getPolygon().contains(e.getPoint())){
                                // select cards from right panel
                                // if selected cards are correct
                                if(player.getNumTrains() < route.getLength()){
                                    // Pat make a button
                                    System.out.println("YOU CANNOT BUY THIS PATH");
                                    continue;
                                }
                                BuyRoutePanel.setRoute(route);
                                Driver.cardLayout.show(Driver.cards, "BuyRoutePanel");
                                //purchasePath(route);	
                                //claimPath(route);
                                break;
                            }
                        }
                    }
                }
            }
            repaint();
        }

        /**
         * Method to override mouseMoved. Allows us to hover over routes
         * 
         * @override e mouseEvent
         */
        @Override
        public void mouseMoved(MouseEvent e){
            //setToolTipText(null);
            for(int i = 0; i < cities.length; i++){
                if(cities[i].getCountry().equals("W")){
                    if(!player.getTechnologies().contains(Technology.WALES_CONCESSION)){
                        continue;
                    }
                }
                if(cities[i].getCountry().equals("S")){
                    if(!player.getTechnologies().contains(Technology.SCOTLAND_CONCESSION)){
                        continue;
                    }
                }
                if(cities[i].getCountry().equals("I")){
                    if(!player.getTechnologies().contains(Technology.IRELAND_FRANCE_CONCESSION)){
                        continue;
                    }
                }
                //if your hovering over that city
                if(cityRects[i].contains(e.getPoint())){
                    setToolTipText(String.valueOf(cities[i]));
                    cityOnHover = cities[i];
                    break;
                }
            }
            //             for(Route route : routes){
            //                 if(!player.getTechnologies().contains(Technology.RIGHT_OF_WAY))
            //                 {
            //                     if(!route.isClaimed())
            //                     {
            //                         if(route.getEnd() == City.NEWYORK){
            //                             if(route.getPolygon().contains(e.getPoint())){
            //                                 routeOnHover = route;
            //                                 break;
            //                             }
            //                         }
            //                         else {
            //                             if(route.getCountry().equals("W") || route.getCountry().equals("WI")){
            //                                 if(!player.getTechnologies().contains(Technology.WALES_CONCESSION)){
            //                                     continue;
            //                                 }
            //                                 if("WI".equals(route.getCountry())){
            //                                     if(!player.getTechnologies().contains(Technology.IRELAND_FRANCE_CONCESSION)){
            //                                         continue;
            //                                     }
            //                                 }
            //                             }
            //                             else if(route.getCountry().equals("I")){
            //                                 if(!player.getTechnologies().contains(Technology.IRELAND_FRANCE_CONCESSION)){
            //                                     continue;
            //                                 }
            //                             }
            //                             else if(route.getCountry().equals("S") || route.getCountry().equals("SI")){
            //                                 if(!player.getTechnologies().contains(Technology.SCOTLAND_CONCESSION)){
            //                                     continue;
            //                                 }
            //                                 if(route.getCountry().equals("SI")){
            //                                     if(!player.getTechnologies().contains(Technology.IRELAND_FRANCE_CONCESSION)){
            //                                         continue;
            //                                     }
            //                                 }
            //                             }
            //                             if(route.getLength() == 3){
            //                                 if(!player.getTechnologies().contains(Technology.MECHANICAL_STOKER)){
            //                                     continue;
            //                                 }
            //                             }
            //                             if(route.getLength() > 3){
            //                                 if(!player.getTechnologies().contains(Technology.SUPERHEATED_STEAM_BOILER)){
            //                                     continue;
            //                                 }
            //                             }
            //                             if(route.getFerry() != 0){
            //                                 if(!player.getTechnologies().contains(Technology.PROPELLERS)){
            //                                     continue;
            //                                 }
            //                             }
            //                             if (route.getColor() != Color.GRAY)
            //                             {
            //                                 int index = 0; 
            //                                 for (int x = 0; x < RightPanel.trainCardsArray.length ; x++)
            //                                 {
            //                                     if (RightPanel.trainCardsArray[x].getColor() == route.getColor())
            //                                     {
            //                                         index = x;
            //                                     }
            //                                 }
            //                                 if (RightPanel.occurrences[index]+ RightPanel.occurrences[0] < route.getLength())
            //                                 {
            //                                     continue;
            //                                 }
            //                             }
            //                             else
            //                             {
            //                                 int max = 0;
            //                                 for (int i = 0; i < RightPanel.occurrences.length; i++)
            //                                 {
            //                                     if(max < RightPanel.occurrences[i])
            //                                         max = RightPanel.occurrences[i];
            //                                 }
            //                                 if (max + RightPanel.occurrences[0] < route.getLength())
            //                                 {
            //                                     continue;
            //                                 }
            //                             }
            //                             if(route.getPolygon().contains(e.getPoint())){
            //                                 routeOnHover = route;
            //                                 repaint();
            //                                 break;
            //                             }
            //                         }
            //                     }
            //                 }
            //                 else 
            //                 {
            //                     if (route.isClaimed() && !player.getRoutesOwned().contains(route))
            //                     {
            //                         if(route.getEnd() == City.NEWYORK){
            //                             if(route.getPolygon().contains(e.getPoint())){
            //                                 routeOnHover = route;
            //                                 break;
            //                             }
            //                         }
            //                         else {
            //                             if(route.getCountry().equals("W") || route.getCountry().equals("WI")){
            //                                 if(!player.getTechnologies().contains(Technology.WALES_CONCESSION)){
            //                                     continue;
            //                                 }
            //                                 if("WI".equals(route.getCountry())){
            //                                     if(!player.getTechnologies().contains(Technology.IRELAND_FRANCE_CONCESSION)){
            //                                         continue;
            //                                     }
            //                                 }
            //                             }
            //                             else if(route.getCountry().equals("I")){
            //                                 if(!player.getTechnologies().contains(Technology.IRELAND_FRANCE_CONCESSION)){
            //                                     continue;
            //                                 }
            //                             }
            //                             else if(route.getCountry().equals("S") || route.getCountry().equals("SI")){
            //                                 if(!player.getTechnologies().contains(Technology.SCOTLAND_CONCESSION)){
            //                                     continue;
            //                                 }
            //                                 if(route.getCountry().equals("SI")){
            //                                     if(!player.getTechnologies().contains(Technology.IRELAND_FRANCE_CONCESSION)){
            //                                         continue;
            //                                     }
            //                                 }
            //                             }
            //                             if(route.getLength() == 3){
            //                                 if(!player.getTechnologies().contains(Technology.MECHANICAL_STOKER)){
            //                                     continue;
            //                                 }
            //                             }
            //                             if(route.getLength() > 3){
            //                                 if(!player.getTechnologies().contains(Technology.SUPERHEATED_STEAM_BOILER)){
            //                                     continue;
            //                                 }
            //                             }
            //                             if(route.getFerry() != 0){
            //                                 if(!player.getTechnologies().contains(Technology.PROPELLERS)){
            //                                     continue;
            //                                 }
            //                             }
            //                             if (route.getColor() != Color.GRAY)
            //                             {
            //                                 int index = 0; 
            //                                 for (int x = 0; x < RightPanel.trainCardsArray.length ; x++)
            //                                 {
            //                                     if (RightPanel.trainCardsArray[x].getColor() == route.getColor())
            //                                     {
            //                                         index = x;
            //                                     }
            //                                 }
            //                                 if (RightPanel.occurrences[index]+ RightPanel.occurrences[0] < route.getLength())
            //                                 {
            //                                     continue;
            //                                 }
            //                             }
            //                             else
            //                             {
            //                                 int max = 0;
            //                                 for (int i = 0; i < RightPanel.occurrences.length; i++)
            //                                 {
            //                                     if(max < RightPanel.occurrences[i])
            //                                         max = RightPanel.occurrences[i];
            //                                 }
            //                                 if (max + RightPanel.occurrences[0] < route.getLength())
            //                                 {
            //                                     continue;
            //                                 }
            //                             }
            //                             if(route.getPolygon().contains(e.getPoint())){
            //                                 routeOnHover = route;
            //                                 repaint();
            //                                 break;
            //                             }
            //                         }
            //                     }
            //                 }
            //             }

            for(Route route : routes){
                if(route.isClaimed()){
                    continue;//if someone alread has the route
                }
                if(player.getNumTrains() < route.getLength()){
                    continue;//if player doesnt have enough trains to cover train
                }
                if(route.getEnd() == City.NEWYORK){
                    if(route.getPolygon().contains(e.getPoint())){
                        routeOnHover = route;
                        break;
                    }
                }
                else {
                    if(route.getCountry().equals("W") || route.getCountry().equals("WI")){
                        if(!player.getTechnologies().contains(Technology.WALES_CONCESSION)){
                            continue;
                        }
                        if(route.getCountry().equals("WI")){
                            if(!player.getTechnologies().contains(Technology.IRELAND_FRANCE_CONCESSION)){
                                continue;
                            }
                        }
                    }
                    else if(route.getCountry().equals("I")){
                        if(!player.getTechnologies().contains(Technology.IRELAND_FRANCE_CONCESSION)){
                            continue;
                        }
                    }
                    else if(route.getCountry().equals("S") || route.getCountry().equals("SI")){
                        if(!player.getTechnologies().contains(Technology.SCOTLAND_CONCESSION)){
                            continue;
                        }
                        if(route.getCountry().equals("SI")){
                            if(!player.getTechnologies().contains(Technology.IRELAND_FRANCE_CONCESSION)){
                                continue;
                            }
                        }
                    }
                    if(route.getLength() == 3){
                        if(!player.getTechnologies().contains(Technology.MECHANICAL_STOKER)){
                            continue;
                        }
                    }
                    if(route.getLength() > 3){
                        if(!player.getTechnologies().contains(Technology.SUPERHEATED_STEAM_BOILER)){
                            continue;
                        }
                    }
                    if(route.getFerry() != 0){
                        if(!player.getTechnologies().contains(Technology.PROPELLERS)){
                            continue;
                        }
                    }
                    if (route.getColor() != Color.GRAY)
                    {
                        int index = 0; 
                        for (int x = 0; x < RightPanel.trainCardsArray.length ; x++)
                        {
                            if (RightPanel.trainCardsArray[x].getColor() == route.getColor())
                            {
                                index = x;
                            }
                        }
                        if (RightPanel.occurrences[index]+ RightPanel.occurrences[0] < route.getLength())
                        {
                            continue;
                        }
                    }
                    else
                    {
                        int max = 0;
                        for (int i = 0; i < RightPanel.occurrences.length; i++)
                        {
                            if(max < RightPanel.occurrences[i])
                                max = RightPanel.occurrences[i];
                        }
                        if (max + RightPanel.occurrences[0] < route.getLength())
                        {
                            continue;
                        }
                    }
                    //show hover over route
                    if(route.getPolygon().contains(e.getPoint())){
                        routeOnHover = route;
                        repaint();
                        break;
                    }
                }
            }
        }
    }
}