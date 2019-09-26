package java.com.github.example.catalog.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

import java.util.Date;

@AllArgsConstructor
@Builder
@Value
public class CarHistoryDto {
    @JsonProperty("car")
    private final CarDto car;
    @JsonProperty("date")
    private final Date date;
    @JsonProperty("type")
    private final String type;
}
