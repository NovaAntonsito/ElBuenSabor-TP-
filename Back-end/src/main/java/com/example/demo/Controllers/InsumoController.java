package com.example.demo.Controllers;

import com.example.demo.Controllers.DTOS.InsumosDTO;
import com.example.demo.Entitys.Categoria;
import com.example.demo.Entitys.Insumo;

import com.example.demo.Services.CatergoriaService;
import com.example.demo.Services.CloudinaryServices;
import com.example.demo.Services.InsumoService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.util.Map;


@RestController
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
@RequestMapping("v1/api/insumo")
@Slf4j
public class InsumoController {

    private final InsumoService insumoService;
    private final CatergoriaService catergoriaService;

    @PostMapping(value = "", consumes = {"application/json"})
    public ResponseEntity<?> crearInsumo(@RequestBody InsumosDTO insumosDTO) throws Exception {
        try {
            Categoria cateFound = catergoriaService.findbyID(insumosDTO.getCategoria().getId());
            if(insumosDTO.getCategoria() != null && cateFound == null) throw new RuntimeException("No existe esa categoria");
            Insumo newInsumo = insumosDTO.toEntity(insumosDTO,cateFound);
            insumoService.createInsumo(newInsumo);
            return ResponseEntity.status(HttpStatus.OK).body(newInsumo);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of(
                            "success", false,
                            "message", e.getMessage()
                    ));
        }
    }

    @GetMapping("/getAgregados")
    public ResponseEntity<?> getAllInsumosAgregados () throws Exception{
        try {
            if(insumoService.getAllInsumosByIndividual().size() == 0){
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body(insumoService.getAllInsumosByIndividual());
            }
            return ResponseEntity.status(HttpStatus.OK).body(insumoService.getAllInsumosByIndividual());
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of(
                            "success", false,
                            "message", e.getMessage()
                    ));
        }
    }

    @GetMapping()
    public ResponseEntity<?> viewAllinsumosInAlta(@PageableDefault(page = 0, size = 10) Pageable page) throws Exception {
        try {
            Page<Insumo> allInsumos = insumoService.getAllInsumos(page);
            return ResponseEntity.status(HttpStatus.OK).body(allInsumos);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("success", false, "message", e.getMessage()));
        }
    }

    @GetMapping("/allWOPage")
    public ResponseEntity<?> getAllInsumos() throws Exception {
        return ResponseEntity.status(HttpStatus.OK).body(insumoService.getAllInsumosWOPage());
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateInsumo(@RequestBody InsumosDTO insumosDTO, @PathVariable("id") Long ID) throws Exception {
        try {
            Categoria cateFound = catergoriaService.findbyID(insumosDTO.getCategoria().getId());
            if(insumosDTO.getCategoria() != null && cateFound == null) throw new RuntimeException("No existe esa categoria");
            Insumo insumo = insumosDTO.toEntity(insumosDTO,cateFound);
            insumo = insumoService.updateInsumo(ID, insumo);
            return ResponseEntity.status(HttpStatus.OK).body(insumo);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("success", false, "message", e.getMessage()));
        }

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteInsumo(@PathVariable("id") Long ID) throws Exception {
        try {
            insumoService.deleteInsumo(ID);
            return ResponseEntity.status(HttpStatus.OK).body("Se borro el elemento correctamente");
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("success", false, "message", e.getMessage()));
        }

    }

    @GetMapping("/filter")
    public ResponseEntity<?> getInsumoByName(@RequestParam(value = "nombre", required = false) String name,
                                             @PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.ASC) Pageable page) throws Exception {
        try {
            Page<Insumo> insumoPage = insumoService.getInsumoByName(name, page);
            return ResponseEntity.status(HttpStatus.OK).body(insumoPage);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("success", false, "message", e.getMessage()));
        }


    }
}
