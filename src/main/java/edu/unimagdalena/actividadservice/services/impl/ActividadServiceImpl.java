package edu.unimagdalena.actividadservice.services.impl;

import edu.unimagdalena.actividadservice.dtos.ApiResponse;
import edu.unimagdalena.actividadservice.dtos.requests.ActividadDtoRequest;
import edu.unimagdalena.actividadservice.dtos.response.ActividadDtoResponse;
import edu.unimagdalena.actividadservice.entities.Actividad;
import edu.unimagdalena.actividadservice.entities.TipoActividad;
import edu.unimagdalena.actividadservice.entities.Ubicacion;
import edu.unimagdalena.actividadservice.entities.Viaje;
import edu.unimagdalena.actividadservice.mappers.ActividadMapper;
import edu.unimagdalena.actividadservice.repositories.ActividadRepository;
import edu.unimagdalena.actividadservice.repositories.TipoActividadRepository;
import edu.unimagdalena.actividadservice.repositories.UbicacionRepository;
import edu.unimagdalena.actividadservice.repositories.ViajeRepository;
import edu.unimagdalena.actividadservice.services.ActividadService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ActividadServiceImpl implements ActividadService {

    private final ActividadRepository actividadRepository;
    private final ActividadMapper actividadMapper;
    private final UbicacionRepository ubicacionRepository;
    private final TipoActividadRepository tipoActividadRepository;
    private final ViajeRepository viajeRepository;


    @Override
    public ApiResponse<ActividadDtoResponse> buscarActividadPorId(Integer id) {
        return actividadRepository.findById(id)
                .map(actividad -> new ApiResponse<>(true, actividadMapper.toActividadDtoResponse(actividad), null))
                .orElseGet(() -> new ApiResponse<>(false, null, "Actividad con ID " + id + " no encontrado"));
    }

    @Override
    public List<ApiResponse<ActividadDtoResponse>> buscarTodasActividades() {

        List<Actividad> actividades = actividadRepository.findAll();

        return actividades.stream()
                .map(actividad -> new ApiResponse<>(
                        true,
                        actividadMapper.toActividadDtoResponse(actividad),
                        null
                ))
                .toList();

    }

    @Override
    public ApiResponse<ActividadDtoResponse> agregarActividad(ActividadDtoRequest actividadDtoRequest) {
        Optional<Viaje> viajeOptional = viajeRepository.findById(actividadDtoRequest.idViaje());
        if(!viajeOptional.isPresent()){
            return new ApiResponse<>(false, null, "El viaje con ID " + actividadDtoRequest.idViaje() + " no encontrado");
        }
        Viaje viaje = viajeOptional.get();

        Optional<TipoActividad> tipoActividadOptional = tipoActividadRepository.findById(actividadDtoRequest.idTipoActividad());
        if(!tipoActividadOptional.isPresent()){
            return new ApiResponse<>(false, null, "El tipo actividad con ID " + actividadDtoRequest.idTipoActividad() + " no encontrado");
        }
        TipoActividad tipoActividad = tipoActividadOptional.get();

        Ubicacion ubicacion = null;
        if(actividadDtoRequest.idUbicacion() != null){
            Optional<Ubicacion> ubicacionOptional = ubicacionRepository.findById(actividadDtoRequest.idUbicacion());
            if(!ubicacionOptional.isPresent()){
                return new ApiResponse<>(false, null, "La ubicación con ID " + actividadDtoRequest.idUbicacion() + " no encontrado");
            }
            ubicacion = ubicacionOptional.get();
        }

        Actividad actividad = actividadMapper.toEntity(actividadDtoRequest);
        actividad.setUbicacion(ubicacion);
        actividad.setViaje(viaje);
        actividad.setTipoActividad(tipoActividad);
        return new ApiResponse<>(true, actividadMapper.toActividadDtoResponse(actividadRepository.save(actividad)), null);
    }

    @Override
    public ApiResponse<ActividadDtoResponse> actualizarActividad(Integer id, ActividadDtoRequest actividadDtoRequest) {

        if (actividadDtoRequest == null) {
            return new ApiResponse<>(false, null, "Request de actualización nulo");
        }

        Optional<Actividad> actividadOptional = actividadRepository.findById(id);
        if(!actividadOptional.isPresent()){
            return new ApiResponse<>(false, null, "El viaje con ID " + actividadDtoRequest.idViaje() + " no encontrado");
        }
        Actividad actividad = actividadOptional.get();

        actividadMapper.actualizarActividadDeDto(actividadDtoRequest, actividad);
        try {
            Actividad saved = actividadRepository.save(actividad);
            ActividadDtoResponse dto = actividadMapper.toActividadDtoResponse(saved);
            return new ApiResponse<>(true, dto, null);
        } catch (DataAccessException dae) {
            return new ApiResponse<>(false, null, "Error al actualizar la actividad en la base de datos");
        }
    }

    @Override
    public ApiResponse<Void> eliminarActividad(Integer id) {
        if(!actividadRepository.existsById(id)){
            return new ApiResponse<>(false, null, "Actividad con ID " + id + " no encontrado");
        }

        actividadRepository.deleteById(id);
        return new ApiResponse<>(true, null, null);
    }
}
