package edu.unimagdalena.actividadservice.dtos.response;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.time.LocalDateTime;

public record ActividadDtoResponse(

        Integer idViaje,
        Integer idTipoActividad,
        Integer idUbicacion,
        String titulo,
        String descripcionActividad,
        LocalDateTime fecha,
        float costo
) {
}
