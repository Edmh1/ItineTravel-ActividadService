package edu.unimagdalena.actividadservice.services;

import edu.unimagdalena.actividadservice.dtos.ApiResponse;
import edu.unimagdalena.actividadservice.dtos.requests.ActividadDtoRequest;
import edu.unimagdalena.actividadservice.dtos.response.ActividadDtoResponse;

import java.util.List;

public interface ActividadService {
    ApiResponse<ActividadDtoResponse> buscarActividadPorId(Integer id);
    List<ApiResponse<ActividadDtoResponse>> buscarTodasActividades();
    ApiResponse<List<ActividadDtoResponse>> buscarActividadesPorViaje(Integer idViaje);
    ApiResponse<ActividadDtoResponse> agregarActividad(ActividadDtoRequest actividadDtoRequest);
    ApiResponse<ActividadDtoResponse> actualizarActividad(Integer id, ActividadDtoRequest actividadDtoRequest);
    ApiResponse<Void> eliminarActividad(Integer id);

}
