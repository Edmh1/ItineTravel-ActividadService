package edu.unimagdalena.actividadservice.services.impl;

import edu.unimagdalena.actividadservice.dtos.ApiResponse;
import edu.unimagdalena.actividadservice.dtos.requests.ActividadDtoRequest;
import edu.unimagdalena.actividadservice.dtos.requests.AlojamientoDtoRequest;
import edu.unimagdalena.actividadservice.dtos.response.ActividadDtoResponse;
import edu.unimagdalena.actividadservice.dtos.response.AlojamientoDtoResponse;
import edu.unimagdalena.actividadservice.entities.Actividad;
import edu.unimagdalena.actividadservice.entities.Alojamiento;
import edu.unimagdalena.actividadservice.mappers.AlojamientoMapper;
import edu.unimagdalena.actividadservice.repositories.ActividadRepository;
import edu.unimagdalena.actividadservice.repositories.AlojamientoRepository;
import edu.unimagdalena.actividadservice.services.ActividadService;
import edu.unimagdalena.actividadservice.services.AlojamientoService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AlojamientoServiceImpl implements AlojamientoService {

    private final AlojamientoRepository alojamientoRepository;
    private final AlojamientoMapper alojamientoMapper;
    private final ActividadService actividadService;
    private final ActividadRepository actividadRepository;

    @Override
    public ApiResponse<AlojamientoDtoResponse> buscarPorId(Integer id) {
        Alojamiento alojamiento = alojamientoRepository.findById(id).orElse(null);
        if(alojamiento == null){
            return new ApiResponse<>(false, null, "Actividad con ID " + id + " no encontrado");
        }

        return new ApiResponse<>(true, alojamientoMapper.toAlojamientoDto(alojamiento), null);
    }

    @Override
    public ApiResponse<List<AlojamientoDtoResponse>> buscarTodos() {

        List<Alojamiento> alojamientos = alojamientoRepository.findAll();
        List<AlojamientoDtoResponse> dtoList = alojamientos.stream()
                .map(alojamientoMapper::toAlojamientoDto)
                .toList();

        return new ApiResponse<>(true, dtoList, null);

    }

    @Override
    public ApiResponse<AlojamientoDtoResponse> crearAlojamiento(AlojamientoDtoRequest dtoRequest) {
        ActividadDtoRequest actividadDto = new ActividadDtoRequest(
                dtoRequest.idViaje(),
                dtoRequest.idTipoActividad(),
                dtoRequest.idUbicacion(),
                dtoRequest.titulo(),
                dtoRequest.descripcionActividad(),
                dtoRequest.fecha(),
                dtoRequest.costo()
        );

        ApiResponse<ActividadDtoResponse> respuestaActividad = actividadService.agregarActividad(actividadDto);

        if(!respuestaActividad.isSuccess() || respuestaActividad.getData() == null){
            return new ApiResponse<>(false, null, "No se pudo crear la actividad: " + respuestaActividad.getError());
        }

        ActividadDtoResponse actividadDtoResponse = respuestaActividad.getData();


        Alojamiento alojamiento = alojamientoMapper.toEntity(dtoRequest);
        Actividad actividadRef = actividadRepository.findById(actividadDtoResponse.idActividad()).orElse(null);
        alojamiento.setActividad(actividadRef);
        Alojamiento saved = alojamientoRepository.save(alojamiento);

        return new ApiResponse<>(true, alojamientoMapper.toAlojamientoDto(saved), null);
    }

    @Override @Transactional
    public ApiResponse<AlojamientoDtoResponse> actualizar(Integer id, AlojamientoDtoRequest dtoRequest) {

        Alojamiento alojamiento = alojamientoRepository.findById(id).orElse(null);
        if(alojamiento == null){
            return new ApiResponse<>(false, null, "Alojamiento con ID " + id + " no encontrado");
        }

        Actividad actividad = alojamiento.getActividad();
        if (actividad == null) {
            return new ApiResponse<>(false, null, "Actividad asociada no encontrada");
        }

        ActividadDtoRequest actividadDto = new ActividadDtoRequest(
                dtoRequest.idViaje(),
                dtoRequest.idTipoActividad(),
                dtoRequest.idUbicacion(),
                dtoRequest.titulo(),
                dtoRequest.descripcionActividad(),
                dtoRequest.fecha(),
                dtoRequest.costo()
        );

        ApiResponse<ActividadDtoResponse> response = actividadService.actualizarActividad(actividad.getIdActividad(), actividadDto);

        if(!response.isSuccess() || response.getData() == null){
            return new ApiResponse<>(false, null, "No se pudo actualizar la actividad: " + response.getError());
        }

        alojamiento.setNombreHotel(dtoRequest.nombreHotel());
        alojamiento.setDireccion(dtoRequest.direccion());
        alojamiento.setCheckIn(dtoRequest.checkIn());
        alojamiento.setCheckOut(dtoRequest.checkOut());
        alojamiento.setContacto(dtoRequest.contacto());

        alojamientoRepository.save(alojamiento);

        return new ApiResponse<>(true, alojamientoMapper.toAlojamientoDto(alojamiento), null);
    }

    @Override
    public ApiResponse<Void> eliminarAlojamiento(Integer id) {
        Alojamiento alojamiento = alojamientoRepository.findById(id).orElse(null);

        if(alojamiento == null){
            return new ApiResponse<>(false, null, "Alojamiento con ID " + id + " no encontrado");
        }

        Actividad actividad = alojamiento.getActividad();

        alojamientoRepository.delete(alojamiento);

        if (actividad != null) {
            actividadRepository.delete(actividad);
        }

        return new ApiResponse<>(true, null, null);
    }
}
