package dmit2015.client;

import jakarta.json.JsonObject;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.QueryParam;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

/**
 *
 * To set the baseUri in microprofile-config.properties:
 * 1) Open src/main/resources/META-INF/microprofile-config.properties
 * 2) Add a key/value pair in the following format:
 *       package-name.ClassName/mp-rest/url=baseUri
 *       For example:
 *          package-name:    dmit2015.client
 *          ClassName:       FirebaseAuthService
 *          baseUri:         https://identitytoolkit.googleapis.com/v1
 *       The key/value pair you need to add is:
 *          dmit2015.client.FirebaseAuthService/mp-rest/url=https://identitytoolkit.googleapis.com/v1
 *
 * Firebase auth request body payload
 * <code>
 *     {
 *         "email": "samplename@gmail.com",
 *         "password": "SamplePassword",
 *         "returnSecureToken": true
 *     }
 * </code>
 *
 * @link https://firebase.google.com/docs/reference/rest/auth#section-create-email-password
 * @link https://firebase.google.com/docs/reference/rest/auth#section-sign-in-email-password
 * @link https://firebase.google.com/docs/database/rest/auth#firebase_id_tokens
 */

@RegisterRestClient(baseUri = "https://identitytoolkit.googleapis.com/v1")
public interface FirebaseAuthService {

    @POST
    @Path("/accounts:signUp")
    JsonObject signUp(@QueryParam("key") String apiKey, JsonObject payload);

    // The response body payload property idToken contains the token we need to use for future requests
    @POST
    @Path("/accounts:signInWithPassword")
    JsonObject signIn(@QueryParam("key") String apiKey, JsonObject payload);

}