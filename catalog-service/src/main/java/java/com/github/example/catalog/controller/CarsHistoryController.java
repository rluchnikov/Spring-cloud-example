package java.com.github.example.catalog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import java.com.github.example.catalog.converter.Converter;
import java.com.github.example.catalog.dto.CarHistoryDto;
import java.com.github.example.catalog.entity.CarHistory;
import java.com.github.example.catalog.service.audit.CarsHistoryRepository;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class CarsHistoryController {

    @Autowired
    private CarsHistoryRepository carsHistoryRepository;

    @GetMapping(value="/history/car/{id}")
    public List<CarHistoryDto> getHistory(@PathVariable("id") Integer id) {
        // Get History:
        List<CarHistory> history = carsHistoryRepository.listCarsRevisions(id);

        // Return the DTO List:
        return history.stream()
                .map(Converter::convert)
                .collect(Collectors.toList());
    }

}
