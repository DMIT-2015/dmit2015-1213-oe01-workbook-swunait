package dmit2015.repository;

import common.jpa.AbstractJpaRepository;
import dmit2015.entity.Category;
import dmit2015.entity.Product;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;

import java.util.List;

@ApplicationScoped
@Transactional
public class ProductRepository extends AbstractJpaRepository<Product, Integer> {
    public void add(Product newProduct) {
        //em.persist(newProduct);
        super.add(newProduct);
    }

    public ProductRepository() {
        super(Product.class);
    }


    public List<Product> findByCategoryId(Integer categoryId) {
        TypedQuery<Product> query = getEntityManager().createQuery("""
                    SELECT p
                    FROM Product p
                    WHERE p.category.id = :categoryIdValue
                    """, Product.class);
        query.setParameter("categoryIdValue", categoryId);

//        TypedQuery<Product> query = getEntityManager().createQuery("""
//                    SELECT p
//                    FROM Product p
//                    WHERE p.categoryId = :categoryIdValue
//                    """, Product.class);
//        query.setParameter("categoryIdValue", categoryId);


//        TypedQuery<Product> query = getEntityManager().createQuery("""
//                    SELECT p
//                    FROM Product p
//                    WHERE p.category = :categoryValue
//                    """, Product.class);
//        query.setParameter("categoryValue", getEntityManager().find(Category.class, categoryId));

        return query.getResultList();
    }

    public List<Product> findByProductName(String partialProductName) {
        return getEntityManager()
                .createQuery("""
                    SELECT p
                    FROM Product p
                    WHERE p.productName LIKE :productNameValue
                    """, Product.class)
                .setParameter("productNameValue", "%" + partialProductName + "%")
                .getResultList();
    }

}