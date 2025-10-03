package edu.unimagdalena.actividadservice.controllers;

import edu.unimagdalena.actividadservice.dtos.ApiResponse;
import edu.unimagdalena.actividadservice.dtos.requests.ActividadDtoRequest;
import edu.unimagdalena.actividadservice.dtos.requests.ViajeDtoRequest;
import edu.unimagdalena.actividadservice.dtos.requests.ViajeDtoUpdateRequest;
import edu.unimagdalena.actividadservice.dtos.response.ActividadDtoResponse;
import edu.unimagdalena.actividadservice.dtos.response.ActividadDtoResponse;
import edu.unimagdalena.actividadservice.dtos.response.ViajeDtoResponse;
import edu.unimagdalena.actividadservice.services.ActividadService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/actividades")
@RequiredArgsConstructor
public class ActividadController {

    private final ActividadService actividadService;

    @GetMapping
    public ResponseEntity<List<ApiResponse<ActividadDtoResponse>>> buscarActividades() {
        List<ApiResponse<ActividadDtoResponse>> list = actividadService.buscarTodasActividades();
        return ResponseEntity.ok(list);
    }

    @GetMapping("/viajes/{idViaje}")
    public ResponseEntity<ApiResponse<List<ActividadDtoResponse>>> buscarActividadesPorIdViaje(@PathVariable  Integer idViaje){
        ApiResponse<List<ActividadDtoResponse>> list = actividadService.buscarActividadesPorViaje(idViaje);
        if(!list.isSuccess()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(list);
        }

        return ResponseEntity.ok(list);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ActividadDtoResponse>> buscarPorId(@PathVariable Integer id) {
        ApiResponse<ActividadDtoResponse> resp = actividadService.buscarActividadPorId(id);
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
    public ResponseEntity<ApiResponse<ActividadDtoResponse>> crearActividad(
            @Valid @RequestBody ActividadDtoRequest request) {

        ApiResponse<ActividadDtoResponse> resp = actividadService.agregarActividad(request);

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
    public ResponseEntity<ApiResponse<ActividadDtoResponse>> updateActividad(
            @PathVariable Integer id,
            @Valid @RequestBody ActividadDtoRequest request) {

        ApiResponse<ActividadDtoResponse> resp = actividadService.actualizarActividad(id, request);

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
        ApiResponse<Void> resp = actividadService.eliminarActividad(id);

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
