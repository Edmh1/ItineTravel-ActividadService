package edu.unimagdalena.actividadservice.dtos.response;

import java.time.LocalDateTime;

public record TransporteDtoResponse(
        Integer idActividad,
        Integer idViaje,
        Integer idTipoActividad,
        Integer idUbicacion,
        String titulo,
        String descripcionActividad,
        LocalDateTime fecha,
        float costo,
        String tipoTransporte,
        String empresa,
        String origenTransporte,
        String destinoTransporte
) {
}
