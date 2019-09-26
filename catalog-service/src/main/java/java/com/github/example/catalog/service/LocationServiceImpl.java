package java.com.github.example.catalog.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import java.com.github.example.catalog.client.AuthServiceClient;
import java.com.github.example.catalog.entity.Car;
import java.com.github.example.catalog.entity.Locations;
import java.com.github.example.catalog.repository.LocationsRepository;

import java.util.List;
import java.util.Optional;

@Component
public class LocationServiceImpl implements LocationService {

    private final Logger log = LoggerFactory.getLogger(getClass());
    private String role;
    private List<Car> cars;
    private Integer id;

    @Autowired
    LocationsRepository locationRepository;

    @Autowired
    private AuthServiceClient authClient;

    @Override
    public Locations userLocation(){
        String currentUserName = "";
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken))
            currentUserName = authentication.getName();
        return locationRepository.findByName(authClient.getLocation(currentUserName));
    }

    @Override
    public String locationName(Integer id){
        Optional<Locations> locations = locationRepository.findById(id);
        return  locations.map(Locations::getName).orElse("unknown");

    }

}

