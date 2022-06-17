package dmit2015.repository;

import common.jpa.AbstractJpaRepository;
import dmit2015.entity.Product;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

import java.util.List;

@ApplicationScoped
@Transactional
public class ProductRepository extends AbstractJpaRepository<Product, Integer> {

    public ProductRepository() {
        super(Product.class);
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