package edu.unimagdalena.actividadservice.mappers;

import edu.unimagdalena.actividadservice.dtos.requests.TipoActividadDtoReq;
import edu.unimagdalena.actividadservice.dtos.response.TipoActividadDtoRes;
import edu.unimagdalena.actividadservice.entities.TipoActividad;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TipoActividadMapper {

    TipoActividadDtoRes toTipoActividadDtoRes(TipoActividad tipoActividad);
    TipoActividad toEntity(TipoActividadDtoReq tipoActividadDtoReq);
}
