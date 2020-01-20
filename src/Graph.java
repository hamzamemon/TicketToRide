import java.util.ArrayList;
import java.util.Arrays;

public class Graph {

	private Player player;

	private Route[] routes = Route.values();

	private City[] cities = City.values();
	private ArrayList<City> cityList = new ArrayList<>(Arrays.asList
			(City.values()));
	private boolean[] visited = new boolean[cities.length];

	public void setPlayer(Player player){
		this.player = player;
	}

	public void dfs(){
		ArrayList<DestinationCard> playerDest = player.getDestinationCards();
		ArrayList<DestinationCard> done = player.getCompletedDestinations();

		for(DestinationCard card : playerDest){
			if(!done.contains(card)){
				visited = new boolean[cities.length];

				if(dfsRecursion(card.getStart(), card.getEnd())){
				    player.addPoints(card.getValue());
				    player.getDestinationCards().remove(card);
					done.add(card);
				}
			}
		}
	}

	private boolean dfsRecursion(City start, City end){
		if(start == end){
			return true;
		}

		visited[cityList.indexOf(start)] = true;

		ArrayList<City> citiesList = adjCities(start);

		for(int i = 0; i < citiesList.size(); i++){
			if(!visited[cityList.indexOf(citiesList.get(i))]){
				return dfsRecursion(citiesList.get(i), end);
			}
		}

		return false;
	}

	private ArrayList<City> adjCities(City city){
		ArrayList<City> cities = new ArrayList<>();

		for(Route route : routes){
			if(route.getPlayer() != null){
				if(route.getStart() == city && route.getPlayer().equals
						(player)){
					cities.add(route.getEnd());
				}

				if(route.getEnd() == city && route.getPlayer().equals(player)){
					cities.add(route.getStart());
				}
			}
		}

		return cities;
	}
}