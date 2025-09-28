package edu.unimagdalena.actividadservice.services;

import edu.unimagdalena.actividadservice.dtos.ApiResponse;
import edu.unimagdalena.actividadservice.dtos.requests.ViajeDtoRequest;
import edu.unimagdalena.actividadservice.dtos.response.ViajeDtoResponse;
import edu.unimagdalena.actividadservice.dtos.requests.ViajeDtoUpdateRequest;

import java.util.List;

public interface ViajeService {

    ApiResponse<ViajeDtoResponse> buscarViajePorId(Integer idViaje);
    List<ApiResponse<ViajeDtoResponse>> buscarTodosViajes();
    ApiResponse<ViajeDtoResponse> guardarViaje(ViajeDtoRequest viajeDtoRequest);
    ApiResponse<ViajeDtoResponse> actualizarViaje(Integer idViaje, ViajeDtoUpdateRequest viajeDtoRequest);
    ApiResponse<Void> borrarViaje(Integer idViaje);

}
