package order.processing.system.exception;

public class OrderDomainException extends DomainException{
    public OrderDomainException(String message) {
        super(message);
    }

    public OrderDomainException(String message, Throwable cause) {
        super(message, cause);
    }
}
