package edu.unimagdalena.actividadservice.dtos.response;

import java.time.LocalDateTime;

public record VueloDtoResponse(
        Integer idActividad,
        Integer idViaje,
        Integer idTipoActividad,
        Integer idUbicacion,
        String titulo,
        String descripcionActividad,
        LocalDateTime fecha,
        float costo,
        String aereolineaa,
        Integer nVuelo,
        Integer idOrigenVuelo,
        Integer idDestinoVuelo
) {
}
