package dmit2015.interceptors;

import jakarta.inject.Inject;
import jakarta.interceptor.AroundInvoke;
import jakarta.interceptor.InvocationContext;
import jakarta.security.enterprise.SecurityContext;

public class AnonymousUserSecurityInterceptor {

  @Inject
  private SecurityContext securityContext;

  @AroundInvoke
  public Object verifyAccess(InvocationContext ic) throws Exception {
     // Only authenticated users can access this method
     if (securityContext.getCallerPrincipal() == null) {
       throw new RuntimeException("Access denied! Only authenticated users have permission to execute this method");
     }
     return ic.proceed();  
  }
}
