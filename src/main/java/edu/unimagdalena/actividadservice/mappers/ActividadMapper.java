package edu.unimagdalena.actividadservice.mappers;

import edu.unimagdalena.actividadservice.dtos.requests.ActividadDtoRequest;
import edu.unimagdalena.actividadservice.dtos.requests.ViajeDtoUpdateRequest;
import edu.unimagdalena.actividadservice.dtos.response.ActividadDtoResponse;
import edu.unimagdalena.actividadservice.entities.Actividad;
import edu.unimagdalena.actividadservice.entities.Viaje;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ActividadMapper {

    @Mapping(source = "viaje.idViaje", target = "idViaje")
    @Mapping(source = "tipoActividad.idTipoActividad", target = "idTipoActividad")
    @Mapping(source = "ubicacion.idUbicacion", target = "idUbicacion")
    ActividadDtoResponse toActividadDtoResponse(Actividad actividad);

    @Mapping(target = "viaje", ignore = true)
    @Mapping(target = "ubicacion", ignore = true)
    @Mapping(target = "tipoActividad", ignore = true)
    Actividad toEntity(ActividadDtoRequest actividadDtoRequest);

    void actualizarActividadDeDto(ActividadDtoRequest dto, @MappingTarget Actividad actividad);

}
