package edu.unimagdalena.actividadservice.dtos.requests;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record ViajeDtoRequest(
        @NotNull
        Integer idViajero,
        @NotBlank
        @NotNull
        String nombreViaje,
        @NotBlank
        String descripcion,
        @FutureOrPresent
        @NotNull
        LocalDate fechaInicio,
        @FutureOrPresent
        @NotNull
        LocalDate fechaFin
) {
}
