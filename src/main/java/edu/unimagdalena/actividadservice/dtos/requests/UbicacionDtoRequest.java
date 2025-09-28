package edu.unimagdalena.actividadservice.dtos.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

public record UbicacionDtoRequest(
        @NotBlank(message = "El nombre no puede estar vacío")
        String nombreUbicacion,
        @Positive(message = "El id no puede ser negativo")
        Integer idPadre
) {
}
