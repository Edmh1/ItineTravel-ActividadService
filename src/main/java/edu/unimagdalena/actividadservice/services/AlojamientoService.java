package edu.unimagdalena.actividadservice.services;

import edu.unimagdalena.actividadservice.dtos.ApiResponse;
import edu.unimagdalena.actividadservice.dtos.requests.AlojamientoDtoRequest;
import edu.unimagdalena.actividadservice.dtos.response.AlojamientoDtoResponse;

import java.util.List;

public interface AlojamientoService {

    ApiResponse<AlojamientoDtoResponse> buscarPorId(Integer id);
    ApiResponse<List<AlojamientoDtoResponse>> buscarTodos();
    ApiResponse<AlojamientoDtoResponse> crearAlojamiento(AlojamientoDtoRequest dtoRequest);
    ApiResponse<AlojamientoDtoResponse> actualizar(Integer id, AlojamientoDtoRequest dtoRequest);
    ApiResponse<Void> eliminarAlojamiento(Integer id);
}
