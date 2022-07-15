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

//	@DataSourceDefinition(
//		name="java:app/datasources/localMssqlDS",
//		className="com.microsoft.sqlserver.jdbc.SQLServerDataSource",
//		url="jdbc:sqlserver://localhost;databaseName=DMIT2015_1213_CourseDB;TrustServerCertificate=true",
//		user="user2015",
//		password="Password2015"),

//	@DataSourceDefinition(
//		name="java:app/datasources/oracleUser2015DS",
//		className="oracle.jdbc.xa.client.OracleXADataSource",
//		url="jdbc:oracle:thin:@localhost:1521/xepdb1",
//		user="user2015",
//		password="Password2015"),

//	@DataSourceDefinition(
//		name="java:app/datasources/oracleHrDS",
//		className="oracle.jdbc.xa.client.OracleXADataSource",
//		url="jdbc:oracle:thin:@localhost:1521/xepdb1",
//		user="hr",
//		password="Password2015"),
//
//	@DataSourceDefinition(
//		name="java:app/datasources/oracleOeDS",
//		className="oracle.jdbc.xa.client.OracleXADataSource",
//		url="jdbc:oracle:thin:@localhost:1521/xepdb1",
//		user="oe",
//		password="Password2015"),

//	@DataSourceDefinition(
//		name="java:app/datasources/mysqlDS",
//		className="com.mysql.cj.jdbc.MysqlXADataSource",
//		url="jdbc:mysql://localhost/DMIT2015_1213_CourseDB",
//		user="user2015",
//		password="Password2015"),

//	@DataSourceDefinition(
//		name="java:app/datasources/mariadbDS",
//		className="org.mariadb.jdbc.MySQLDataSource",
//		url="jdbc:mariadb://localhost/DMIT2015_1213_CourseDB",
//		user="user2015",
//		password="Password2015"),

//	@DataSourceDefinition(
//		name="java:app/datasources/postgresqlDS",
//		className="org.postgresql.xa.PGXADataSource",
//		url="jdbc:postgresql://localhost/DMIT2015_1213_CourseDB",
//		user="user2015",
//		password="Password2015"),

//	@DataSourceDefinition(
//		name="java:app/datasources/hsqldatabaseDS",
//		className="org.hsqldb.jdbc.JDBCDataSource",
////		url="jdbc:hsqldb:mem:dmit2015hsqldb",
//		url="jdbc:hsqldb:file:~/jdk/databases/hsqldb/DMIT2015_1213_CourseDB",
//		user="user2015",
//		password="Password2015"),

})

@CustomFormAuthenticationMechanismDefinition(
        loginToContinue = @LoginToContinue(
                loginPage="/customLogin.xhtml",
                useForwardToLogin = false,
                errorPage=""
        )
)

//@LdapIdentityStoreDefinition(
//        url = "ldap://192.168.101.198:389",
//        callerSearchBase = "ou=Departments,dc=dmit2015,dc=ca",
//        callerNameAttribute = "SamAccountName", // SamAccountName or UserPrincipalName
//        groupSearchBase = "ou=Departments,dc=dmit2015,dc=ca",
//        bindDn = "cn=DAUSTIN,ou=IT,ou=Departments,dc=dmit2015,dc=ca",
//        bindDnPassword = "Password2015",
//        priority = 5
//)

@DatabaseIdentityStoreDefinition(
        dataSourceLookup="java:app/datasources/h2databaseDS",
        callerQuery="SELECT password FROM CallerUser WHERE username = ?",
        groupsQuery="SELECT groupname FROM CallerGroup WHERE username = ? ",
        priority = 10
)

//@DatabaseIdentityStoreDefinition(
//		dataSourceLookup="java:app/datasources/remoteMssqlDS",
//		callerQuery="SELECT password FROM CallerUser WHERE username = ?",
//		groupsQuery="SELECT groupname FROM CallerGroup WHERE username = ? ",
//		priority = 10
//)

@FacesConfig
@ApplicationScoped
public class ApplicationConfig {

}