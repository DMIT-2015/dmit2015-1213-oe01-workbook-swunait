package dmit2015.listener;

import dmit2015.entity.Todo;
import dmit2015.repository.TodoRepository;
import jakarta.inject.Inject;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

@WebListener
public class ApplicationStartupListener implements ServletContextListener{

    public ApplicationStartupListener() {
    }

    @Inject
    private TodoRepository _todoRepository;

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        /* This method is called when the servlet context is initialized(when the Web application is deployed). */
        if (_todoRepository.count() == 0) {
            Todo todo1 = new Todo();
            todo1.setName("Todo Dto 1");
            todo1.setComplete(false);
            _todoRepository.add(todo1);

            Todo todo2 = new Todo();
            todo2.setName("Todo Dto 2");
            todo2.setComplete(false);
            _todoRepository.add(todo2);

            Todo todo3 = new Todo();
            todo3.setName("Todo Dto 3");
            todo3.setComplete(false);
            _todoRepository.add(todo3);
        }


    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        /* This method is called when the servlet Context is undeployed or Application Server shuts down. */
    }
}
