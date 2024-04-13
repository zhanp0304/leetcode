package graph;


import java.util.*;

/**
 * Find the customer's itinerary that covers all the cities
 */
public class FindTripItinerary {

    private final List<List<String>> fullCoverRouts = new ArrayList<>();

    private boolean[] visitedCities;

    private String[] cities;

    private boolean[][] canReachRouteTable;

    public List<List<String>> findTripItinerary(List<DirectRoute> directRoutes, String startingCity) {
        if (directRoutes == null || directRoutes.isEmpty()) {
            return Collections.emptyList();
        }

        // Find all citySet
        Set<String> citySet = new HashSet<>();
        for (DirectRoute directRoute : directRoutes) {
            citySet.add(directRoute.start);
            citySet.add(directRoute.dest);
        }


        // Initialize
        int cityCount = citySet.size();
        this.cities = citySet.toArray(new String[0]);
        this.visitedCities = new boolean[cities.length];


        Map<String, Integer> idByCityName = new HashMap<>(cityCount);
        int idGenerator = 0;
        for (String cityName : citySet) {
            idByCityName.put(cityName, idGenerator++);
        }

        this.canReachRouteTable = new boolean[cityCount][cityCount];
        for (DirectRoute directRoute : directRoutes) {
            int startingCityId = idByCityName.get(directRoute.getStart());
            int destCityId = idByCityName.get(directRoute.getDest());
            this.canReachRouteTable[startingCityId][destCityId] = true;
        }


        // Find Itinerary
        int startId = idByCityName.get(startingCity);

        Stack<String> routeStack = new Stack<>();

        traverse(startId, routeStack);
        return fullCoverRouts;
    }

    private void traverse(int currentCityId, Stack<String> currentRouteStack) {
        // Mark current city as visited
        visitedCities[currentCityId] = true;
        currentRouteStack.push(cities[currentCityId]);

        if (currentRouteStack.size() == cities.length) {
            // Find the cover route
            fullCoverRouts.add(new ArrayList<>(currentRouteStack));
        } else {
            // Not matched yet, go ahead to find the cover route
            for (int i = 0; i < cities.length; i++) {
                // If current city can be reached and hasn't been visited, then traverse recursively
                if (canReachRouteTable[currentCityId][i] && !visitedCities[i]) {
                    traverse(i, currentRouteStack);
                }
            }
        }

        // Backtrack
        visitedCities[currentCityId] = false;
        currentRouteStack.pop();
    }

    public static void main(String[] args) {
        // Test Case 1
        List<DirectRoute> routes1 = Arrays.asList(
                new DirectRoute("Amsterdam", "Berlin"),
                new DirectRoute("Amsterdam", "London"),
                new DirectRoute("Berlin", "Amsterdam"),
                new DirectRoute("Barcelona", "Berlin"),
                new DirectRoute("London", "Milan")
        );
        String startingCity1 = "Barcelona";
        testFindTripItinerary(routes1, startingCity1);

        // Test Case 2
        List<DirectRoute> routes2 = Arrays.asList(
                new DirectRoute("Amsterdam", "London"),
                new DirectRoute("Barcelona", "Berlin")
        );
        String startingCity2 = "Barcelona";
        testFindTripItinerary(routes2, startingCity2);
    }

    private static void testFindTripItinerary(List<DirectRoute> routes, String startingCity) {
        FindTripItinerary findTripItinerary = new FindTripItinerary();
        List<List<String>> result = findTripItinerary.findTripItinerary(routes, startingCity);

        System.out.println("Routes for starting city '" + startingCity + "':");
        if (result.isEmpty()) {
            System.out.println("No valid itinerary found.");
        } else {
            for (List<String> itinerary : result) {
                System.out.println(itinerary);
            }
        }
        System.out.println("------------------------");
    }

}
