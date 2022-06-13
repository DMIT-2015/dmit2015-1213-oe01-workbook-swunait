package dmit2015.repository;

import common.jpa.AbstractJpaRepository;
import dmit2015.entity.Supplier;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

@ApplicationScoped
@Transactional
public class SupplierRepository extends AbstractJpaRepository<Supplier,Integer> {

     public SupplierRepository() {
        super(Supplier.class);
     }

}