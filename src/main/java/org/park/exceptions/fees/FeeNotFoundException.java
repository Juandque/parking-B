package org.park.exceptions.fees;

public class FeeNotFoundException extends RuntimeException {
    public FeeNotFoundException(String term) {
        super("Fee with term " + term + " not found");
    }
}
