package edu.unimagdalena.actividadservice.controllers;

import edu.unimagdalena.actividadservice.dtos.ApiResponse;
import edu.unimagdalena.actividadservice.dtos.requests.TipoActividadDtoReq;
import edu.unimagdalena.actividadservice.dtos.response.TipoActividadDtoRes;
import edu.unimagdalena.actividadservice.services.TipoActividadService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tipoActividades")
@RequiredArgsConstructor
public class TipoActividadController {

    private final TipoActividadService tipoActividadService;

    @GetMapping
    public ResponseEntity<List<ApiResponse<TipoActividadDtoRes>>> buscarTipoActividades() {
        List<ApiResponse<TipoActividadDtoRes>> list = tipoActividadService.buscarTodos();
        return ResponseEntity.ok(list);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<TipoActividadDtoRes>> buscarPorId(@PathVariable Integer id) {
        ApiResponse<TipoActividadDtoRes> resp = tipoActividadService.buscarPorId(id);
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
    public ResponseEntity<ApiResponse<TipoActividadDtoRes>> crearTipoActividad(
            @Valid @RequestBody TipoActividadDtoReq request) {

        ApiResponse<TipoActividadDtoRes> resp = tipoActividadService.guardarTipo(request);

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
    public ResponseEntity<ApiResponse<TipoActividadDtoRes>> updateTipoActividad(
            @PathVariable Integer id,
            @Valid @RequestBody TipoActividadDtoReq request) {

        ApiResponse<TipoActividadDtoRes> resp = tipoActividadService.actualizarTipo(id, request);

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
        ApiResponse<Void> resp = tipoActividadService.eliminarTipo(id);

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
