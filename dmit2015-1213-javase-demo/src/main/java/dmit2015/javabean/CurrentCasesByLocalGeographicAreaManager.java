package dmit2015.javabean;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CurrentCasesByLocalGeographicAreaManager {

    private List<CurrentCasesByLocalGeographicArea> currentCases;

    // Implement Class-Based Singleton as shown in https://www.baeldung.com/java-singleton
    private static CurrentCasesByLocalGeographicAreaManager INSTANCE;
    private CurrentCasesByLocalGeographicAreaManager() {
        loadData();
    }
    public static CurrentCasesByLocalGeographicAreaManager getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new CurrentCasesByLocalGeographicAreaManager();
        }

        return INSTANCE;
    }

    private void loadData() {
        try {
            Path csvPath = Path.of(getClass().getClassLoader().getResource("data/csv/COVID-19_in_Alberta__Current_cases_by_local_geographic_area.csv").toURI());
//            Path csvPath = Path.of("d:/temp/COVID-19_in_Alberta__Current_cases_by_local_geographic_area.csv");
            Stream<String> linesStream = Files.lines(csvPath);
            currentCases = linesStream
                    .skip(1)
                    .map(line -> CurrentCasesByLocalGeographicArea.parseCsv(line).orElse(null))
                    .filter(Objects::nonNull)
                    .toList();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public int count() {
        return currentCases.size();
    }

    public List<CurrentCasesByLocalGeographicArea> findAll(int pageNumber, int pageSize) {
        return currentCases.stream()
                .skip((pageNumber - 1) * pageSize)
                .limit(pageSize)
                .toList();
    }

    public List<CurrentCasesByLocalGeographicArea> findByCity(String city) {
        return currentCases.stream()
                .filter(item -> item.getLocation().startsWith(city))
                .toList();
    }

    public long totalPopulationCaseDataByCity(String city) {
        return currentCases.stream()
                .filter(item -> item.getLocation().startsWith(city))
                .mapToLong(CurrentCasesByLocalGeographicArea::getPopulationCaseData)
                .sum();
    }

    public Map<String, Integer> locationPopulationMapByCity(String city) {
        var populationCaseDataComparator = Comparator.comparing(CurrentCasesByLocalGeographicArea::getPopulationCaseData).reversed();
        Map<String, Integer> locationPopulationCaseDataMap = currentCases.stream()
                .filter(item -> item.getPopulationCaseData() != null)   // include only elements where the PopulationCaseData is not null
                .sorted(populationCaseDataComparator)                   // sort the elements in descending order by PopulationCaseData
                .filter(item -> item.getLocation().startsWith(city))    // include only elements that starts with the city name
                .collect(Collectors.toMap(                              // collect the elements in the stream into a Map
                        // instead of using the method expression `CurrentCasesByLocalGeographicArea::getLocation` we are going to use a lambda expression to remove the "City - " prefix for cities with multiple lcoations
                        item -> item.getLocation().contains(" - ") ? item.getLocation().replaceAll(city + " - ","") : item.getLocation(),   // the property to use for the Key of the map
                        CurrentCasesByLocalGeographicArea::getPopulationCaseData,   // the property to use for the Value of the map
                        (oldValue, newValue) -> oldValue,                           // if a value already exists for the key that don't overwrite with new value
                        LinkedHashMap::new                                          // a LinkHasMap allows to maintain the order of the elements
                        )
                );

        return locationPopulationCaseDataMap;
    }
}
