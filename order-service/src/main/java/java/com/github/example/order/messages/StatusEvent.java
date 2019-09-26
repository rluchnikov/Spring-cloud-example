package java.com.github.example.order.messages;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Builder
public class StatusEvent {

    private Integer orderId;
    private Integer carId;
    private String startRentdate;
    private String endRentdate;
}
