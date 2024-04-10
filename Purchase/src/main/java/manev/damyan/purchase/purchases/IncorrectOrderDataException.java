package manev.damyan.purchase.purchases;


public class IncorrectOrderDataException extends RuntimeException{

    public IncorrectOrderDataException() {
    }

    public IncorrectOrderDataException(String message) {
        super(message);
    }

    public IncorrectOrderDataException(String message, Throwable cause) {
        super(message, cause);
    }

    public IncorrectOrderDataException(Throwable cause) {
        super(cause);
    }

    public IncorrectOrderDataException(String message, Throwable cause, boolean enableSuppression,
            boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
