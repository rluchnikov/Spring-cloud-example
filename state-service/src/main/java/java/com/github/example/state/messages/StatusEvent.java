package java.com.github.example.state.messages;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class StatusEvent {

    private Integer orderId;
    private Integer carId;
    private String startRentdate;
    private String endRentdate;
}
