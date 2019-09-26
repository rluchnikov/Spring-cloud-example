package java.com.github.example.catalog.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Date;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CarDtoPost {

    private Integer car_id;

    private String registration_number;

    private String brand;

    private Integer year_manufacture;

    private Integer mileage;

    @JsonFormat(pattern="dd-MM-yyyy")
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDate last_maintenance;

    @JsonFormat(pattern="dd-MM-yyyy")
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDate next_maintenance;

    private Integer state;

    @JsonFormat(pattern="dd-MM-yyyy")
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDate state_date;

    private Integer next_state;

    @JsonFormat(pattern="dd-MM-yyyy")
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDate planned_next_state;

   private Integer order;

    private Integer location;

   private Integer current_location;

   private String comment;

   private String createdBy;

   private Date createdAt;

   private String lastModifiedBy;

   private Date lastModifiedAt;

}
