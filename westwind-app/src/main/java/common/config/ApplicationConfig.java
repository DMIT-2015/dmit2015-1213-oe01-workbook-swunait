package common.config;

import jakarta.annotation.sql.DataSourceDefinition;
import jakarta.annotation.sql.DataSourceDefinitions;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.faces.annotation.FacesConfig;

@DataSourceDefinitions({


	@DataSourceDefinition(
		name="java:app/datasources/RemoteMssqlDMIT2015DS",
		className="com.microsoft.sqlserver.jdbc.SQLServerDataSource",
		url="jdbc:sqlserver://DMIT-Capstone1.ad.sast.ca;databaseName=DMIT2015_1213_E01_swu2015;TrustServerCertificate=true", // replace yourNaitUsername
		user="swu2015",	// replace yourNaitUsername
		password="RemotePassword200012345"), // replace 200012345 with your NAIT StudentID


})

@FacesConfig
@ApplicationScoped
public class ApplicationConfig {

}