package edu.unimagdalena.actividadservice.dtos.response;

import java.time.LocalDate;

public record ViajeDtoResponse(
        Integer idViaje,
        Integer idViajero,
        String nombreViaje,
        String descripcion,
        LocalDate fechaInicio,
        LocalDate fechaFin
) {
}
