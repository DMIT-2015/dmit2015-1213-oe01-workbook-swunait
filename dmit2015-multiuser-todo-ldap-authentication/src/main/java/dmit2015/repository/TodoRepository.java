package dmit2015.repository;

import common.jpa.AbstractJpaRepository;
import dmit2015.entity.Todo;
import dmit2015.interceptors.AnonymousUserSecurityInterceptor;
import dmit2015.interceptors.TodoSecurityInterceptor;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.interceptor.Interceptors;
import jakarta.security.enterprise.SecurityContext;
import jakarta.transaction.Transactional;

import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
@Transactional
@Interceptors({AnonymousUserSecurityInterceptor.class, TodoSecurityInterceptor.class})
public class TodoRepository extends AbstractJpaRepository<Todo, Long> {

    public TodoRepository() {
        super(Todo.class);
    }

    @Inject
    private SecurityContext _securityContext;

    public void add(Todo newTodo) {
        String username = _securityContext.getCallerPrincipal().getName();
        newTodo.setUsername(username);
        super.add(newTodo);
    }
    public List<Todo> findAll() {
        List<Todo> resultList = null;

        // Return an empty list if the user has not been authenticated
        if (_securityContext.getCallerPrincipal() == null ) {
            resultList = new ArrayList<>();
        } else if (_securityContext.isCallerInRole("IT") ) {     // Return all Todo entity if the login user belongs to the IT role
            resultList = super.findAll();
        } else { // Return only Todo associated the current authenticated user
            String username = _securityContext.getCallerPrincipal().getName();
            resultList = getEntityManager().createQuery(
                            "SELECT t FROM Todo t WHERE t.username = :usernameValue ", Todo.class)
                    .setParameter("usernameValue", username).getResultList();
        }
        return resultList;
    }
}