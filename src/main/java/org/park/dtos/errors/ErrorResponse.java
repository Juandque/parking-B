package org.park.dtos.errors;

import java.time.LocalDateTime;
import java.util.Map;

public record ErrorResponse(
        String codigo,
        String mensaje,
        LocalDateTime timestamp,
        Map<String, String> detalles
) {

    public ErrorResponse(String codigo, String mensaje) {
        this(codigo, mensaje, LocalDateTime.now(), null);
    }
}
