package org.park.exceptions.differentStatusExpected;

import org.park.exceptions.BaseBusinessException;
import org.springframework.http.HttpStatus;

import java.util.UUID;

public class PaymentStatusConflictException extends BaseBusinessException {
    public PaymentStatusConflictException(String message) {
        super(message, "ENTITY-STATUS-CONFLICT", HttpStatus.CONFLICT);
    }
}
