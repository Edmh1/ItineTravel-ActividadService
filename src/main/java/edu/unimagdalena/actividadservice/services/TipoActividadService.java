package edu.unimagdalena.actividadservice.services;

import edu.unimagdalena.actividadservice.dtos.ApiResponse;
import edu.unimagdalena.actividadservice.dtos.requests.TipoActividadDtoReq;
import edu.unimagdalena.actividadservice.dtos.response.TipoActividadDtoRes;

import java.util.List;

public interface TipoActividadService {

    List<ApiResponse<TipoActividadDtoRes>> buscarTodos();
    ApiResponse<TipoActividadDtoRes> buscarPorId(Integer id);
    ApiResponse<TipoActividadDtoRes> guardarTipo(TipoActividadDtoReq tipoDto);
    ApiResponse<TipoActividadDtoRes> actualizarTipo(Integer id, TipoActividadDtoReq tipoDto);
    ApiResponse<Void> eliminarTipo(Integer id);
}
