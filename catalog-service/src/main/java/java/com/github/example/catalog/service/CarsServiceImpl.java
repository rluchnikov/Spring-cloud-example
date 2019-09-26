package java.com.github.example.catalog.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import java.com.github.example.catalog.client.AuthServiceClient;
import java.com.github.example.catalog.entity.Car;
import java.com.github.example.catalog.entity.Locations;
import java.com.github.example.catalog.entity.Statuses;
import java.com.github.example.catalog.repository.CarsRepository;
import java.com.github.example.catalog.repository.LocationsRepository;
import java.com.github.example.catalog.repository.StateRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Component
public class CarsServiceImpl implements CarsService {

    private String role;
    private List<Car> cars;
    private Integer id;

    @Autowired
    private StateRepository stateRepository;

    @Autowired
    public CarsRepository carsRepository;
    @Autowired
    public LocationsRepository locationsRepository;

    @Autowired
    private AuthServiceClient authClient;

    public List<Car> findCars() {
        this.findLocations();
        if (role.equals("RENT_MANAGER"))
       cars = carsRepository.findCarsByLocation(id);
        if (role.equals("BOSS")||role.equals("ADMIN"))
            cars = carsRepository.findAll();
        if (role.equals("SERVICE_MANAGER"))
            cars = carsRepository.findCarsByState("На обслуживании");
        return cars;
   }

    public List<Car> findCarsWithDate(LocalDate fromDate, LocalDate endDate) {
        this.findLocations();
        if (role.equals("RENT_MANAGER"))
            cars = carsRepository.findCarsByLocationWithDate(id,fromDate,endDate);
        if (role.equals("BOSS")||role.equals("ADMIN"))
            cars = carsRepository.findAllWithDate(fromDate,endDate);
        if (role.equals("SERVICE_MANAGER"))
            cars = carsRepository.findCarsByStateWithDate("На обслуживании",fromDate,endDate);
        return cars;
    }

   @Override
    public List<Car> findAll() {
         return  findCars();
    }

    public List<Car> findAll( LocalDate fromDate, LocalDate endDate) {
        return  findCarsWithDate(fromDate,endDate);
    }

    @Override
    public Car findCarByid(Integer id) {
        this.findLocations();
       Car car =  carsRepository.findById(id).orElseThrow
               (() -> new RuntimeException("No value"));
        Statuses nextState = Optional.ofNullable(car.getNext_state()).orElse(null);
        Statuses state = Optional.ofNullable(car.getState()).orElse(new Statuses());
        if (!state.getName().equals("В прокате") && role.equals("RENT_MANAGER")||role.equals("ADMIN"))
            car.setStateButton("Передать на ТО");
        if (nextState != null && nextState.getName().equals("На обслуживании") && role.equals("SERVICE_MANAGER")) {
            car.setStateButton("Принять на ТО");
        }
        if (nextState != null && nextState.getName().equals("На обслуживании") && role.equals("ADMIN")){
            car.setStateButton("Принять на ТО");
        }
        if (car.getState().getName().equals("На обслуживании") && role.equals("SERVICE_MANAGER")){
            car.setStateButton("Вернуть с ТО");
        }
        if (car.getState().getName().equals("На обслуживании") && role.equals("ADMIN")){
            car.setStateButton("Вернуть с ТО");
        }
        return car;
    }

    @Override
    public void changeStatus(Integer orderId,Integer carId,String state,LocalDate  rentDate,LocalDate endRentDate){
        Car carOptional = carsRepository.findOne(carId);
        if (state.equals("start")) {
            carOptional.setNext_state(stateRepository.findByName("В прокате"));
            carOptional.setPlanned_next_state(rentDate);
        }
        if (state.equals("rent")) {
            carOptional.setNext_state(stateRepository.findByName("В наличии"));
            carOptional.setState(stateRepository.findByName("В прокате"));
            carOptional.setOrder(orderId);
            carOptional.setPlanned_next_state(endRentDate);

        }
        if (state.equals("enable")) {
            carOptional.setNext_state(null);
            carOptional.setState(stateRepository.findByName("В наличии"));
            carOptional.setPlanned_next_state(null);
            carOptional.setOrder(null);
        }

        carsRepository.save(carOptional);

    }

    private void findLocations(){
        String currentUserName = "";
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken))
            currentUserName = authentication.getName();
        authentication.getAuthorities().stream().findFirst().ifPresent(x-> role=x.getAuthority());
            Locations location = locationsRepository.findByName(authClient.getLocation(currentUserName));
        id = location.getId();
    }


}

