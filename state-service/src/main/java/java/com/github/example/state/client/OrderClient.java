package java.com.github.example.state.client;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = "order-service")
public interface OrderClient {

    @RequestMapping(method = RequestMethod.PUT, value = "/orders/order/{id}/{status}", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
     void changeOrderStatus(@PathVariable("id") Integer id,
                          @PathVariable("status") String status);
}
