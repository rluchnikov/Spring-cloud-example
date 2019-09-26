package java.com.github.example.catalog.client;

import feign.Param;
import feign.RequestLine;
import org.springframework.cloud.netflix.feign.FeignClient;

@FeignClient(name = "auth-service")
public interface AuthServiceClient {

    @RequestLine("GET /auth/location?username={username}")
    String getLocation(@Param("username") String username);
}
