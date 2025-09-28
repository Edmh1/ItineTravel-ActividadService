package edu.unimagdalena.actividadservice.dtos.requests;

import jakarta.validation.constraints.FutureOrPresent;

import java.time.LocalDate;

public record ViajeDtoUpdateRequest(
        String nombreViaje,
        String descripcion,
        @FutureOrPresent
        LocalDate fechaInicio,
        @FutureOrPresent
        LocalDate fechaFin
) {
}
