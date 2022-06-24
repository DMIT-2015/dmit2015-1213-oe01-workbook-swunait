package org.openweathermap.api;

import jakarta.inject.Inject;
import org.apache.maven.model.Model;
import org.apache.maven.model.io.xpp3.MavenXpp3Reader;
import org.codehaus.plexus.util.xml.pull.XmlPullParserException;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit5.ArquillianExtension;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.maven.Maven;
import org.jboss.shrinkwrap.resolver.api.maven.PomEquippedResolveStage;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(ArquillianExtension.class)
public class CurrentWeatherApiResponseMpRestClient_ArquillianIT { // The class must be declared as public

    static String mavenArtifactIdId;

    @Deployment
    public static WebArchive createDeployment() throws IOException, XmlPullParserException {
        PomEquippedResolveStage pomFile = Maven.resolver().loadPomFromFile("pom.xml");
        MavenXpp3Reader reader = new MavenXpp3Reader();
        Model model = reader.read(new FileReader("pom.xml"));
        mavenArtifactIdId = model.getArtifactId();
        final String archiveName = model.getArtifactId() + ".war";
        return ShrinkWrap.create(WebArchive.class, archiveName)
                .addAsLibraries(pomFile.resolve("org.codehaus.plexus:plexus-utils:3.4.2").withTransitivity().asFile())
                .addPackage("org.openweathermap.api")
//                .addClasses(CurrentWeatherApiResponse.class, CurrentWeatherApiResponseMpRestClient.class)
                .addAsManifestResource(new File("src/main/resources/META-INF/microprofile-config.properties"))
                // .addAsWebInfResource(new File("src/main/webapp/WEB-INF/beans.xml"));
                .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml");
    }

    @Inject
    @RestClient
    private CurrentWeatherApiResponseMpRestClient _currentweatherapiresponseMpRestClient;

    @Inject
    @ConfigProperty(name = "org.openweathermap.api.ApiKey") // The name is defined in src/main/resources/META-INF/microprofile-config.properties file or an O/S environment variable
    private String openweatherApiKey;

    @Inject
    @ConfigProperty(name = "org.openweathermap.api.Units") // The name is defined in src/main/resources/META-INF/microprofile-config.properties file or an O/S environment variable
    private String units;    // The units of measurement: standard, metric, imperial

    @ParameterizedTest
    @CsvSource({
            "Vancouver, 16",
            "Calgary, 11",
            "Regina, 23",
            "Winnipeg, 27"
    })
    void shouldFindByCity(String city, double expectedTemp) {
        assertNotNull(_currentweatherapiresponseMpRestClient);
        // Arrange and Act
        CurrentWeatherApiResponse apiResponse = _currentweatherapiresponseMpRestClient.findByCity(city, openweatherApiKey, units);
        // Assert
        assertEquals(city, apiResponse.getName());
        assertEquals(expectedTemp, apiResponse.getMain().getTemp(), 1);
    }

}