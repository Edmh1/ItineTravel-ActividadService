package edu.unimagdalena.actividadservice.controllers;

import edu.unimagdalena.actividadservice.dtos.ApiResponse;
import edu.unimagdalena.actividadservice.dtos.requests.AlojamientoDtoRequest;
import edu.unimagdalena.actividadservice.dtos.requests.TransporteDtoRequest;
import edu.unimagdalena.actividadservice.dtos.response.AlojamientoDtoResponse;
import edu.unimagdalena.actividadservice.dtos.response.TransporteDtoResponse;
import edu.unimagdalena.actividadservice.services.AlojamientoService;
import edu.unimagdalena.actividadservice.services.TransporteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/actividades/transportes")
@RequiredArgsConstructor
public class TransporteController {

    private final TransporteService transporteService;

    @GetMapping
    ResponseEntity<ApiResponse<List<TransporteDtoResponse>>> buscarTodosAlojamientos(){
        return ResponseEntity.ok(transporteService.buscarTodos());
    }

    @GetMapping("/{id}")
    ResponseEntity<ApiResponse<TransporteDtoResponse>> buscarPorId(@PathVariable Integer id){
        ApiResponse<TransporteDtoResponse> resp = transporteService.buscarPorId(id);
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
    ResponseEntity<ApiResponse<TransporteDtoResponse>> agregar(@RequestBody @Valid TransporteDtoRequest dto){
        ApiResponse<TransporteDtoResponse> resp = transporteService.crear(dto);

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
    ResponseEntity<ApiResponse<TransporteDtoResponse>> actualizar(@RequestBody @Valid TransporteDtoRequest dto, @PathVariable Integer id){
        ApiResponse<TransporteDtoResponse> resp = transporteService.actualizar(id, dto);

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
    public ResponseEntity<ApiResponse<Void>> borrar(@PathVariable Integer id) {
        ApiResponse<Void> resp = transporteService.eliminar(id);

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
