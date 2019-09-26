package java.com.github.example.state.messages;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CatalogEvent {
    private Integer orderId;
    private Integer carId;
    private String status;
    private String startRentdate;
    private String endRentdate;
}
