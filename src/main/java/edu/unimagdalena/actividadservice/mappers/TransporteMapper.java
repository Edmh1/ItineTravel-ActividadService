package edu.unimagdalena.actividadservice.mappers;

import edu.unimagdalena.actividadservice.dtos.requests.TransporteDtoRequest;
import edu.unimagdalena.actividadservice.dtos.response.TransporteDtoResponse;
import edu.unimagdalena.actividadservice.entities.Transporte;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TransporteMapper {

    @Mapping(source = "actividad.viaje.idViaje", target = "idViaje")
    @Mapping(source = "actividad.tipoActividad.idTipoActividad", target = "idTipoActividad")
    @Mapping(source = "actividad.ubicacion.idUbicacion", target = "idUbicacion")
    @Mapping(source = "actividad.titulo", target = "titulo")
    @Mapping(source = "actividad.descripcionActividad", target = "descripcionActividad")
    @Mapping(source = "actividad.fecha", target = "fecha")
    @Mapping(source = "actividad.costo", target = "costo")
    TransporteDtoResponse toTransporteDto(Transporte transporte);


    Transporte toEntity(TransporteDtoRequest transporteDto);
}
