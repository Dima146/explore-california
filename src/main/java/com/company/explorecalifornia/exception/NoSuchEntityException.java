package com.company.explorecalifornia.exception;

/**
 * {@exception NoSuchEntityException} thrown to indicate that the entity does not exist in the database.
 *
 */
public class NoSuchEntityException extends RuntimeException {

    public NoSuchEntityException() {
    }

    public NoSuchEntityException(String message) {
        super(message);
    }

    public NoSuchEntityException(String message, Throwable cause) {
        super(message, cause);
    }

    public NoSuchEntityException(Throwable cause) {
        super(cause);
    }
}
