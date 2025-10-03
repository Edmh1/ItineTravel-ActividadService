package edu.unimagdalena.actividadservice.controllers;

import edu.unimagdalena.actividadservice.dtos.ApiResponse;
import edu.unimagdalena.actividadservice.dtos.requests.TipoActividadDtoReq;
import edu.unimagdalena.actividadservice.dtos.requests.UbicacionDtoRequest;
import edu.unimagdalena.actividadservice.dtos.response.TipoActividadDtoRes;
import edu.unimagdalena.actividadservice.dtos.response.UbicacionDtoResponse;
import edu.unimagdalena.actividadservice.services.UbicacionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ubicaciones")
@RequiredArgsConstructor
public class UbicacionController {

    private final UbicacionService ubicacionService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<UbicacionDtoResponse>>> buscarUbicaciones() {
        return ResponseEntity.ok(ubicacionService.buscarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<UbicacionDtoResponse>> buscarPorId(@PathVariable Integer id) {
        ApiResponse<UbicacionDtoResponse> resp = ubicacionService.buscarPorId(id);
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
    public ResponseEntity<ApiResponse<UbicacionDtoResponse>> crearUbicacion(
            @Valid @RequestBody UbicacionDtoRequest request) {

        ApiResponse<UbicacionDtoResponse> resp = ubicacionService.agregarUbicacion(request);

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
    public ResponseEntity<ApiResponse<UbicacionDtoResponse>> actualizarUbicacion(
            @PathVariable Integer id,
            @Valid @RequestBody UbicacionDtoRequest request) {

        ApiResponse<UbicacionDtoResponse> resp = ubicacionService.actualizarUbicacion(id, request);

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
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Integer id) {
        ApiResponse<Void> resp = ubicacionService.eliminar(id);

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
