package edu.unimagdalena.actividadservice.services.impl;

import edu.unimagdalena.actividadservice.dtos.ApiResponse;
import edu.unimagdalena.actividadservice.dtos.requests.ActividadDtoRequest;
import edu.unimagdalena.actividadservice.dtos.requests.AlojamientoDtoRequest;
import edu.unimagdalena.actividadservice.dtos.requests.TransporteDtoRequest;
import edu.unimagdalena.actividadservice.dtos.response.ActividadDtoResponse;
import edu.unimagdalena.actividadservice.dtos.response.AlojamientoDtoResponse;
import edu.unimagdalena.actividadservice.dtos.response.TransporteDtoResponse;
import edu.unimagdalena.actividadservice.entities.Actividad;
import edu.unimagdalena.actividadservice.entities.Alojamiento;
import edu.unimagdalena.actividadservice.entities.Transporte;
import edu.unimagdalena.actividadservice.mappers.ActividadMapper;
import edu.unimagdalena.actividadservice.mappers.TransporteMapper;
import edu.unimagdalena.actividadservice.repositories.ActividadRepository;
import edu.unimagdalena.actividadservice.repositories.TransporteRepository;
import edu.unimagdalena.actividadservice.services.ActividadService;
import edu.unimagdalena.actividadservice.services.TransporteService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TransporteServiceImpl implements TransporteService {

    private final TransporteRepository transporteRepository;
    private final TransporteMapper transporteMapper;
    private final ActividadService actividadService;
    private final ActividadRepository actividadRepository;

    @Override
    public ApiResponse<TransporteDtoResponse> buscarPorId(Integer id) {
        Transporte transporte = transporteRepository.findById(id).orElse(null);
        if(transporte == null){
            return new ApiResponse<>(false, null, "Actividad con ID " + id + " no encontrado");
        }

        return new ApiResponse<>(true, transporteMapper.toTransporteDto(transporte), null);
    }

    @Override
    public ApiResponse<List<TransporteDtoResponse>> buscarTodos() {
        List<Transporte> transportes = transporteRepository.findAll();
        List<TransporteDtoResponse> dtoList = transportes.stream()
                .map(transporteMapper::toTransporteDto)
                .toList();

        return new ApiResponse<>(true, dtoList, null);
    }

    @Override
    public ApiResponse<TransporteDtoResponse> crear(TransporteDtoRequest dtoRequest) {
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


        Transporte transporte = transporteMapper.toEntity(dtoRequest);
        Actividad actividadRef = actividadRepository.findById(actividadDtoResponse.idActividad()).orElse(null);
        transporte.setActividad(actividadRef);
        Transporte saved = transporteRepository.save(transporte);

        return new ApiResponse<>(true, transporteMapper.toTransporteDto(saved), null);
    }

    @Override
    public ApiResponse<TransporteDtoResponse> actualizar(Integer id, TransporteDtoRequest dtoRequest) {
        Transporte transporte = transporteRepository.findById(id).orElse(null);
        if(transporte == null){
            return new ApiResponse<>(false, null, "Transporte con ID " + id + " no encontrado");
        }

        Actividad actividad = transporte.getActividad();
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

        transporte.setDestinoTransporte(dtoRequest.destinoTransporte());
        transporte.setOrigenTransporte(dtoRequest.origenTransporte());
        transporte.setTipoTransporte(dtoRequest.tipoTransporte());
        transporte.setEmpresa(dtoRequest.empresa());

        transporteRepository.save(transporte);

        return new ApiResponse<>(true, transporteMapper.toTransporteDto(transporte), null);
    }

    @Override
    public ApiResponse<Void> eliminar(Integer id) {
        Transporte transporte = transporteRepository.findById(id).orElse(null);

        if(transporte == null){
            return new ApiResponse<>(false, null, "Transporte con ID " + id + " no encontrado");
        }

        Actividad actividad = transporte.getActividad();

        transporteRepository.delete(transporte);

        if (actividad != null) {
            actividadRepository.delete(actividad);
        }

        return new ApiResponse<>(true, null, null);
    }
}
