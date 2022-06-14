package dmit2015.repository;

import common.jpa.AbstractJpaRepository;
import dmit2015.entity.Category;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

@ApplicationScoped
@Transactional
public class CategoryRepository extends AbstractJpaRepository<Category, Integer> {

    public CategoryRepository() {
        super(Category.class);
    }

}