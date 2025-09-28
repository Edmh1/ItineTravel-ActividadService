package edu.unimagdalena.actividadservice.dtos.requests;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.time.LocalDateTime;

public record ActividadDtoRequest(
        @NotNull @Positive(message = "El id debe ser positivo")
        Integer idViaje,
        @NotNull @Positive(message = "El id debe ser positivo")
        Integer idTipoActividad,
        @NotNull @Positive(message = "El id debe ser positivo")
        Integer idUbicacion,
        @NotNull @NotBlank
        String titulo,
        String descripcionActividad,
        @FutureOrPresent
        LocalDateTime fecha,
        @Positive(message = "El costo no puede ser negativo")
        float costo

) {
}
