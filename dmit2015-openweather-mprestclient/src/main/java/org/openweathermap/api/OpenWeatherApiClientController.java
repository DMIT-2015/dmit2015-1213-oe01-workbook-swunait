package org.openweathermap.api;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import lombok.Getter;
import lombok.Setter;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.omnifaces.util.Messages;

import java.io.Serializable;

@Named("currentOpenWeatherApiClientController")
@SessionScoped
public class OpenWeatherApiClientController implements Serializable {

    @Inject
    @ConfigProperty(name = "org.openweathermap.api.ApiKey") // The name is defined in src/main/resources/META-INF/microprofile-config.properties file or an O/S environment variable
    private String openweatherApiKey;

    @Inject
    @ConfigProperty(name = "org.openweathermap.api.DefaultCity") // The name is defined in src/main/resources/META-INF/microprofile-config.properties file or an O/S environment variable
    @Getter @Setter
    private String city;    // The city to get the weather for

    @Inject
    @ConfigProperty(name = "org.openweathermap.api.Units") // The name is defined in src/main/resources/META-INF/microprofile-config.properties file or an O/S environment variable
    private String units;    // // The units of measurement: standard, metric, imperial

    @Inject
    @RestClient // Inject the MP Rest Client
    private CurrentWeatherApiResponseMpRestClient _weatherRestClient;

    @Getter
    private CurrentWeatherApiResponse currentWeatherApiResponse;

    @PostConstruct
    public void init() {
        doSearchByCity();
    }

    public void doSearchByCity() {
        try {
            currentWeatherApiResponse = _weatherRestClient.findByCity(city, openweatherApiKey, units);
            String message = String.format("The current weather in %s is %.2f", currentWeatherApiResponse.getName(), currentWeatherApiResponse.getMain().getTemp());
            Messages.addGlobalInfo(message);
        } catch (Exception ex) {
            ex.printStackTrace();
            Messages.addGlobalWarn("There are no weather data for {0}", city);
        }
    }

}