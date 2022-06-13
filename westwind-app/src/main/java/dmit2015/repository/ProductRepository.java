package dmit2015.repository;

import common.jpa.AbstractJpaRepository;
import dmit2015.entity.Product;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

@ApplicationScoped
@Transactional
public class ProductRepository extends AbstractJpaRepository<Product,Integer> {

     public ProductRepository() {
        super(Product.class);
     }

}