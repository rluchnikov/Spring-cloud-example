package java.com.github.example.catalog.converter;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.com.github.example.catalog.dto.CarDto;
import java.com.github.example.catalog.dto.CarHistoryDto;
import java.com.github.example.catalog.entity.Car;
import java.com.github.example.catalog.entity.CarHistory;

import java.util.Date;

public class Converter {

    public static CarDto convert(Car source) {

        if(source == null) {
            return null;
        }

        return CarDto.builder().car_id(source.getId()).brand(source.getBrand()).year_manufacture(source.getYear_manufacture()).mileage(source.getMileage())
                .last_maintenance(source.getLast_maintenance()).next_maintenance(source.getNext_maintenance()).state(source.getState().getName()).
                        state_date(source.getState_date()).next_state(source.getNext_state().getName())
                .planned_next_state(source.getPlanned_next_state()).order(source.getOrder()).location(source.getLocation().getId()).current_location(source.getCurrent_location().getId())
                .createdBy(source.getCreatedBy()).createdAt(source.getCreatedAt()).lastModifiedBy(source.getLastModifiedBy()).lastModifiedAt(source.getLastModifiedAt()).build();
    }

    public static CarHistoryDto convert(CarHistory source) {

        if(source == null) {
            return null;
        }

        CarDto customerDto = convert(source.getCar());
        Date date = source.getDate();
        String revisionType =source.getRevisionType().toString();
        return new CarHistoryDto(customerDto, date, revisionType);
    }

    private static ObjectMapper getJsonMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, true);
        return mapper;
    }
}
