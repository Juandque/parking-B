package org.park.exceptions.documents;

import org.park.exceptions.BaseBusinessException;
import org.springframework.http.HttpStatus;

public class TicketException extends BaseBusinessException {
    public TicketException(String message, String code, HttpStatus status) {
        super(message, "INTERNAL-SERVER-ERROR ", HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
