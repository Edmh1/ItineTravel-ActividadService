package edu.unimagdalena.actividadservice.services.impl;

import edu.unimagdalena.actividadservice.dtos.ApiResponse;
import edu.unimagdalena.actividadservice.dtos.requests.ActividadDtoRequest;
import edu.unimagdalena.actividadservice.dtos.requests.VueloDtoRequest;
import edu.unimagdalena.actividadservice.dtos.response.ActividadDtoResponse;
import edu.unimagdalena.actividadservice.dtos.response.VueloDtoResponse;
import edu.unimagdalena.actividadservice.entities.Actividad;
import edu.unimagdalena.actividadservice.entities.Ubicacion;
import edu.unimagdalena.actividadservice.entities.Vuelo;
import edu.unimagdalena.actividadservice.mappers.VueloMapper;
import edu.unimagdalena.actividadservice.repositories.ActividadRepository;
import edu.unimagdalena.actividadservice.repositories.UbicacionRepository;
import edu.unimagdalena.actividadservice.repositories.VueloRepository;
import edu.unimagdalena.actividadservice.services.ActividadService;
import edu.unimagdalena.actividadservice.services.VueloService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class VueloServiceImpl implements VueloService {

    private final VueloRepository vueloRepository;
    private final VueloMapper vueloMapper;
    private final ActividadService actividadService;
    private final ActividadRepository actividadRepository;
    private final UbicacionRepository ubicacionRepository;

    @Override
    public ApiResponse<VueloDtoResponse> buscarPorId(Integer id) {
        Vuelo vuelo = vueloRepository.findById(id).orElse(null);
        if(vuelo == null){
            return new ApiResponse<>(false, null, "Vuelo con ID " + id + " no encontrado");
        }

        return new ApiResponse<>(true, vueloMapper.toVueloDto(vuelo), null);
    }

    @Override
    public ApiResponse<List<VueloDtoResponse>> buscarTodos() {
        List<Vuelo> vuelos = vueloRepository.findAll();
        List<VueloDtoResponse> dtoList = vuelos.stream()
                .map(vueloMapper::toVueloDto)
                .toList();

        return new ApiResponse<>(true, dtoList, null);
    }

    @Override
    @Transactional
    public ApiResponse<VueloDtoResponse> crearVuelo(VueloDtoRequest dtoRequest) {

        if(dtoRequest.idOrigenVuelo().equals(dtoRequest.idDestinoVuelo())){
            return new ApiResponse<>(false, null, "El lugar de origen no puede ser el mismo que ciudad desitno");
        }

        Optional< Ubicacion> ubicacionOpt = ubicacionRepository.findById(dtoRequest.idOrigenVuelo());
        if(ubicacionOpt.isEmpty()){
            return new ApiResponse<>(false, null, "El ID para el origen: " + dtoRequest.idOrigenVuelo() + " no encontrado");
        }
        Ubicacion origen = ubicacionOpt.get();

        Optional<Ubicacion> ubicacionOpt1 = ubicacionRepository.findById(dtoRequest.idDestinoVuelo());
        if(ubicacionOpt1.isEmpty()){
            return new ApiResponse<>(false, null, "El ID para el destino: " + dtoRequest.idDestinoVuelo() + " no encontrado");
        }
        Ubicacion destino = ubicacionOpt1.get();

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


        Vuelo vuelo = vueloMapper.toEntity(dtoRequest);
        vuelo.setOrigenVuelo(origen);
        vuelo.setDestinoVuelo(destino);
        Actividad actividadRef = actividadRepository.findById(actividadDtoResponse.idActividad()).orElse(null);
        vuelo.setActividad(actividadRef);
        Vuelo saved = vueloRepository.save(vuelo);

        return new ApiResponse<>(true, vueloMapper.toVueloDto(saved), null);
    }

    @Override @Transactional
    public ApiResponse<VueloDtoResponse> actualizar(Integer id, VueloDtoRequest dtoRequest) {
        Vuelo vuelo = vueloRepository.findById(id).orElse(null);
        if(vuelo == null){
            return new ApiResponse<>(false, null, "Vuelo con ID " + id + " no encontrado");
        }

        Actividad actividad = vuelo.getActividad();
        if (actividad == null) {
            return new ApiResponse<>(false, null, "Actividad asociada no encontrada");
        }

        if(dtoRequest.idOrigenVuelo().equals(dtoRequest.idDestinoVuelo())){
            return new ApiResponse<>(false, null, "El lugar de origen no puede ser el mismo que ciudad desitno");
        }

        Optional< Ubicacion> ubicacionOpt = ubicacionRepository.findById(dtoRequest.idOrigenVuelo());
        if(ubicacionOpt.isEmpty()){
            return new ApiResponse<>(false, null, "El ID para el origen: " + dtoRequest.idOrigenVuelo() + " no encontrado");
        }
        Ubicacion origen = ubicacionOpt.get();

        Optional<Ubicacion> ubicacionOpt1 = ubicacionRepository.findById(dtoRequest.idDestinoVuelo());
        if(ubicacionOpt1.isEmpty()){
            return new ApiResponse<>(false, null, "El ID para el destino: " + dtoRequest.idDestinoVuelo() + " no encontrado");
        }
        Ubicacion destino = ubicacionOpt1.get();

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

        vuelo.setAereolineaa(dtoRequest.aereolineaa());
        vuelo.setNVuelo(dtoRequest.nVuelo());
        vuelo.setOrigenVuelo(origen);
        vuelo.setDestinoVuelo(destino);

        vueloRepository.save(vuelo);

        return new ApiResponse<>(true, vueloMapper.toVueloDto(vuelo), null);
    }

    @Override
    public ApiResponse<Void> eliminarVuelo(Integer id) {
        Vuelo vuelo = vueloRepository.findById(id).orElse(null);

        if(vuelo == null){
            return new ApiResponse<>(false, null, "Vuelo con ID " + id + " no encontrado");
        }

        Actividad actividad = vuelo.getActividad();

        vueloRepository.delete(vuelo);

        if (actividad != null) {
            actividadRepository.delete(actividad);
        }

        return new ApiResponse<>(true, null, null);
    }
}
