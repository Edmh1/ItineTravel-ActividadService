package edu.unimagdalena.actividadservice.dtos.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record TipoActividadDtoReq(
        @NotBlank(message = "No puede estar en blanco") @NotNull
        String nombreTipoActividad
) {
}
