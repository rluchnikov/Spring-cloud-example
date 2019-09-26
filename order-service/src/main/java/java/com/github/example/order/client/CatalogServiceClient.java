package java.com.github.example.order.client;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = "catalog-service")
public interface CatalogServiceClient {


    @RequestMapping(value = "/car/{id}/{status}",method = RequestMethod.PUT)
    void changeCarStatus(@PathVariable("id") Integer id,
                         @PathVariable("status") String status);

   }
