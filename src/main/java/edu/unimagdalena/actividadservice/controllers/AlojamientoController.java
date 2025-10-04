package edu.unimagdalena.actividadservice.controllers;

import edu.unimagdalena.actividadservice.dtos.ApiResponse;
import edu.unimagdalena.actividadservice.dtos.requests.AlojamientoDtoRequest;
import edu.unimagdalena.actividadservice.dtos.response.AlojamientoDtoResponse;
import edu.unimagdalena.actividadservice.services.AlojamientoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/actividades/alojamientos")
@RequiredArgsConstructor
public class AlojamientoController {

    private final AlojamientoService alojamientoService;

    @GetMapping
    ResponseEntity<ApiResponse<List<AlojamientoDtoResponse>>> buscarTodosAlojamientos(){
        return ResponseEntity.ok(alojamientoService.buscarTodos());
    }

    @GetMapping("/{id}")
    ResponseEntity<ApiResponse<AlojamientoDtoResponse>> buscarPorId(@PathVariable Integer id){
        ApiResponse<AlojamientoDtoResponse> resp = alojamientoService.buscarPorId(id);
        if (resp.isSuccess()) {
            return ResponseEntity.ok(resp);
        }

        String err = resp.getError() == null ? "" : resp.getError().toLowerCase();
        if (err.contains("no encontrado") || err.contains("not found")) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(resp);
        }
        return ResponseEntity.badRequest().body(resp);
    }

    @PostMapping
    ResponseEntity<ApiResponse<AlojamientoDtoResponse>> agregarAlojamiento(@RequestBody @Valid AlojamientoDtoRequest dto){
        ApiResponse<AlojamientoDtoResponse> resp = alojamientoService.crearAlojamiento(dto);

        if (resp == null) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>(false, null, "Respuesta nula del servicio"));
        }

        if (!resp.isSuccess()) {
            return ResponseEntity.badRequest().body(resp);
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(resp);
    }

    @PutMapping("/{id}")
    ResponseEntity<ApiResponse<AlojamientoDtoResponse>> actualizarAlojamiento(@RequestBody @Valid AlojamientoDtoRequest dto, @PathVariable Integer id){
        ApiResponse<AlojamientoDtoResponse> resp = alojamientoService.actualizar(id, dto);

        if (resp.isSuccess()) {
            return ResponseEntity.ok(resp);
        }

        String err = resp.getError() == null ? "" : resp.getError().toLowerCase();
        if (err.contains("no encontrado") || err.contains("not found")) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(resp);
        }
        return ResponseEntity.badRequest().body(resp);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> borrarAlojamiento(@PathVariable Integer id) {
        ApiResponse<Void> resp = alojamientoService.eliminarAlojamiento(id);

        if (resp.isSuccess()) {

            return ResponseEntity.noContent().build();
        }

        String err = resp.getError() == null ? "" : resp.getError().toLowerCase();
        if (err.contains("no encontrado") || err.contains("not found")) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(resp);
        }
        return ResponseEntity.badRequest().body(resp);
    }

}
