package dmit2015.view;

import dmit2015.entity.Todo;
import dmit2015.repository.TodoRepository;
import lombok.Getter;
import org.omnifaces.util.Messages;

import jakarta.annotation.PostConstruct;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.io.Serializable;
import java.util.List;

@Named("currentTodoListController")
@ViewScoped
public class TodoListController implements Serializable {
    private static final long serialVersionUID = 1L;

    @Inject
    private TodoRepository _todoRepository;

    @Getter
    private List<Todo> todoList;

    @PostConstruct  // After @Inject is complete
    public void init() {
        try {
            todoList = _todoRepository.findAll();
        } catch (Exception ex) {
            Messages.addGlobalError(ex.getMessage());
        }
    }
}