package java.com.github.example.authservice.client;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = "catalog-service")
public interface CatalogServiceClient {

    @RequestMapping(value = "/catalog/locationname/{id}", method = RequestMethod.GET)
    String getLocation(@PathVariable("id") Integer  id);
}
