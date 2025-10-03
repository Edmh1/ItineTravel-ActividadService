package edu.unimagdalena.actividadservice.services;

import edu.unimagdalena.actividadservice.dtos.ApiResponse;
import edu.unimagdalena.actividadservice.dtos.requests.UbicacionDtoRequest;
import edu.unimagdalena.actividadservice.dtos.response.UbicacionDtoResponse;

import java.util.List;

public interface UbicacionService {

    ApiResponse<UbicacionDtoResponse> buscarPorId(Integer id);
    ApiResponse<List<UbicacionDtoResponse>> buscarTodos();
    ApiResponse<UbicacionDtoResponse> agregarUbicacion(UbicacionDtoRequest ubicacionDto);
    ApiResponse<UbicacionDtoResponse> actualizarUbicacion(Integer id, UbicacionDtoRequest ubicacionDto);
    ApiResponse<Void> eliminar(Integer id);
}
