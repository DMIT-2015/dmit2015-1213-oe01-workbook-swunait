package dmit2015.view;

import dmit2015.client.Todo;
import dmit2015.client.TodoService;

import lombok.Getter;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.omnifaces.util.Messages;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

@Named("currentTodoCreateController")
@RequestScoped
public class TodoCreateController {

    @Inject
    @RestClient
    private TodoService _employeeService;

    @Inject
    private LoginSession _loginSession;

    @Getter
    private Todo newTodo = new Todo();

    public String onCreateNew() {
        String nextPage = "";
        try {
            nextPage = _loginSession.checkForToken();
            if (nextPage == null) {
                String authorization = _loginSession.getAuthorization();
                _employeeService.create(newTodo, authorization);
                Messages.addFlashGlobalInfo("Create was successful.");
                nextPage = "index?faces-redirect=true";
            }
        } catch (Exception e) {
            e.printStackTrace();
            Messages.addGlobalError("Create was not successful. {0}", e.getMessage());
        }
        return nextPage;
    }

}