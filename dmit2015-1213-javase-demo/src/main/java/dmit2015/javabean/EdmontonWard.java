package dmit2015.javabean;

import lombok.Data;
import org.locationtech.jts.geom.MultiPolygon;
import org.locationtech.jts.io.WKTReader;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

/**
 * City of Edmonton Ward Boundary and Council Composition: Current
 *
 * This dataset provides the current (as of the date that this dataset was last refreshed) geometric polygons of the City of Edmonton's civic ward boundaries and the associated Council member in office.
 *
 * @link https://data.edmonton.ca/Administrative/City-of-Edmonton-Ward-Boundary-and-Council-Composi/b4er-5rp2
 *
 * @author data.edmonton.ca
 * @version 2022.05.16
 */
@Data
public class EdmontonWard {

    private String name1;

    private String name2;

    private LocalDate effectiveStartDate;

    private String councillor;

    private MultiPolygon geometryMultipolygon;

    public static Optional<EdmontonWard> parseCsv(String line) {
        Optional<EdmontonWard> optionalEdmontonWard = Optional.empty();

        final String DELIMITER = ",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)";
        String[] tokens = line.split(DELIMITER, -1);  // The -1 limit allows for any number of fields and not discard trailing empty fields
        /* There are 7 columns in the dataset in the following order:
        0 - Name_1 (Name only)
        1 - Name_2 (Name with Ward suffix)
        2 - Effective Start Date
        3 - NULLABLE Effect End Date
        4 - Councillor
        5 - NULLABLE councillor2
        6 - Geometry Multipolyon
         */
        if (tokens.length == 7) {
            EdmontonWard parsedEdmontonWard = new EdmontonWard();
            try {
                parsedEdmontonWard.setName1(tokens[0]);
                parsedEdmontonWard.setName2(tokens[1]);
                parsedEdmontonWard.setCouncillor(tokens[4]);

                var dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                parsedEdmontonWard.setEffectiveStartDate(LocalDate.parse(tokens[2], dtf));

                String polygonWellKnownText = tokens[6].replaceAll("\"", "");
                MultiPolygon multiPolygon = (MultiPolygon) new WKTReader().read(polygonWellKnownText);
                parsedEdmontonWard.setGeometryMultipolygon(multiPolygon);

                optionalEdmontonWard = Optional.of(parsedEdmontonWard);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return optionalEdmontonWard;
    }
}
