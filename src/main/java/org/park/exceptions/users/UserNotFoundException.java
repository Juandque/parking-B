package org.park.exceptions.users;

import java.util.UUID;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String term) {
        super("User with term search: " + term + " not found");
    }
}
