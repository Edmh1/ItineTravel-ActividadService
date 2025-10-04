package edu.unimagdalena.actividadservice.services;

import edu.unimagdalena.actividadservice.dtos.ApiResponse;
import edu.unimagdalena.actividadservice.dtos.requests.VueloDtoRequest;
import edu.unimagdalena.actividadservice.dtos.response.VueloDtoResponse;

import java.util.List;

public interface VueloService {

    ApiResponse<VueloDtoResponse> buscarPorId(Integer id);
    ApiResponse<List<VueloDtoResponse>> buscarTodos();
    ApiResponse<VueloDtoResponse> crearVuelo(VueloDtoRequest dtoRequest);
    ApiResponse<VueloDtoResponse> actualizar(Integer id, VueloDtoRequest dtoRequest);
    ApiResponse<Void> eliminarVuelo(Integer id);
}
