package dmit2015.javabean;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.io.WKTReader;

import java.util.Optional;

/**
 * https://data.edmonton.ca/Transportation/Scheduled-Photo-Enforcement-Zone-Centre-Points/akzz-54k3
 *
 * The direct link to download the CSV file is: https://data.edmonton.ca/api/views/akzz-54k3/rows.csv?accessType=DOWNLOAD
 *
 * This dataset represents the centre points of each Zone or Operational Area where Photo Enforcement is "scheduled" to be conducted. 
 * An enforcement unit can be found anywhere along the area of the Zone. 
 * An enforcement unit may not be able to operate at the specified zone and subsequently move to a zone that is not scheduled for enforcement. 
 * The centre points are extracted for mapping purposes only and are not intended to imply or suggest that is where the Photo Enforcement is being conducted. 
 * Designated Zones are continuously reviewed, revised, added, removed and otherwise updated in accordance to the guidelines for establishing a Zone for photo enforcement. 
 * Automated enforcement is expected to be operating at the locations indicated. 
 * Please be advised that automated enforcement may be used at other locations within Edmonton as well. 
 * Locations selected for enforcement may be removed or added as determined by weather, road conditions, roadway closures or construction, equipment issues or other unforeseen circumstances.
 * Each enforcement site has one or more reasons for why enforcement is taking place. The list of reasons are:
 * a) Areas or intersections where conventional enforcement is unsafe or ineffective;
 * b) Areas or intersections with an identifiable, documented history of collisions;
 * c) Areas or intersections with an identifiable, documented history of speeding problems;
 * d) Intersections with an identifiable, documented history of offences;
 * e) Intersections near schools, post-secondary institutions, or other areas with high pedestrian volumes;
 * f) School and playground zones or areas;
 * g) Construction zones; or
 * h) Areas where the public or a community has expressed concerns related to speeding.
 * 
 * Please refer to the FAQ the City has available in regards to Photo Enforcement.
 * https://www.edmonton.ca/transportation/Mobile_Photo_Enforcement_FAQ.pdf
 * 
 * @author City of Edmonton
 *
 */
@Data
@NoArgsConstructor
public class EnforcementZoneCentre {
	/**
	 * A system generated number assigned to any newly created enforcement zone
	 */
	private int siteId;
		
	/**
	 * A description of where along the Road Name that the enforcement zone is located
	 */
	private String locationDescription;
	
	/**
	 * A speed limit that is being monitored and enforced (km/h)
	 */
	private int speedLimit;
	
	/**
	 * A reason code for why this location was chosen for photo enforcement: 
	 * a) Areas or intersections where conventional enforcement is unsafe or ineffective; 
	 * b) Areas or intersections with an identifiable, documented history of collisions; 
	 * c) Areas or intersections with an identifiable, documented history of speeding problems; 
	 * d) Intersections with an identifiable, documented history of offences; 
	 * e) Intersections near schools, post-secondary institutions, or other areas with high pedestrian volumes; 
	 * f) School and playground zones or areas; 
	 * g) Construction zones; or 
	 * h) Areas where the public or a community has expressed concerns related to speeding.
	 */
	private String reasonCodes;
	
	/**
	 * The latitude value for the centre of the enforcement zone
	 */
	private double latitude;

	/**
	 * The longitude value for the centre of the enforcement zone
	 */
	private double longitude;

	/**
	 * The combined latitude/longitude values used to geo locate the centre of the enforcement zone for mapping purposes
	 */
	private Point geoLocation;

	public static Optional<EnforcementZoneCentre> parseCsv(String line) {
		Optional<EnforcementZoneCentre> optionalEnforcementZoneCentre = Optional.empty();

		final String DELIMITER = ",(?=(?:[^\\\"]*\\\"[^\\\"]*\\\")*[^\\\"]*$)";
		String[] tokens = line.split(DELIMITER, -1);  // The -1 limit allows for any number of fields and not discard trailing empty fields
		if (tokens.length == 7) {
			EnforcementZoneCentre parsedEnforcementZoneCentre = new EnforcementZoneCentre();
			try {
				/* The order of the columns in the line are:
				0 - Site ID
				1 - Location Description
				2 - Speed Limit
				3 - Reason Code(s)
				4 - Latitude
				5 - Longitude
				6 - Geo Location
				 */
				parsedEnforcementZoneCentre.setSiteId(Short.parseShort(tokens[0]));
				// Store null for LocationDescription if the column value is an empty string
				parsedEnforcementZoneCentre.setLocationDescription(tokens[1].isBlank() ? null : tokens[1]);
				parsedEnforcementZoneCentre.setSpeedLimit(Short.parseShort(tokens[2]));
				// Store null for Reason Codes if the column value is an empty string
				parsedEnforcementZoneCentre.setReasonCodes(tokens[3].isBlank() ? null : tokens[3]);
				parsedEnforcementZoneCentre.setLatitude(Double.parseDouble(tokens[4]));
				parsedEnforcementZoneCentre.setLongitude(Double.parseDouble(tokens[5]));

				// The geoLocation column contains quotation around the value that we need to remove
				// The geoLocation column uses a comma instead of a space between he coordinates that we need to remove
				// The geoLocation column in the dataset is missing the POINT keyword before the opening round bracket that we need to add before reading the value
				Point geoLocation = (Point) new WKTReader().read(
						"POINT" +	// add the POINT keyword before the value
							tokens[6].replaceAll("\"","")	// Remove the quotation characters from the value
									.replaceAll(","," ")	// Replace the comma character with a space character
				);
				parsedEnforcementZoneCentre.setGeoLocation(geoLocation);

				optionalEnforcementZoneCentre = Optional.of(parsedEnforcementZoneCentre);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return optionalEnforcementZoneCentre;
	}
}