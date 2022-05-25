package dmit2015.javabean;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

public class EnforcementZoneCentreManager {

    private List<EnforcementZoneCentre> enforcementZoneCentres;

    // Using Thread Safe Singelton from https://www.journaldev.com/1377/java-singleton-design-pattern-best-practices-examples#thread-safe-singleton
    // Also known as Class-Based Singelton https://www.baeldung.com/java-singleton
    private static EnforcementZoneCentreManager INSTANCE;

    private EnforcementZoneCentreManager() {
        loadData();
    }

    public static synchronized EnforcementZoneCentreManager getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new EnforcementZoneCentreManager();
        }
        return INSTANCE;
    }

    private void loadData() {
        try {
            Path csvPath = Path.of(getClass().getClassLoader().getResource("data/csv/Scheduled_Photo_Enforcement_Zone_Centre_Points.csv").toURI());
//            Path csvPath = Path.of("d:/temp/Scheduled_Photo_Enforcement_Zone_Centre_Points.csv");
            Stream<String> linesStream = Files.lines(csvPath);
            enforcementZoneCentres = linesStream
                    .skip(1)
                    .map(line -> EnforcementZoneCentre.parseCsv(line).orElse(null))
                    .filter(Objects::nonNull)
                    .toList();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public int count() {
        return enforcementZoneCentres.size();
    }

    public List<EnforcementZoneCentre> findAll(int pageNumber, int pageSize) {
        return enforcementZoneCentres
                .stream()
                .skip((pageNumber - 1) * pageSize)
                .limit(pageSize)
                .toList();
    }

    public List<EnforcementZoneCentre> findByReason(String partialReason) {
        var locationDescriptionComparator = Comparator.comparing(EnforcementZoneCentre::getLocationDescription,
                Comparator.nullsLast(Comparator.naturalOrder()));
        var reasonCodeComparator = Comparator.comparing(EnforcementZoneCentre::getReasonCodes,
                Comparator.nullsLast(Comparator.naturalOrder()));
        return enforcementZoneCentres
                .stream()
                .filter(item -> item.getReasonCodes().contains(partialReason))
                .sorted(locationDescriptionComparator.thenComparing(reasonCodeComparator))
                .toList();
    }

    public List<EnforcementZoneCentre> findBySpeedLimit(int speedLimit) {
        return enforcementZoneCentres
                .stream()
                .filter(item -> item.getSpeedLimit() == speedLimit)
                .toList();
    }

    public Optional<EnforcementZoneCentre> findBySiteID(int siteID) {
        return enforcementZoneCentres.stream()
                .filter(item -> item.getSiteId() == siteID)
                .findFirst();
    }

    public List<Integer> findDistinctSpeedLimits() {
        return enforcementZoneCentres.stream()
                .map(EnforcementZoneCentre::getSpeedLimit)
                .distinct()
                .sorted()
                .toList();
    }

    public double highestSpeedLimit() {
        return enforcementZoneCentres.stream()
                .mapToDouble(EnforcementZoneCentre::getSpeedLimit)
                .max()
                .orElseThrow();
    }

    public double lowestSpeedLimit() {
        return enforcementZoneCentres.stream()
                .mapToDouble(EnforcementZoneCentre::getSpeedLimit)
                .min()
                .orElseThrow();
    }

    public double averageSpeedLimit() {
        return enforcementZoneCentres.stream()
                .mapToDouble(EnforcementZoneCentre::getSpeedLimit)
                .average()
                .orElseThrow();
    }
}
