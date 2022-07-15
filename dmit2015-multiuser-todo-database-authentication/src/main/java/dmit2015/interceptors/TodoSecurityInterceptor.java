package dmit2015.interceptors;

import jakarta.inject.Inject;
import jakarta.interceptor.AroundInvoke;
import jakarta.interceptor.InvocationContext;
import jakarta.security.enterprise.SecurityContext;

public class TodoSecurityInterceptor {

    @Inject
    private SecurityContext securityContext;

    @AroundInvoke
    public Object verifyAccess(InvocationContext ic) throws Exception {
        String methodName = ic.getMethod().getName();
        // Only IT role are allowed to call the methods that begin with delete or remove
        if (methodName.startsWith("delete") || methodName.startsWith("remove")) {
            boolean isInItRole = securityContext.isCallerInRole("IT");
            if (!isInItRole) {
                throw new RuntimeException("Access denied. Only IT role have permission to execute this method");
            }
        }
        // Only Sales and Shipping role allowed to call methods that begin with add or create
        if (methodName.startsWith("add") || methodName.startsWith("create")) {
            boolean isInSalesOrShippingRole = securityContext.isCallerInRole("Sales")
                    || securityContext.isCallerInRole("Shipping");
            if (!isInSalesOrShippingRole) {
                throw new RuntimeException("Access denied. Only Sales and Shipping have permission to execute this method");
            }
        }

        return ic.proceed();
    }

}
