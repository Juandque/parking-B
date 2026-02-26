package org.park.exceptions.alreadyExists;

import org.park.exceptions.BaseBusinessException;
import org.springframework.http.HttpStatus;

public class EntityAlreadyExists extends BaseBusinessException {
    public EntityAlreadyExists(String message) {
        super(message, "ENTITY_ALREADY_EXISTS", HttpStatus.CONFLICT);
    }
}
