package java.com.github.example.catalog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.com.github.example.catalog.entity.Locations;
import java.com.github.example.catalog.repository.LocationsRepository;
import java.com.github.example.catalog.service.LocationService;

import java.util.List;
import java.util.Optional;

@RestController
public class LocationsController {

    @Autowired
    private LocationsRepository locationRepository;

    @Autowired
    private LocationService locationService;

    @GetMapping(value="/locations")
    public List<Locations> listRepository(){
        return locationRepository.findAll();
    }

    @GetMapping(value="/userlocation")
    public Locations userLocation(){
        return locationService.userLocation();
    }

    @PostMapping(value = "/location")
    public Locations create(@RequestBody Locations locations){
        return locationRepository.save(locations);
    }

    @GetMapping(value = "/locations/{id}")
    public Locations find(@PathVariable("id") Integer id) {
        return locationRepository.findById(id).orElse(null);
    }

    @GetMapping(value = "/locationname/{id}")
    public String findLocationName(@PathVariable("id") Integer id) {
        return locationService.locationName(id);
    }

    @PutMapping("/locations/{id}")
    public ResponseEntity<Object> update(@RequestBody Locations location, @PathVariable Integer id) {

        Optional<Locations> locationOptional = locationRepository.findById(id);
        if (!locationOptional.isPresent())
            return ResponseEntity.notFound().build();
        location.setId(id);
        locationRepository.save(location);
        return ResponseEntity.noContent().build();
    }
}
