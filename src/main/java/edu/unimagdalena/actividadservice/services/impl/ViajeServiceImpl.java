package edu.unimagdalena.actividadservice.services.impl;

import edu.unimagdalena.actividadservice.dtos.ApiResponse;
import edu.unimagdalena.actividadservice.dtos.requests.ViajeDtoRequest;
import edu.unimagdalena.actividadservice.dtos.response.ViajeDtoResponse;
import edu.unimagdalena.actividadservice.dtos.requests.ViajeDtoUpdateRequest;
import edu.unimagdalena.actividadservice.entities.Viaje;
import edu.unimagdalena.actividadservice.mappers.ViajeMapper;
import edu.unimagdalena.actividadservice.repositories.ViajeRepository;
import edu.unimagdalena.actividadservice.services.ViajeService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ViajeServiceImpl implements ViajeService {

    private final ViajeRepository viajeRepository;
    private final ViajeMapper viajeMapper;

    @Override
    public ApiResponse<ViajeDtoResponse> buscarViajePorId(Integer idViaje) {
        return viajeRepository.findById(idViaje)
                .map(viaje -> new ApiResponse<>(true, viajeMapper.toViajeDtoResponse(viaje), null))
                .orElseGet(() -> new ApiResponse<>(false, null, "Viaje con ID " + idViaje + " no encontrado"));
    }

    @Override
    public ApiResponse<List<ViajeDtoResponse>> buscarTodosViajes() {
        List<Viaje> viajes = viajeRepository.findAll();

        List<ViajeDtoResponse> dtoList = viajes.stream()
                .map(viajeMapper::toViajeDtoResponse)
                .toList();

        return new ApiResponse<>(true, dtoList, null);
    }

    @Override
    public ApiResponse<List<ViajeDtoResponse>> buscarTodosViajesPorUsuario(Integer idUsuario) {
        List<Viaje> viajes = viajeRepository.findAllByIdViajero(idUsuario);
        List<ViajeDtoResponse> listaDto = viajes.stream()
                .map(viajeMapper::toViajeDtoResponse)
                .toList();

        return new ApiResponse<>(true, listaDto, null);
    }

    @Override
    public ApiResponse<ViajeDtoResponse> guardarViaje(ViajeDtoRequest viajeDtoRequest) {
        if (viajeDtoRequest.fechaInicio().isAfter(viajeDtoRequest.fechaFin())) {
            return new ApiResponse<>(false, null, "La fecha de inicio no puede ser después que la fecha de fin");
        }

        Viaje viaje = viajeMapper.toEntity(viajeDtoRequest);
        Viaje nuevoViaje = viajeRepository.save(viaje);
        ViajeDtoResponse dto = viajeMapper.toViajeDtoResponse(nuevoViaje);

        return new ApiResponse<>(true, dto, null);
    }

    @Override
    public ApiResponse<ViajeDtoResponse> actualizarViaje(Integer idViaje, ViajeDtoUpdateRequest viajeDtoRequest) {

        if (viajeDtoRequest == null) {
            return new ApiResponse<>(false, null, "Request de actualización nulo");
        }


        Optional<Viaje> optionalViaje = viajeRepository.findById(idViaje);
        if (optionalViaje.isEmpty()) {
            return new ApiResponse<>(false, null, "El viaje con ID " + idViaje + " no encontrado");
        }
        Viaje viaje = optionalViaje.get();


        LocalDate reqInicio = viajeDtoRequest.fechaInicio();
        LocalDate reqFin = viajeDtoRequest.fechaFin();
        LocalDate currentInicio = viaje.getFechaInicio();
        LocalDate currentFin = viaje.getFechaFin();

        if (reqInicio != null && reqFin != null) {
            if (reqInicio.isAfter(reqFin)) {
                return new ApiResponse<>(false, null, "La fecha de inicio no puede ser posterior a la fecha de fin");
            }
        }

        if (reqInicio != null && reqFin == null && currentFin != null) {
            if (reqInicio.isAfter(currentFin)) {
                return new ApiResponse<>(false, null, "La fecha de inicio no puede ser posterior a la fecha de fin existente");
            }
        }

        if (reqFin != null && reqInicio == null && currentInicio != null) {
            if (currentInicio.isAfter(reqFin)) {
                return new ApiResponse<>(false, null, "La fecha de fin no puede ser anterior a la fecha de inicio existente");
            }
        }

        viajeMapper.actualizarViajeDeDto(viajeDtoRequest, viaje);

        try {
            Viaje saved = viajeRepository.save(viaje);
            ViajeDtoResponse dto = viajeMapper.toViajeDtoResponse(saved);
            return new ApiResponse<>(true, dto, null);
        } catch (DataAccessException dae) {
            return new ApiResponse<>(false, null, "Error al actualizar el viaje en la base de datos");
        }
    }

    @Override
    public ApiResponse<Void> borrarViaje(Integer idViaje) {

        if(!viajeRepository.existsById(idViaje)){
            return new ApiResponse<>(false, null, "Viaje con ID " + idViaje + " no encontrado");
        }

        viajeRepository.deleteById(idViaje);
        return new ApiResponse<>(true, null, null);

    }
}
