package java.com.github.example.catalog.service.audit;


import java.com.github.example.catalog.entity.CarHistory;

import java.util.List;

public interface CarsHistoryRepository {

    List<CarHistory> listCarsRevisions(Integer carId);
}
