package com.company.explorecalifornia.exception;

/**
 * {@exception InvalidPaginationException} thrown to indicate that the pagination parameters are invalid.
 *
 */
public class InvalidPaginationException extends RuntimeException {

    public InvalidPaginationException() {
    }

    public InvalidPaginationException(String message) {
        super(message);
    }

    public InvalidPaginationException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidPaginationException(Throwable cause) {
        super(cause);
    }
}
