package edu.unimagdalena.actividadservice.mappers;

import edu.unimagdalena.actividadservice.dtos.requests.VueloDtoRequest;
import edu.unimagdalena.actividadservice.dtos.response.VueloDtoResponse;
import edu.unimagdalena.actividadservice.entities.Vuelo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface VueloMapper {

    @Mapping(source = "actividad.viaje.idViaje", target = "idViaje")
    @Mapping(source = "actividad.tipoActividad.idTipoActividad", target = "idTipoActividad")
    @Mapping(source = "actividad.ubicacion.idUbicacion", target = "idUbicacion")
    @Mapping(source = "actividad.titulo", target = "titulo")
    @Mapping(source = "actividad.descripcionActividad", target = "descripcionActividad")
    @Mapping(source = "actividad.fecha", target = "fecha")
    @Mapping(source = "actividad.costo", target = "costo")
    @Mapping(source = "origenVuelo.idUbicacion", target = "idOrigenVuelo")
    @Mapping(source = "destinoVuelo.idUbicacion", target = "idDestinoVuelo")
    VueloDtoResponse toVueloDto(Vuelo vuelo);

    @Mapping(target = "origenVuelo", ignore = true)
    @Mapping(target = "destinoVuelo", ignore = true)
    Vuelo toEntity(VueloDtoRequest dtoRequest);
}
