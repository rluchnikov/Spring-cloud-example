package java.com.github.example.catalog.service;

import java.com.github.example.catalog.entity.Car;

import java.time.LocalDate;
import java.util.List;

public interface CarsService {

  List<Car> findAll();
  List<Car> findAll(LocalDate fromDate, LocalDate endDate);
  Car findCarByid(Integer id);
  void changeStatus(Integer orderId,Integer carId,String status,LocalDate startRentDate,LocalDate endRentDate);
}
