package dmit2015.repository;

import common.jpa.AbstractJpaRepository;
import dmit2015.entity.Category;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.Tuple;
import jakarta.transaction.Transactional;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

@ApplicationScoped
@Transactional
public class CategoryRepository extends AbstractJpaRepository<Category, Integer> {

    public CategoryRepository() {
        super(Category.class);
    }

    public Map<String, Integer> selectItemMap() {
        return getEntityManager().createQuery("""
SELECT c.categoryName as key, c.id as value
FROM Category c
ORDER BY c.categoryName""", Tuple.class)
                .getResultStream()
                .collect(Collectors.toMap(
                        tuple -> tuple.get("key", String.class),
                        tuple -> tuple.get("value", Integer.class),
                        (oldValue, newValue) -> oldValue,
                        LinkedHashMap::new
                ));
    }

}