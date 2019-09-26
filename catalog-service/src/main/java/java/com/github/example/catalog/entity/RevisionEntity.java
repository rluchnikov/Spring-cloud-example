package java.com.github.example.catalog.entity;

import org.hibernate.envers.DefaultRevisionEntity;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "revinfo")
@org.hibernate.envers.RevisionEntity
public class RevisionEntity extends DefaultRevisionEntity {
}
