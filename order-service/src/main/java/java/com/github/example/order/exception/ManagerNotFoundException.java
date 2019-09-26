package java.com.github.example.order.exception;

public class ManagerNotFoundException extends AbstractOrderException {

    private final Integer managerId;

    public ManagerNotFoundException(Integer mangerId) {
        this.managerId = mangerId;
    }

    @Override
    public String getMessage() {
        return "Manager with id = " + managerId + " not found";
    }
}
