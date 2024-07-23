package com.example.demo.Controllers;

import com.example.demo.Entitys.Enum.Baja_Alta;
import com.example.demo.Entitys.UnidadMedida;
import com.example.demo.Services.UnidadMedidaService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "v1/api/um")
@RequiredArgsConstructor
public class UnidadMedidaController {
    private final UnidadMedidaService unidadService;

    @PostMapping()
    public ResponseEntity<?> createUnidadMedida(@RequestBody UnidadMedida unidadMedida) throws Exception {
        try {
            unidadService.save(unidadMedida);

            return ResponseEntity.status(HttpStatus.OK).body(unidadMedida);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of(
                    "success", false,
                    "message", e.getMessage()
            ));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateUnidadMedida(@RequestBody UnidadMedida unidadMedida, @PathVariable("id") Long id) throws Exception {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(unidadService.updateUnidadMedida(id, unidadMedida));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of(
                    "success", false,
                    "message", e.getMessage()
            ));
        }
    }

    @GetMapping()
    public ResponseEntity<?> findAllUnidades() throws Exception {
        try {
            List<UnidadMedida> unidadMedidas = unidadService.findAllByEstadoDisponible();
            if (unidadMedidas.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body(unidadMedidas);
            }
            return ResponseEntity.status(HttpStatus.OK).body(unidadMedidas);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of(
                    "success", false,
                    "message", e.getMessage()
            ));
        }
    }

    @GetMapping("/filter")
    public ResponseEntity<?> filterAllUnidades(
            //OPTIONAL PARAMS
            @RequestParam(required = false) Long id,
            @RequestParam(required = false) String nombre,
            @RequestParam(required = false) Baja_Alta estado,
            @PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.ASC) Pageable page) throws Exception {
        try {
            Page<UnidadMedida> unidadMedidaPage = unidadService.filterUnidadesDeMedida(id, nombre, estado, page);
            return ResponseEntity.status(HttpStatus.OK).body(unidadMedidaPage);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("success", false, "message", e.getMessage()));
        }

    }
}



