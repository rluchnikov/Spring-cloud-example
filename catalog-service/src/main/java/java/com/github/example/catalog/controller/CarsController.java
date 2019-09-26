package java.com.github.example.catalog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.com.github.example.catalog.dto.CarDtoPost;
import java.com.github.example.catalog.entity.Car;
import java.com.github.example.catalog.entity.Locations;
import java.com.github.example.catalog.entity.Statuses;
import java.com.github.example.catalog.repository.CarsRepository;
import java.com.github.example.catalog.repository.LocationsRepository;
import java.com.github.example.catalog.repository.StateRepository;
import java.com.github.example.catalog.service.CarsService;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
public class CarsController {

    @Autowired
    private CarsService carService;

    @Autowired
    private StateRepository stateRepository;

    @Autowired
    private CarsRepository carsRepository;

    @Autowired
    private LocationsRepository locationRepository;

    @GetMapping(value="/cars")
    public List<Car> listCars(@RequestParam(value = "startDate",required=false)   @DateTimeFormat(pattern="dd-MM-yyyy") LocalDate fromDate,
        @RequestParam(value = "endDate",required=false)   @DateTimeFormat(pattern="dd-MM-yyyy") LocalDate endDate){
        List<Car> car = new ArrayList<>();
        if (fromDate !=null||endDate !=null){
           car = carService.findAll(fromDate,endDate);
        }else {
            car = carService.findAll();
        }
        return car;
    }

    @PostMapping(value = "/car")
    public Car create(@RequestBody CarDtoPost car){
        Locations location = locationRepository.findOne(car.getLocation());
        Locations currentLocation = locationRepository.findOne(car.getCurrent_location());
        Statuses state = stateRepository.findOne(1);

        return carsRepository.save(Car.builder().brand(car.getBrand()).registration_number(car.getRegistration_number()).year_manufacture(car.getYear_manufacture())
                .mileage(car.getMileage()).last_maintenance(car.getLast_maintenance()).next_maintenance(car.getNext_maintenance()).state(state).
                        state_date(LocalDate.now()).planned_next_state(car.getPlanned_next_state()).order(car.getOrder())
                .location(location).current_location(currentLocation).comment(car.getComment()).build());
    }

    @GetMapping(value = "/car/{id}")
    public Car find(@PathVariable("id") Integer id){
        return carService.findCarByid(id);
    }

    @PutMapping("/car/{id}")
    public ResponseEntity<Object> updateCar(@RequestBody Car car, @PathVariable Integer id) {

        Optional<Car> carOptional = carsRepository.findById(id);
        if (!carOptional.isPresent())
            return ResponseEntity.notFound().build();
        car.setId(id);
        carsRepository.save(car);
        return ResponseEntity.noContent().build();
    }
    @PutMapping("/car/{id}/{state}")
    public ResponseEntity<Object> changeState(@PathVariable("id") Integer id,@PathVariable("state") String state,
                                              @RequestParam(value = "orderID",required=false) Integer orderid,
                                              @RequestParam(value = "rentDate",required=false) @DateTimeFormat(pattern="dd-MM-yyyy") LocalDate rentDate) {

        Car carOptional = carsRepository.findById(id).orElseThrow
                (() -> new RuntimeException("Нет такой машины"));
            if (state.equals("start")) {
                carOptional.setNext_state(stateRepository.findByName("В прокате"));
                carOptional.setPlanned_next_state(rentDate);
            }
        if (state.equals("rent")) {
            carOptional.setNext_state(stateRepository.findByName("В наличии"));
            carOptional.setState(stateRepository.findByName("В прокате"));
            carOptional.setOrder(orderid);
        }
            if (state.equals("enable")) {
                carOptional.setNext_state(null);
                carOptional.setState(stateRepository.findByName("В наличии"));
                carOptional.setPlanned_next_state(null);
            }

        carsRepository.save(carOptional);
        return ResponseEntity.noContent().build();
    }
}
