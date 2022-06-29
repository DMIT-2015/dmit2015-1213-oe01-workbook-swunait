package dmit2015.view;

import dmit2015.client.LoginSession;
import dmit2015.client.Todo;
import dmit2015.client.TodoMpRestClient;
import lombok.Getter;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.omnifaces.cdi.ViewScoped;
import org.omnifaces.util.Messages;

import jakarta.annotation.PostConstruct;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.io.Serial;
import java.io.Serializable;
import java.util.Map;


@Named("currentTodoListController")
@ViewScoped
public class TodoListController implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Inject
    @RestClient
    private TodoMpRestClient _todoMpRestClient;

    @Getter
    private Map<String, Todo> todoMap;

    @Inject
    private LoginSession _loginSession;

    @PostConstruct  // After @Inject is complete
    public void init() {
        try {
//            todoMap = _todoMpRestClient.findAll();
            String token = _loginSession.getToken();
            todoMap = _todoMpRestClient.findAll(token);

        } catch (Exception ex) {
            Messages.addGlobalError(ex.getMessage());
        }
    }
}