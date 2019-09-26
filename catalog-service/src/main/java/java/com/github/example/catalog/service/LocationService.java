package java.com.github.example.catalog.service;

import java.com.github.example.catalog.entity.Locations;

public interface LocationService {

  Locations userLocation();
  String locationName(Integer id);
}
