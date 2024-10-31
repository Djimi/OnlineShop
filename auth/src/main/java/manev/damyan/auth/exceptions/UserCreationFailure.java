package manev.damyan.auth.exceptions;

public class UserCreationFailure extends RuntimeException{

    public UserCreationFailure() {
    }

    public UserCreationFailure(String message) {
        super(message);
    }

    public UserCreationFailure(String message, Throwable cause) {
        super(message, cause);
    }

    public UserCreationFailure(Throwable cause) {
        super(cause);
    }

    public UserCreationFailure(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
