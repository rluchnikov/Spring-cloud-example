package java.com.github.example.order.exception;

public class CarNotFoundException extends AbstractOrderException {

    private final Integer carId;

    public CarNotFoundException(Integer carId) {
        this.carId = carId;
    }

    @Override
    public String getMessage() {
        return "Car with id = " + carId + " not found";
    }
}
