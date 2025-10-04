package edu.unimagdalena.actividadservice.dtos.requests;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.time.LocalDateTime;

public record AlojamientoDtoRequest(
        @NotNull @Positive(message = "El id debe ser positivo")
        Integer idViaje,
        @NotNull @Positive(message = "El id debe ser positivo")
        Integer idTipoActividad,
        @Positive(message = "El id debe ser positivo")
        @Nullable
        Integer idUbicacion,
        @NotNull @NotBlank
        String titulo,
        String descripcionActividad,
        @FutureOrPresent
        LocalDateTime fecha,
        @Positive(message = "El costo no puede ser negativo")
        float costo,

        @NotNull @NotBlank
        String nombreHotel,
        String direccion,
        @FutureOrPresent
        LocalDateTime checkIn,
        @FutureOrPresent
        LocalDateTime checkOut,
        String contacto
) {
}
