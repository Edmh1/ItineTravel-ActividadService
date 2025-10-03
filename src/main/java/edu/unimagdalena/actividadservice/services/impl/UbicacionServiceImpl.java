package edu.unimagdalena.actividadservice.services.impl;

import edu.unimagdalena.actividadservice.dtos.ApiResponse;
import edu.unimagdalena.actividadservice.dtos.requests.UbicacionDtoRequest;
import edu.unimagdalena.actividadservice.dtos.response.UbicacionDtoResponse;
import edu.unimagdalena.actividadservice.entities.Ubicacion;
import edu.unimagdalena.actividadservice.mappers.UbicacionMapper;
import edu.unimagdalena.actividadservice.repositories.UbicacionRepository;
import edu.unimagdalena.actividadservice.services.UbicacionService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UbicacionServiceImpl implements UbicacionService {

    private final UbicacionRepository ubicacionRepository;
    private final UbicacionMapper ubicacionMapper;


    @Override
    public ApiResponse<UbicacionDtoResponse> buscarPorId(Integer id) {
        return ubicacionRepository.findById(id)
                .map(ubicacion -> new ApiResponse<>(true, ubicacionMapper.toUbicacionDto(ubicacion), null))
                .orElseGet(() -> new ApiResponse<>(false, null, "Ubicación con ID " + id + " no encontrado"));

    }

    @Override
    public ApiResponse<List<UbicacionDtoResponse>> buscarTodos() {
        List<Ubicacion> ubicaciones = ubicacionRepository.findAll();

        List<UbicacionDtoResponse> dtoList = ubicaciones.stream()
                .map(ubicacionMapper::toUbicacionDto)
                .toList();

        return new ApiResponse<>(true, dtoList, null);
    }

    @Override
    public ApiResponse<UbicacionDtoResponse> agregarUbicacion(UbicacionDtoRequest ubicacionDto) {

        Ubicacion ubicacion = ubicacionMapper.toEntity(ubicacionDto);

        if (ubicacionDto.idPadre() != null) {

            Optional<Ubicacion> ubicacionOptional = ubicacionRepository.findById(ubicacionDto.idPadre());

            if (!ubicacionOptional.isPresent()) {
                return new ApiResponse<>(false, null, "Ubicación con ID " + ubicacionDto.idPadre() + " no encontrado");
            }

            Ubicacion padre = ubicacionOptional.get();
            ubicacion.setPadre(padre);

        }

        Ubicacion saved = ubicacionRepository.save(ubicacion);

        return new ApiResponse<>(true, ubicacionMapper.toUbicacionDto(saved), null);
    }

    @Override
    public ApiResponse<UbicacionDtoResponse> actualizarUbicacion(Integer id, UbicacionDtoRequest ubicacionDto) {
        Optional<Ubicacion> ubicacionOptional = ubicacionRepository.findById(id);
        if(!ubicacionOptional.isPresent()){
            return new ApiResponse<>(false, null, "Ubicación con ID " + id + " no encontrado");
        }

        Optional<Ubicacion> padreOptional = ubicacionRepository.findById(ubicacionDto.idPadre());
        if(!padreOptional.isPresent()){
            return new ApiResponse<>(false, null, "Ubicacion padre con ID " + ubicacionDto.idPadre() + " no encontrado");
        }
        Ubicacion padre = padreOptional.get();

        Ubicacion ubicacion = ubicacionOptional.get();
        ubicacion.setNombreUbicacion(ubicacionDto.nombreUbicacion());
        ubicacion.setPadre(padre);

        try {
            Ubicacion saved = ubicacionRepository.save(ubicacion);
            UbicacionDtoResponse dto = ubicacionMapper.toUbicacionDto(saved);
            return new ApiResponse<>(true, dto, null);
        } catch (DataAccessException dae) {
            return new ApiResponse<>(false, null, "Error al actualizar el tipo actividad en la base de datos");
        }
    }

    @Override
    public ApiResponse<Void> eliminar(Integer id) {
        if(!ubicacionRepository.existsById(id)){
            return new ApiResponse<>(false, null, "Ubicación con ID " + id + " no encontrado");
        }

        ubicacionRepository.deleteById(id);
        return new ApiResponse<>(true, null, null);
    }
}
