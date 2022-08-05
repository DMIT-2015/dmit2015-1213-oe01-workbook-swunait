package common.ejb;

import jakarta.annotation.Resource;
import jakarta.ejb.Schedule;
import jakarta.ejb.Schedules;
import jakarta.ejb.Singleton;
import jakarta.ejb.Startup;
import jakarta.ejb.Timer;
import jakarta.inject.Inject;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import java.util.logging.Logger;

@Singleton                // Instruct the container to create a single instance of this EJB
@Startup                // Create this EJB is created when this app starts
public class AutomaticTimersBean {        // Also known as Calendar-Based Timers

        private Logger _logger = Logger.getLogger(AutomaticTimersBean.class.getName());

        /**
         * Assuming you have define the following entries in your META-INF/microprofile-config.properties file
             ca.dmit2015.config.SYSADMIN_EMAIL=yourUsername@yourEmailServer

         * This code assumes that this project is configured to use Eclipse Microprofile.
         * You can add the following to pom.xml to enable Eclipse Microprofile

        <dependency>
            <groupId>org.eclipse.microprofile</groupId>
            <artifactId>microprofile</artifactId>
            <version>5.0</version>
            <type>pom</type>
            <scope>provided</scope>
        </dependency>

         */
        @Inject
        @ConfigProperty(name="ca.dmit2015.config.SYSADMIN_EMAIL", defaultValue = "")
        private String mailToAddress;

        @Inject
        private EmailSessionBean mail;

        private void sendEmail(Timer timer) {
                if (!mailToAddress.isBlank()) {
                        String mailSubject = timer.getInfo().toString();
                        String mailText = String.format("You have a %s on %s %s, %s  ",
                                timer.getInfo().toString(),
                                timer.getSchedule().getDayOfWeek(),
                                timer.getSchedule().getMonth(),
                                timer.getSchedule().getYear()
                        );
                        try {
                                // mail.sendTextEmail(mailToAddress, mailSubject, mailText);
                                _logger.info("mailSubject: " + mailSubject);
                                _logger.info("mailText: " + mailText);
                                _logger.info("Successfully sent email to " + mailToAddress);
                        } catch (Exception e) {
                                e.printStackTrace();
                                _logger.fine("Error sending email with exception " + e.getMessage());
                        }
                }
        }

        // @Schedules({
        //         @Schedule(second = "0", minute ="50", hour = "9", dayOfWeek = "Mon,Wed", month = "Jan-Apr", year = "2022", info ="DMIT2015-OA01 Meeting", persistent = false),
        //         @Schedule(second = "0", minute ="50", hour = "7", dayOfWeek = "Tue", month = "Jan-Apr", year = "2022", info ="DMIT2015-OA01 Meeting", persistent = false)
        // })
        public void dmit2015SectionOA01ClassNotifiation(Timer timer) {
                sendEmail(timer);
        }

        @Schedule(second = "0", minute ="53", hour = "20", dayOfWeek = "Tue,Thu", month = "May-Aug", year = "2022", info ="DMIT2015-OE01 Meeting", persistent = false)
        public void dmit2015SectionOE01ClassNotifiation(Timer timer) {
                System.out.println("timer firing");
                sendEmail(timer);
        }

}