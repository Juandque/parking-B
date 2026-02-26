package org.park.exceptions.notFound;

import org.park.exceptions.BaseBusinessException;
import org.springframework.http.HttpStatus;

public class EntityNotFound extends BaseBusinessException {
    public EntityNotFound(String message) {
        super(message,"ENTITY_NOT_FOUND", HttpStatus.NOT_FOUND);
    }
}
