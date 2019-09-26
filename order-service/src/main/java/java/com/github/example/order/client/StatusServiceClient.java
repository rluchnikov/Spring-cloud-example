package java.com.github.example.order.client;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;

@FeignClient(name = "state-service")
public interface StatusServiceClient {


    @RequestMapping( value = "/state/orders/{id}/receive/{event}/{carid}",method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    void sendOrderStatus(@PathVariable("id") Integer id,
                         @PathVariable("event") String status, @PathVariable("carid")Integer carid,
                         @RequestParam(value = "rentDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate rentDate);

   }
