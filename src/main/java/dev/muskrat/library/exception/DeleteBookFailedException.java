package dev.muskrat.library.exception;

public class DeleteBookFailedException extends RuntimeException {

    public DeleteBookFailedException(String message) {
        super(message);
    }
}
