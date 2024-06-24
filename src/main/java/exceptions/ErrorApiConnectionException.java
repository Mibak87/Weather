package exceptions;

public class ErrorApiConnectionException extends RuntimeException {
    public ErrorApiConnectionException(String message) {
        super(message);
    }
}
