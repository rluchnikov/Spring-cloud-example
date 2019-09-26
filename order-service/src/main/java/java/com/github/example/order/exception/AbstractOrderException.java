package java.com.github.example.order.exception;

public abstract class AbstractOrderException extends RuntimeException {

    public AbstractOrderException() {
    }

    public AbstractOrderException(String message) {
        super(message);
    }

    public AbstractOrderException(String message, Throwable cause) {
        super(message, cause);
    }

    public AbstractOrderException(Throwable cause) {
        super(cause);
    }

    public AbstractOrderException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
