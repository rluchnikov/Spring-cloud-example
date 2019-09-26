package java.com.github.example.catalog.client;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import java.com.github.example.catalog.messages.CatalogEvent;
import java.com.github.example.catalog.service.CarsService;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Slf4j
@EnableBinding(Sink.class)
@Component
public class StreamClient {

    @Qualifier("carsServiceImpl")
    @Autowired
    private CarsService carService;

   @StreamListener(Sink.INPUT)
    public void streamListener(@Payload CatalogEvent event) {
       if (event != null) {
           log.info("get message " + event);
           carService.changeStatus(event.getOrderId()!=null ? event.getOrderId():0, event.getCarId(), event.getStatus(), LocalDate.parse(event.getStartRentdate(), DateTimeFormatter.ofPattern("dd-MM-yyyy"))
           ,LocalDate.parse(event.getEndRentdate(), DateTimeFormatter.ofPattern("dd-MM-yyyy")));
       }
   }
}
