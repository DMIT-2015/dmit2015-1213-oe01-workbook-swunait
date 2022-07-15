package dmit2015.view;

import dmit2015.entity.Todo;
import dmit2015.repository.TodoRepository;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import lombok.Getter;
import org.omnifaces.util.Messages;

@Named("currentTodoCreateController")
@RequestScoped
public class TodoCreateController {

    @Inject
    private TodoRepository _todoRepository;

    @Getter
    private Todo newTodo = new Todo();

    public String onCreateNew() {
        String nextPage = "";
        try {
            _todoRepository.add(newTodo);
            Messages.addFlashGlobalInfo("Create was successful. {0}", newTodo.getId());
            nextPage = "index?faces-redirect=true";
        } catch (Exception e) {
            e.printStackTrace();
            Messages.addGlobalError("Create was not successful. {0}", e.getMessage());
        }
        return nextPage;
    }

}