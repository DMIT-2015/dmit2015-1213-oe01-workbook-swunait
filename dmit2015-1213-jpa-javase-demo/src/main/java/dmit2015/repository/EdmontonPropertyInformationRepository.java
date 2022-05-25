package dmit2015.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class EdmontonPropertyInformationRepository {

    private EntityManagerFactory _emf;

    public EdmontonPropertyInformationRepository() {
        _emf = Persistence.createEntityManagerFactory("remote-mssql-assignment02-jpa-pu");
    }

    public long count() {
        EntityManager em = _emf.createEntityManager();

        Long countValue = em.createQuery("""
SELECT COUNT(info)
FROM EdmontonPropertyInformation info
""", Long.class)
                .getSingleResult();

        em.close();
        return countValue.longValue();
    }

    public int lowestYear() {
        EntityManager em = _emf.createEntityManager();

        Integer year = em.createQuery(
                "SELECT MIN(info.yearBuilt) FROM EdmontonPropertyInformation info", Integer.class)
                .getSingleResult();

        em.close();
        return year;
    }
}
