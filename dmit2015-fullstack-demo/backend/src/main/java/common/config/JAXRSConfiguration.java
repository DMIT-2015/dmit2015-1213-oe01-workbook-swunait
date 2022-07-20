package common.config;

import jakarta.annotation.security.DeclareRoles;
import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;
import org.eclipse.microprofile.auth.LoginConfig;

@ApplicationPath("/restapi")
@LoginConfig(authMethod="MP-JWT", realmName="MP JWT Realm")
@DeclareRoles({"Sales","Shipping","IT"})
public class JAXRSConfiguration extends Application {

}