package dmit2015.javabean;

import lombok.*;
import org.locationtech.jts.geom.MultiPolygon;
import org.locationtech.jts.io.WKTReader;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

/**
 * https://data.edmonton.ca/Community-Services/COVID-19-in-Alberta-Current-cases-by-local-geograp/ix8f-s9xp
 *
 * Direct link to download CSV file: https://data.edmonton.ca/api/views/ix8f-s9xp/rows.csv?accessType=DOWNLOAD
 *
 * This is a mirror of the data presented in the map view at https://www.alberta.ca/stats/covid-19-alberta-statistics.htm ('Geospatial' tab).
 *
 */
@Data
public class CurrentCasesByLocalGeographicArea {

    private LocalDate date;

    private String location;

    private int populationVaccinationData;

    private Integer populationCaseData; // Must use Numeric Wrapper Class primitive values which can be NULLABLE

    private int totalCases;

    private int activeCases;

    private Double activeCasesRate; // Must use Numeric Wrapper Class primitive values which can be NULLABLE

    private Integer recoveredCases; // Must use Numeric Wrapper Class primitive values which can be NULLABLE

    private int deaths;

    private int dose1;

    private int dose2;

    private int totalDoesAdministered;

    private double percentPopulationOneDose;

    private double percentPopulationFullyImmunized;

    private MultiPolygon polygon;

    public static Optional<CurrentCasesByLocalGeographicArea> parseCsv(String line) {
        Optional<CurrentCasesByLocalGeographicArea> optionalCurrentCasesByLocalGeographicArea = Optional.empty();

        final String DELIMITER = ",(?=(?:[^\\\"]*\\\"[^\\\"]*\\\")*[^\\\"]*$)";
        String[] tokens = line.split(DELIMITER, -1);  // The -1 limit allows for any number of fields and not discard trailing empty fields
        if (tokens.length == 15) {
            CurrentCasesByLocalGeographicArea parsedCurrentCasesByLocalGeographicArea = new CurrentCasesByLocalGeographicArea();
            try {
				/* There are 14 columns in the dataset and with the following column order:
				0 - Date
				1 - Location
				2 - Population (vaccination data)
				3 - Population (case data)
				4 - Total Cases
				5 - Active Cases
				6 - NULLABLE Active Case Rate
				7 - NULLABLE Recovered Cases
				8 - Deaths
				9 - # of population with at least 1 dose
				10 - # of population fully immunized
				11 - Total Doses Administered
				12 - Percent of population who received at least one dose
				13 - Polygon
				 */

                // Parse the data from a String to LocalDate using the pattern yyyy/MM/dd that is being used in the csv file
                var dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");
                parsedCurrentCasesByLocalGeographicArea.setDate(LocalDate.parse(tokens[0], dtf));

                // Remove quotation characters if there are any surrounding the value
                parsedCurrentCasesByLocalGeographicArea.setLocation(tokens[1].replaceAll("\"", ""));

                parsedCurrentCasesByLocalGeographicArea.setPopulationVaccinationData(Integer.parseInt(tokens[2]));

                // There is one record (Cochrane - Springbank (Nearby Rocky View County) that has no data on Population Case Data
                parsedCurrentCasesByLocalGeographicArea.setPopulationCaseData(tokens[3].isEmpty() ? null : Integer.parseInt(tokens[3]));

                parsedCurrentCasesByLocalGeographicArea.setTotalCases(Integer.parseInt(tokens[4]));
                parsedCurrentCasesByLocalGeographicArea.setActiveCases(Integer.parseInt(tokens[5]));

                // The next two columns often does not have any values (are empty/blank)
                parsedCurrentCasesByLocalGeographicArea.setActiveCasesRate(tokens[6].isBlank() ? null : Double.parseDouble(tokens[6]));
                parsedCurrentCasesByLocalGeographicArea.setRecoveredCases(tokens[7].isBlank() ? null : Integer.parseInt(tokens[7]));

                parsedCurrentCasesByLocalGeographicArea.setDeaths(Integer.parseInt(tokens[8]));
                parsedCurrentCasesByLocalGeographicArea.setDose1(Integer.parseInt(tokens[9]));
                parsedCurrentCasesByLocalGeographicArea.setDose2(Integer.parseInt(tokens[10]));
                parsedCurrentCasesByLocalGeographicArea.setTotalDoesAdministered(Integer.parseInt(tokens[11]));
                parsedCurrentCasesByLocalGeographicArea.setPercentPopulationOneDose(Double.parseDouble(tokens[12]));
                parsedCurrentCasesByLocalGeographicArea.setPercentPopulationFullyImmunized(Double.parseDouble(tokens[13]));

                // Remove quotation characters that use to enclose the value
                String wktText = tokens[14].replaceAll("\"", "");
                MultiPolygon multiPolygon = (MultiPolygon) new WKTReader().read(wktText);
                parsedCurrentCasesByLocalGeographicArea.setPolygon(multiPolygon);

                optionalCurrentCasesByLocalGeographicArea = Optional.of(parsedCurrentCasesByLocalGeographicArea);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return optionalCurrentCasesByLocalGeographicArea;
    }

}
