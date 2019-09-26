package java.com.github.example.order.exception;

public class OrderNotFoundException extends AbstractOrderException {

    private final Integer id;

    public OrderNotFoundException(Integer id) {
        this.id = id;
    }

    @Override
    public String getMessage() {
        return "Order with id = " + id + " not found";
    }

}
