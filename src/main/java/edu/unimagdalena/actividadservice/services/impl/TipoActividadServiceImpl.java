package edu.unimagdalena.actividadservice.services.impl;

import edu.unimagdalena.actividadservice.dtos.ApiResponse;
import edu.unimagdalena.actividadservice.dtos.requests.TipoActividadDtoReq;
import edu.unimagdalena.actividadservice.dtos.response.TipoActividadDtoRes;
import edu.unimagdalena.actividadservice.entities.TipoActividad;
import edu.unimagdalena.actividadservice.mappers.TipoActividadMapper;
import edu.unimagdalena.actividadservice.repositories.TipoActividadRepository;
import edu.unimagdalena.actividadservice.services.TipoActividadService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TipoActividadServiceImpl implements TipoActividadService {

    private final TipoActividadRepository tipoActividadRepository;
    private final TipoActividadMapper tipoActividadMapper;


    @Override
    public ApiResponse<List<TipoActividadDtoRes>> buscarTodos() {
        List<TipoActividad> tipoAactividades = tipoActividadRepository.findAll();

        List<TipoActividadDtoRes> dtoList = tipoAactividades.stream()
                .map(tipoActividadMapper::toTipoActividadDtoRes)
                .toList();

        return new ApiResponse<>(true, dtoList, null);

    }

    @Override
    public ApiResponse<TipoActividadDtoRes> buscarPorId(Integer id) {
        return tipoActividadRepository.findById(id)
                .map(tipoActividad -> new ApiResponse<>(true, tipoActividadMapper.toTipoActividadDtoRes(tipoActividad), null))
                .orElseGet(() -> new ApiResponse<>(false, null, "Actividad con ID " + id + " no encontrado"));
    }

    @Override
    public ApiResponse<TipoActividadDtoRes> guardarTipo(TipoActividadDtoReq tipoDto) {
        TipoActividad tipoActividad = tipoActividadMapper.toEntity(tipoDto);

        return new ApiResponse<>(true, tipoActividadMapper.toTipoActividadDtoRes(tipoActividadRepository.save(tipoActividad)), null);
    }

    @Override
    public ApiResponse<TipoActividadDtoRes> actualizarTipo(Integer id, TipoActividadDtoReq tipoDto) {
        Optional<TipoActividad> tipoActividadOptional = tipoActividadRepository.findById(id);
        if(!tipoActividadOptional.isPresent()){
            return new ApiResponse<>(false, null, "Tipo Actividad con ID " + id + " no encontrado");
        }

        TipoActividad tipoActividad = tipoActividadOptional.get();
        tipoActividad.setNombreTipoActividad(tipoDto.nombreTipoActividad());

        try {
            TipoActividad saved = tipoActividadRepository.save(tipoActividad);
            TipoActividadDtoRes dto = tipoActividadMapper.toTipoActividadDtoRes(saved);
            return new ApiResponse<>(true, dto, null);
        } catch (DataAccessException dae) {
            return new ApiResponse<>(false, null, "Error al actualizar el tipo actividad en la base de datos");
        }
    }

    @Override
    public ApiResponse<Void> eliminarTipo(Integer id) {
        if(!tipoActividadRepository.existsById(id)){
            return new ApiResponse<>(false, null, "Tipo Actividad con ID " + id + " no encontrado");
        }
        tipoActividadRepository.deleteById(id);
        return new ApiResponse<>(true, null, null);
    }
}

