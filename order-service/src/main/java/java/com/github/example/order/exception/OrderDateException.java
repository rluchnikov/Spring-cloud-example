package java.com.github.example.order.exception;

public class OrderDateException extends AbstractOrderException {


    @Override
    public String getMessage() {
        return "Уже есть заказ на эти даты";
    }

}
