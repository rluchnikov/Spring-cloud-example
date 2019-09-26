package java.com.github.example.order.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "orders")
public class Order {

    @Id
    @SequenceGenerator( name = "orderSequence", sequenceName = "order_sequence", allocationSize = 1)
    @GeneratedValue( strategy = GenerationType.SEQUENCE, generator = "orderSequence")
    private Integer id;

    @JsonProperty("car")
    @Column(name = "car_id")
    private Integer carId;

    @JsonProperty("lastname")
    @Column(name = "last_name")
    private String lastName;

    @JsonProperty("firstname")
    @Column(name = "first_name")
    private String firstName;

    @JsonProperty("middlename")
    @Column(name = "middle_name")
    private String middleName;

    @JsonProperty("mobile")
    @Column(name = "client_phone_number")
    private String clientPhoneNumber;

    @JsonProperty("email")
    @Column(name = "client_email")
    private String clientEmail;

    @JsonProperty("dt_start")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @JsonFormat(pattern = "dd-MM-yyyy")
    @Column(name = "start_rent_date")
    private LocalDate startRentDate;

    @JsonProperty("dt_end")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @JsonFormat(pattern = "dd-MM-yyyy")
    @Column(name = "end_rent_date")
    private LocalDate endRantDate;

    @JsonProperty("location_start")
    @Column(name = "location_start")
    private String locationStart;

    @JsonProperty("location_end")
    @Column(name = "location_end")
    private String locationEnd;

    @OneToOne
    @JoinColumn(name = "state")
    private Statuses status;

    @JsonProperty("manager")
    @Column(name = "manager")
    private String manger;

   @Column(name = "comment")
    private String comment;

    @Column(name = "create_date")
    @DateTimeFormat(iso = DateTimeFormat.ISO.TIME)
    @JsonFormat(pattern = "dd-MM-yyyy HH:mm")
    @JsonProperty("dt_create")
    private LocalDateTime CreateDate;


}
