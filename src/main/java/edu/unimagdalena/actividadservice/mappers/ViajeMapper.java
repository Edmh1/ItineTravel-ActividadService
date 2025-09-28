package edu.unimagdalena.actividadservice.mappers;

import edu.unimagdalena.actividadservice.dtos.requests.ViajeDtoRequest;
import edu.unimagdalena.actividadservice.dtos.requests.ViajeDtoUpdateRequest;
import edu.unimagdalena.actividadservice.dtos.response.ViajeDtoResponse;
import edu.unimagdalena.actividadservice.entities.Viaje;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ViajeMapper {


    ViajeDtoResponse toViajeDtoResponse(Viaje viaje);
    Viaje toEntity(ViajeDtoRequest viajeDto);

    void actualizarViajeDeDto(ViajeDtoUpdateRequest dto, @MappingTarget Viaje viaje);
}
