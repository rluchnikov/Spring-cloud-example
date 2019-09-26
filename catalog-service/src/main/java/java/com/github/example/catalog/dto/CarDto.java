package java.com.github.example.catalog.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

import java.time.LocalDate;
import java.util.Date;

@AllArgsConstructor
@Builder
@Value
public class CarDto {

    private Integer car_id;

    private String registration_number;

    private String brand;

    private Integer year_manufacture;

    private Integer mileage;

    @JsonFormat(pattern="dd-MM-yyyy")
    private LocalDate last_maintenance;

    @JsonFormat(pattern="dd-MM-yyyy")
    private LocalDate next_maintenance;

    private String state;

    @JsonFormat(pattern="dd-MM-yyyy")
    private LocalDate state_date;

    private String next_state;

    @JsonFormat(pattern="dd-MM-yyyy")
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
