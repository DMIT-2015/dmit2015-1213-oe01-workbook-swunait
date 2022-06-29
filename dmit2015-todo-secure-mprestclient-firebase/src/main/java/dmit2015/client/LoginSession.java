package dmit2015.client;

import lombok.Getter;
import lombok.Setter;

import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Named;
import org.omnifaces.util.Faces;
import org.omnifaces.util.Utils;

import java.io.Serializable;

@Named("firebaseLoginSession")
@SessionScoped
public class LoginSession implements Serializable {
    private static final long serialVersionUID = 1L;

    @Getter @Setter
    private String username;

    @Getter @Setter
    private String token;

    public String checkForToken() {
        String nextPage = null;

        if (token == null) {
            nextPage = "/firebaseLogin?requestURI=" + Utils.encodeURI(Faces.getRequestURI());
        }

        return nextPage;
    }
}