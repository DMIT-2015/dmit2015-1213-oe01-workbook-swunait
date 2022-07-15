package common.security;

import jakarta.inject.Inject;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;

@WebListener
public class ApplicationStartupSecurityListener implements ServletContextListener {

    @Inject
    private CallerUserRepository _callerUserRepository;

    public ApplicationStartupSecurityListener() {
    }

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        /* This method is called when the servlet context is initialized(when the Web application is deployed). */
        if (_callerUserRepository.count() == 0) {
            try {
                Path csvPath = Path.of("/home/user2015/Downloads/employees.csv");
                Stream<String> linesStream = Files.lines(csvPath);
                linesStream
                        .skip(1)    // Skip the first line as it contains column names
                        .forEach(line -> {
                            final String DELIMITER = ",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)";
                            String[] tokens = line.split(DELIMITER, -1);  // The -1 limit allows for any number of fields and not discard trailing empty fields
                            // The order of the columns in the CSV file are:
                            // 0 - FirstName
                            // 1 - LastName
                            // 2 - LogonName
                            // 3 - PhoneNumber
                            // 4 - Department
                            if (tokens.length == 5) {
                                CallerUser parsedCallerUser = new CallerUser();
                                try {
                                    parsedCallerUser.setUsername(tokens[2]);
                                    String[] groups = {tokens[4]};
                                    _callerUserRepository.add(parsedCallerUser, "Password2015", groups);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                System.out.printf("Created %d sample accounts", _callerUserRepository.count());
            } catch (Exception ex) {
                ex.printStackTrace();
            }

        } else {
            System.out.println("Application has user accounts");
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        /* This method is called when the servlet Context is undeployed or Application Server shuts down. */
    }

}
