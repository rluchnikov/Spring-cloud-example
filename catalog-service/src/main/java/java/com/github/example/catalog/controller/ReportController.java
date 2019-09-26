package java.com.github.example.catalog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.jasperreports.JasperReportsPdfView;
import java.com.github.example.catalog.dto.CarDto;
import java.com.github.example.catalog.entity.Car;
import java.com.github.example.catalog.repository.CarsRepository;

import java.time.LocalDate;
import java.util.*;


@Controller
public class ReportController {

    @Autowired
    private ApplicationContext appContext;

    @Autowired
    private CarsRepository carsRepository;

    @RequestMapping(path = "/report", method = RequestMethod.GET)
    public ModelAndView report() {
        String currentUserName = "";
        JasperReportsPdfView view = new JasperReportsPdfView();
        view.setUrl("classpath:report.jrxml");
        view.setApplicationContext(appContext);

        Map<String, Object> params = new HashMap<>();
        params.put("datasource",getCars());
        params.put("", LocalDate.now());
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken))
            currentUserName = authentication.getName();
        params.put("USER", currentUserName);
        return new ModelAndView(view, params);
    }

    List<CarDto> getCars(){
        List<CarDto> carsDto = new ArrayList<>();
        List<Car> cars = carsRepository.findAll();
        cars.forEach(x-> carsDto.add(CarDto.builder().car_id(x.getId()).registration_number(x.getRegistration_number())
        .brand(x.getBrand()).year_manufacture(x.getYear_manufacture()).mileage(x.getMileage()).last_maintenance(Optional.ofNullable(x.getLast_maintenance()).orElse(null))
        .state(Optional.ofNullable(x.getState().getName()).orElse("")).state_date(Optional.ofNullable(x.getState_date()).orElse(null)).next_state(x.getNext_state() !=null ? x.getNext_state().getName():"")
        .planned_next_state(Optional.ofNullable(x.getPlanned_next_state()).orElse(null)).build()));
        return carsDto;
    }
}
