package edu.unimagdalena.actividadservice.controllers;

import edu.unimagdalena.actividadservice.dtos.ApiResponse;
import edu.unimagdalena.actividadservice.dtos.requests.ViajeDtoRequest;
import edu.unimagdalena.actividadservice.dtos.requests.ViajeDtoUpdateRequest;
import edu.unimagdalena.actividadservice.dtos.response.ViajeDtoResponse;
import edu.unimagdalena.actividadservice.services.ViajeService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/viajes")
@RequiredArgsConstructor
@Validated
public class ViajeController {

    private final ViajeService viajeService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<ViajeDtoResponse>>> buscarViajes() {
        return ResponseEntity.ok(viajeService.buscarTodosViajes());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ViajeDtoResponse>> buscarPorId(@PathVariable Integer id) {
        ApiResponse<ViajeDtoResponse> resp = viajeService.buscarViajePorId(id);
        if (resp.isSuccess()) {
            return ResponseEntity.ok(resp);
        }

        String err = resp.getError() == null ? "" : resp.getError().toLowerCase();
        if (err.contains("no encontrado") || err.contains("not found")) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(resp);
        }
        return ResponseEntity.badRequest().body(resp);
    }

    @GetMapping("/usuarios/{idUsuario}")
    public ResponseEntity<ApiResponse<List<ViajeDtoResponse>>> buscarViajesPorUsuario(@PathVariable @NotNull Integer idUsuario){
        ApiResponse<List<ViajeDtoResponse>> response = viajeService.buscarTodosViajesPorUsuario(idUsuario);
        if(!response.isSuccess()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }

        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<ApiResponse<ViajeDtoResponse>> crearViaje(
            @Valid @RequestBody ViajeDtoRequest request) {

        ApiResponse<ViajeDtoResponse> resp = viajeService.guardarViaje(request);

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
    public ResponseEntity<ApiResponse<ViajeDtoResponse>> update(
            @PathVariable Integer id,
            @Valid @RequestBody ViajeDtoUpdateRequest request) {

        ApiResponse<ViajeDtoResponse> resp = viajeService.actualizarViaje(id, request);

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
        ApiResponse<Void> resp = viajeService.borrarViaje(id);

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
