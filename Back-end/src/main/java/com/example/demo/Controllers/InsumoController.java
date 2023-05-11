package com.example.demo.Controllers;

import com.example.demo.Controllers.DTOS.InsumosDTO;
import com.example.demo.Entitys.Insumo;

import com.example.demo.Services.InsumoService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
@RequestMapping("v1/api/insumo")
@Slf4j
public class InsumoController {
    private final InsumoService insumoService;
    @PostMapping("")
    public ResponseEntity<Insumo> crearInsumo (@RequestBody InsumosDTO insumosDTO) throws Exception{
        log.info(insumosDTO.getNombre()+insumosDTO.getImagen()+ " <-------------- DTO Incoming");
        Insumo newInsumo = insumosDTO.toEntity(insumosDTO);
        log.info(newInsumo.getNombre()+newInsumo.getImagen()+" <------------- Insumo Incoming");
        insumoService.createInsumo(newInsumo);
        return ResponseEntity.status(HttpStatus.OK).body(newInsumo);
    }
    @GetMapping()
    public ResponseEntity<Page<Insumo>> viewAllinsumosInAlta(@PageableDefault(page = 0, size = 10 )Pageable page) throws Exception{
        Page<Insumo> allInsumos = insumoService.getAllInsumos(page);
        return ResponseEntity.status(HttpStatus.OK).body(allInsumos);
    }
    @PutMapping("/{id}")
    public ResponseEntity<Insumo> updateInsumo (@RequestBody InsumosDTO insumosDTO, @PathVariable("id") Long ID) throws Exception{
        Insumo insumo = insumosDTO.toEntity(insumosDTO);
        insumoService.updateInsumo(ID, insumo);
        return ResponseEntity.status(HttpStatus.OK).body(insumo);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteInsumo (@PathVariable("id") Long ID) throws Exception{
        insumoService.deleteInsumo(ID);
        return ResponseEntity.status(HttpStatus.OK).body("Se borro el elemento correctamente");
    }
}
