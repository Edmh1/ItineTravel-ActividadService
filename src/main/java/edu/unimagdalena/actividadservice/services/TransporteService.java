package edu.unimagdalena.actividadservice.services;

import edu.unimagdalena.actividadservice.dtos.ApiResponse;
import edu.unimagdalena.actividadservice.dtos.requests.TransporteDtoRequest;
import edu.unimagdalena.actividadservice.dtos.response.TransporteDtoResponse;

import java.util.List;

public interface TransporteService {

    ApiResponse<TransporteDtoResponse> buscarPorId(Integer id);
    ApiResponse<List<TransporteDtoResponse>> buscarTodos();
    ApiResponse<TransporteDtoResponse> crear(TransporteDtoRequest dtoRequest);
    ApiResponse<TransporteDtoResponse> actualizar(Integer id, TransporteDtoRequest dtoRequest);
    ApiResponse<Void> eliminar(Integer id);
}
