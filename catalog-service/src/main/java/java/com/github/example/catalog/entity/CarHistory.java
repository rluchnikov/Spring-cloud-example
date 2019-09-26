package java.com.github.example.catalog.entity;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.hibernate.envers.RevisionType;

import java.util.Date;

@Data
@RequiredArgsConstructor
public class CarHistory {
    private final Car car;
    private Date date;
    private final RevisionType revisionType;

    public CarHistory(Car car, Date date, RevisionType revisionType) {
        this.car = car;
        this.date = date;
        this.revisionType = revisionType;
    }

}
