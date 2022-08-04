package dmit2015.client;

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;
import java.util.List;

/**
 * The baseUri for the web service be set in either microprofile-config.properties (recommended)
 * or in this file using @RegisterRestClient(baseUri = "http://server/path").
 *
 * To set the baseUri in microprofile-config.properties:
 *    1) Open src/main/resources/META-INF/microprofile-config.properties
 *    2) Add a key/value pair in the following format:
 *          package-name.ClassName/mp-rest/url=baseUri
 *       For example:
 *          package-name:    dmit2015.client
 *          ClassName:       TodoService
 *          baseUri:         https://localhost:8443/contextName
 *       The key/value pair you need to add is:
 *          dmit2015.client.TodoService/mp-rest/url=https://localhost:8443/backend/webapi
 *
 *
 * To use the client interface from an environment does support CDI, add @Inject and @RestClient before the field declaration such as:
 *
 *     @Inject
 *     @RestClient
 *     private TodoService _employeeService;
 *
 * To use the client interface from an environment that does not support CDI, you can use the RestClientBuilder class to programmatically build an instance as follows:
 *
 *      URI apiURi = new URI("http://sever/contextName");
 *      TodoService _TodoService = RestClientBuilder.newBuilder()
 *                 .baseUri(apiURi)
 *                 .build(TodoService.class);
 *
 */
@RegisterRestClient
@Path("/TodoDtos")
public interface TodoService {

    @GET
    List<Todo> findAll();

    @GET
    @Path("/user")
    List<Todo> findAllForUser(@HeaderParam("Authorization") String authorization);

    @GET
    @Path("{id}")
    Todo findOneById(@PathParam("id") Long id);

    @POST
    Response create(Todo newTodo, @HeaderParam("Authorization") String authorization);

    @PUT
    @Path("/{id}")
    Response update(@PathParam("id") Long id, Todo updatedTodo, @HeaderParam("Authorization") String authorization);

    @DELETE
    @Path("/{id}")
    Response delete(@PathParam("id") Long id, @HeaderParam("Authorization") String authorization);

}