package edu.unimagdalena.actividadservice.controllers;

import edu.unimagdalena.actividadservice.dtos.ApiResponse;
import edu.unimagdalena.actividadservice.dtos.requests.VueloDtoRequest;
import edu.unimagdalena.actividadservice.dtos.response.VueloDtoResponse;
import edu.unimagdalena.actividadservice.services.VueloService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/actividades/vuelos")
public class VueloController {
    private final VueloService vueloService;

    @GetMapping
    ResponseEntity<ApiResponse<List<VueloDtoResponse>>> buscarTodosAlojamientos(){
        return ResponseEntity.ok(vueloService.buscarTodos());
    }

    @GetMapping("/{id}")
    ResponseEntity<ApiResponse<VueloDtoResponse>> buscarPorId(@PathVariable Integer id){
        ApiResponse<VueloDtoResponse> resp = vueloService.buscarPorId(id);
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
    ResponseEntity<ApiResponse<VueloDtoResponse>> agregarAlojamiento(@RequestBody @Valid VueloDtoRequest dto){
        ApiResponse<VueloDtoResponse> resp = vueloService.crearVuelo(dto);

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
    ResponseEntity<ApiResponse<VueloDtoResponse>> actualizarAlojamiento(@RequestBody @Valid VueloDtoRequest dto, @PathVariable Integer id){
        ApiResponse<VueloDtoResponse> resp = vueloService.actualizar(id, dto);

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
    public ResponseEntity<ApiResponse<Void>> borrarVuelo(@PathVariable Integer id) {
        ApiResponse<Void> resp = vueloService.eliminarVuelo(id);

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
