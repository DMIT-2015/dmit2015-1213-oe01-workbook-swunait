package dmit2015.client;

import org.omnifaces.util.Faces;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Named;
import jakarta.servlet.ServletException;

@Named("firebaseLogout")
@RequestScoped
public class Logout {

    public void submit() throws ServletException {
        Faces.invalidateSession();
        Faces.redirect( Faces.getRequestContextPath() + "/firebaseLogin.xhtml?faces-redirect=true");
    }

}