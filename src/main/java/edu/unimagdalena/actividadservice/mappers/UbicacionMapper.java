package edu.unimagdalena.actividadservice.mappers;

import edu.unimagdalena.actividadservice.dtos.requests.UbicacionDtoRequest;
import edu.unimagdalena.actividadservice.dtos.response.UbicacionDtoResponse;
import edu.unimagdalena.actividadservice.entities.Ubicacion;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UbicacionMapper {

    @Mapping(source = "padre.idUbicacion", target = "idPadre")
    UbicacionDtoResponse toUbicacionDto(Ubicacion ubicacion);

    @Mapping(target = "padre", ignore = true)
    Ubicacion toEntity(UbicacionDtoRequest ubicacionDto);
}
