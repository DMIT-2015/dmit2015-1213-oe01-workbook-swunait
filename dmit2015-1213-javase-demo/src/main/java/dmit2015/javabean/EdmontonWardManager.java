package dmit2015.javabean;

import lombok.Getter;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

public class EdmontonWardManager {

    @Getter
    private List<EdmontonWard> wards;

    // Implement Class-Based Singleton as shown in https://www.baeldung.com/java-singleton
    private static EdmontonWardManager INSTANCE;
    private EdmontonWardManager() {
        loadData();
    }
    public static EdmontonWardManager getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new EdmontonWardManager();
        }
        return INSTANCE;
    }

    private void loadData() {
        try {
            Path csvPath = Path.of(getClass().getClassLoader().getResource("data/csv/City_of_Edmonton_Ward_Boundary_and_Council_Composition__Current.csv").toURI());
//            Path csvPath = Path.of("d:/temp/City_of_Edmonton_Ward_Boundary_and_Council_Composition__Current.csv");
            Stream<String> linesStream = Files.lines(csvPath);
            wards = linesStream
                    .skip(1)
                    .map(line -> EdmontonWard.parseCsv(line).orElse(null))
                    .filter(Objects::nonNull)
                    .toList();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public int count() {
        return wards.size();
    }

    public Optional<EdmontonWard> findByName1(String name1) {
        return wards.stream()
                .filter(item -> item.getName1().equalsIgnoreCase(name1))
                .findFirst();
    }

    public Optional<EdmontonWard> findByName2(String name2) {
        return wards.stream()
                .filter(item -> item.getName2().equalsIgnoreCase(name2))
                .findFirst();
    }

    public Optional<EdmontonWard> findByCouncillor(String councillor) {
        return wards.stream()
                .filter(item -> item.getCouncillor().contains(councillor))
                .findFirst();
    }
    public String findName1ByCouncillor(String councillor) {
        Optional<EdmontonWard> optionalEdmontonWard = findByCouncillor(councillor);
        String wardName = "Unknown";
        if (optionalEdmontonWard.isPresent()) {
            wardName = optionalEdmontonWard.get().getName1();
        }
        return wardName;
    }

    public String findWardName1(double latitude, double longitude) {
        String wardName1 = "Unknown";

        Point locationPoint = new GeometryFactory().createPoint(new Coordinate(longitude,latitude));

        Optional<EdmontonWard> optionalEdmontonWard = wards.stream()
                .filter(item -> item.getGeometryMultipolygon().contains(locationPoint))
                .findFirst();
        if (optionalEdmontonWard.isPresent()) {
            wardName1 = optionalEdmontonWard.get().getName1();
        }

        return wardName1;
    }

    public String findWardName2(double latitude, double longitude) {
        String wardName2 = "Unknown";

        Point locationPoint = new GeometryFactory().createPoint(new Coordinate(longitude,latitude));

        Optional<EdmontonWard> optionalEdmontonWard = wards.stream()
                .filter(item -> item.getGeometryMultipolygon().contains(locationPoint))
                .findFirst();
        if (optionalEdmontonWard.isPresent()) {
            wardName2 = optionalEdmontonWard.get().getName2();
        }

        return wardName2;
    }
}