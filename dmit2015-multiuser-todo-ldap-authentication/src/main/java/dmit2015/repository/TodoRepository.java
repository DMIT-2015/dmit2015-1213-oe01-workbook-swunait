package dmit2015.repository;

import common.jpa.AbstractJpaRepository;
import dmit2015.entity.Todo;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

@ApplicationScoped
@Transactional
public class TodoRepository extends AbstractJpaRepository<Todo, Long> {

    public TodoRepository() {
        super(Todo.class);
    }

}