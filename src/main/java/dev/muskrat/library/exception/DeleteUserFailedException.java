package dev.muskrat.library.exception;

public class DeleteUserFailedException extends RuntimeException {

    public DeleteUserFailedException(String message) {
        super(message);
    }
}
