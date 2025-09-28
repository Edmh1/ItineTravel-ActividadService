package edu.unimagdalena.actividadservice.dtos.response;

public record UbicacionDtoResponse(
        Integer idUbicacion,
        String nombreUbicacion,
        Integer idPadre
) {
}
