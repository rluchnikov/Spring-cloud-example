package java.com.github.example.state.client;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "catalog-service")
public interface CatalogClient {

    @RequestMapping(method = RequestMethod.PUT, value = "/catalog/car/{id}/{state}", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
     void changeCarStatus(@PathVariable("id") Integer id,@PathVariable("state") String status,
                          @RequestParam("orderID") Integer orderid,
                          @RequestParam("rentDate") String rentDate);
}
