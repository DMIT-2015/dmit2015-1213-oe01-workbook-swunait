package dmit2015.repository;

import common.jpa.AbstractJpaRepository;
import dmit2015.entity.Supplier;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.Tuple;
import jakarta.transaction.Transactional;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

@ApplicationScoped
@Transactional
public class SupplierRepository extends AbstractJpaRepository<Supplier, Integer> {

    public SupplierRepository() {
        super(Supplier.class);
    }


    public Map<String, Integer> selectItemMap() {
        return getEntityManager().createQuery("""
SELECT s.companyName as key, s.id as value
FROM Supplier s
ORDER BY s.companyName""", Tuple.class)
                .getResultStream()
                .collect(Collectors.toMap(
                        tuple -> tuple.get("key", String.class),
                        tuple -> tuple.get("value", Integer.class),
                        (oldValue, newValue) -> oldValue,
                        LinkedHashMap::new
                ));
    }


}