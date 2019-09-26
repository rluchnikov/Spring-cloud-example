package java.com.github.example.catalog.service.audit;

import org.hibernate.envers.AuditReader;
import org.hibernate.envers.AuditReaderFactory;
import org.hibernate.envers.query.AuditEntity;
import org.hibernate.envers.query.AuditQuery;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import java.com.github.example.catalog.audit.query.AuditQueryResult;
import java.com.github.example.catalog.audit.query.AuditQueryUtils;
import java.com.github.example.catalog.entity.Car;
import java.com.github.example.catalog.entity.CarHistory;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class CarsHistoryRepositoryImpl implements CarsHistoryRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional(readOnly = true)
    public List<CarHistory> listCarsRevisions(Integer carId) {

        // Create the Audit Reader. It uses the EntityManager, which will be opened when
        // starting the new Transation and closed when the Transaction finishes.
        AuditReader auditReader = AuditReaderFactory.get(entityManager);

        // Create the Query:
        AuditQuery auditQuery = auditReader.createQuery()
                .forRevisionsOfEntity(Car.class, false, true)
                .add(AuditEntity.id().eq(carId));

        // We don't operate on the untyped Results, but cast them into a List of AuditQueryResult:
        return AuditQueryUtils.getAuditQueryResults(auditQuery, Car.class).stream()
                // Turn into the CustomerHistory Domain Object:
                .map(x -> getCustomerHistory(x))
                // And collect the Results:
                .collect(Collectors.toList());
    }

    private static CarHistory getCustomerHistory(AuditQueryResult<Car> auditQueryResult) {
        return new CarHistory(
                auditQueryResult.getEntity(),
                auditQueryResult.getRevision().getRevisionDate(),
                auditQueryResult.getType()

        );
    }
}
