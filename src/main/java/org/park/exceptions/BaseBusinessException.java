package org.park.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public abstract class BaseBusinessException extends RuntimeException {
    // Getters
    private final String codigo;
    private final HttpStatus status;

    protected BaseBusinessException(String mensaje, String codigo, HttpStatus status) {
        super(mensaje);
        this.codigo = codigo;
        this.status = status;
    }

}
