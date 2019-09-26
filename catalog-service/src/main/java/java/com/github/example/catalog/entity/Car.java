package java.com.github.example.catalog.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.envers.Audited;
import org.hibernate.envers.RelationTargetAuditMode;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Audited
@EntityListeners({AuditingEntityListener.class})
@Table(name="cars")
public class Car implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator ="carsequence")
    @SequenceGenerator(name = "carsequence", sequenceName= "car_sequence"
           , allocationSize = 1)
    private Integer id;

    @Column(name="registration_number")
    private String registration_number;

    @Column(name="brand")
    private String brand;

    @Column(name="year_manufacture")
    private Integer year_manufacture;

    @Column(name="mileage")
    private Integer mileage;

    @JsonFormat(pattern="dd-MM-yyyy")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @Column(name="last_maintenance")
    private LocalDate last_maintenance;

    @JsonFormat(pattern="dd-MM-yyyy")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @Column(name="next_maintenance")
    private LocalDate next_maintenance;

    @Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
    @OneToOne
    @JoinColumn(name="state")
    private Statuses state;

    @JsonFormat(pattern="dd-MM-yyyy")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @Column(name="state_date")
    private LocalDate state_date;

    @Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
    @OneToOne
    @JoinColumn(name="next_state")
    private Statuses next_state;

    @JsonFormat(pattern="dd-MM-yyyy")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @Column(name="planned_next_state")
    private LocalDate planned_next_state;

    @Column(name="order_id")
    private Integer order;

    @Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
    @OneToOne
    @JoinColumn(name = "location")
    private Locations location;

    @Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
    @OneToOne
    @JoinColumn(name="current_location")
    private Locations current_location;

    @Column(name="comment")
    private String comment;

    @CreatedBy
    @Column(name = "created_by")
    @JsonIgnore
    private String createdBy;

    @CreatedDate
    @Column(name = "created_at")
    @JsonIgnore
    private Date createdAt;

    @LastModifiedBy
    @JsonIgnore
    @Column(name = "last_modified_by")
    private String lastModifiedBy;

    @LastModifiedDate
    @JsonIgnore
    @Column(name = "last_modified_at")
    private Date lastModifiedAt;

    @Transient
    private String stateButton;

}
