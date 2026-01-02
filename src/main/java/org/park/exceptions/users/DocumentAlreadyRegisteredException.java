package org.park.exceptions.users;

public class DocumentAlreadyRegisteredException extends RuntimeException {
    public DocumentAlreadyRegisteredException(String document) {
        super("The document "+document+" already registered");
    }
}
