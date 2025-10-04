package edu.unimagdalena.actividadservice.dtos.response;

import java.time.LocalDateTime;

public record AlojamientoDtoResponse(
        Integer idActividad,
        Integer idViaje,
        Integer idTipoActividad,
        Integer idUbicacion,
        String titulo,
        String descripcionActividad,
        LocalDateTime fecha,
        float costo,
        String nombreHotel,
        String direccion,
        LocalDateTime checkIn,
        LocalDateTime checkOut,
        String contacto
) {
}
