package org.ericklemos;

public class ValidationException extends RuntimeException {

    public ValidationException(Throwable cause, String message) {
        super(message, cause);
    }

    public ValidationException(String errorMessage) {
        super(errorMessage);
    }

}
