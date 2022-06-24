package org.openweathermap.api;

import jakarta.ws.rs.*;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@RegisterRestClient(baseUri = "https://api.openweathermap.org/data/2.5")
public interface CurrentWeatherApiResponseMpRestClient {

    @GET
    @Path("/weather")
    CurrentWeatherApiResponse findByCity(
            @QueryParam("q") String city,
            @QueryParam("appid") String apiKey,
            @QueryParam("units") String units
    );

}