import java.util.ArrayList;
import java.util.Arrays;

/**
 * Calculates if a player has completed a path
 */
public class Graph {
    
    private Player player;
    
    private Route[] routes = Route.values();
    
    private City[] cities = City.values();
    private ArrayList<City> cityList = new ArrayList<>(Arrays.asList(City.values()));
    
    private boolean[] visited = new boolean[cities.length];
    
    /**
     * Sets the player we are currently looking at
     *
     * @param player player we are currently looking at
     */
    public void setPlayer(Player player) {
        this.player = player;
    }
    
    /**
     * Determines if a player completed a card by Depth-First Search
     */
    public void dfs() {
        ArrayList<DestinationCard> playerDestinationCards = player.getDestinationCards();
        ArrayList<DestinationCard> done = player.getCompletedDestinations();
        
        for(DestinationCard card : playerDestinationCards) {
            if(!done.contains(card)) {
                visited = new boolean[cities.length];
                
                if(dfsRecursion(card.getStart(), card.getEnd())) {
                    player.addPoints(card.getValue());
                    player.getDestinationCards().remove(card);
                    done.add(card);
                }
            }
        }
    }
    
    /**
     * Performs recursion on paths to see if the two cities connect
     *
     * @param start the starting city
     * @param end   the ending city
     *
     * @return if the cities are connected
     */
    private boolean dfsRecursion(City start, City end) {
        if(start == end) {
            return true;
        }
        
        visited[cityList.indexOf(start)] = true;
        
        ArrayList<City> citiesList = adjCities(start);
        
        for(City city : citiesList) {
            if(!visited[cityList.indexOf(city)]) {
                return dfsRecursion(city, end);
            }
        }
        
        return false;
    }
    
    /**
     * Get list of adjacent cities
     *
     * @param city center city
     *
     * @return list of cities next to center city
     */
    private ArrayList<City> adjCities(City city) {
        ArrayList<City> cities = new ArrayList<>();
        
        for(Route route : routes) {
            Player player1 = route.getPlayer();
            
            if(player1 != null) {
                if(route.getStart() == city && player1.equals(player)) {
                    cities.add(route.getEnd());
                }
                
                if(route.getEnd() == city && player1.equals(player)) {
                    cities.add(route.getStart());
                }
            }
        }
        
        return cities;
    }
}
