package java.com.github.example.catalog.audit.query;

import org.hibernate.envers.RevisionType;
import java.com.github.example.catalog.entity.RevisionEntity;

public class AuditQueryResult <T> {

        private final T entity;
        private final RevisionEntity revision;
        private final RevisionType type;

    public AuditQueryResult(T entity, RevisionEntity revision, RevisionType type) {
            this.entity = entity;
            this.revision = revision;
            this.type = type;
        }

    public T getEntity() {
        return entity;
    }

    public RevisionEntity getRevision() {
        return revision;
    }

    public RevisionType getType() {
        return type;
    }
}
