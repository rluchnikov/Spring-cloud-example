package java.com.github.example.order.exception;

public class LocationNotFoundException extends AbstractOrderException {

    @Override
    public String getMessage() {
        return "Location not found";
    }
}
