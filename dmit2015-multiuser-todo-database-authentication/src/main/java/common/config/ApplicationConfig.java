package common.config;

import jakarta.annotation.sql.DataSourceDefinition;
import jakarta.annotation.sql.DataSourceDefinitions;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.faces.annotation.FacesConfig;
import jakarta.security.enterprise.authentication.mechanism.http.CustomFormAuthenticationMechanismDefinition;
import jakarta.security.enterprise.authentication.mechanism.http.LoginToContinue;
import jakarta.security.enterprise.identitystore.DatabaseIdentityStoreDefinition;

@DataSourceDefinitions({

        @DataSourceDefinition(
                name = "java:app/datasources/h2databaseDS",
                className = "org.h2.jdbcx.JdbcDataSource",
//                 url="jdbc:h2:file:~/jdk/databases/h2/DMIT2015_1213_CourseDB;MODE=LEGACY;",
                url = "jdbc:h2:mem:test;DB_CLOSE_DELAY=-1;MODE=LEGACY;",
                user = "user2015",
                password = "Password2015"),

//	@DataSourceDefinition(
//		name="java:app/datasources/remoteMssqlDS",
//		className="com.microsoft.sqlserver.jdbc.SQLServerDataSource",
//		url="jdbc:sqlserver://DMIT-Capstone1.ad.sast.ca;databaseName=DMIT2015_1213_E01_yourNaitUsername;TrustServerCertificate=true",   // Replace yourNaitUsername with your NAIT username
//		user="yourNaitUsername",  //  Replace yourNaitUsername with your NAIT username
//		password="RemotePassword.200012345"),    // Replace 200012345 with your NAIT StudentID


})

@CustomFormAuthenticationMechanismDefinition(
        loginToContinue = @LoginToContinue(
                loginPage="/customLogin.xhtml",
                useForwardToLogin = false,
                errorPage=""
        )
)


@DatabaseIdentityStoreDefinition(
        dataSourceLookup="java:app/datasources/h2databaseDS",
        callerQuery="SELECT password FROM CallerUser WHERE username = ?",
        groupsQuery="SELECT groupname FROM CallerGroup WHERE username = ? ",
        priority = 10
)


@FacesConfig
@ApplicationScoped
public class ApplicationConfig {

}