package com.example.demo.Controllers;

import com.example.demo.Controllers.DTOS.InsumosDTO;
import com.example.demo.Entitys.Categoria;
import com.example.demo.Entitys.Enum.Baja_Alta;
import com.example.demo.Entitys.Enum.TipoCategoria;
import com.example.demo.Entitys.Insumo;

import com.example.demo.Repository.InsumoRepository;
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
    private final CloudinaryServices cloudServices;
    private final InsumoRepository insumoRepository;

    @PostMapping(value = "")
    public ResponseEntity<?> crearInsumo(@RequestPart(value = "insumo", required = true) InsumosDTO insumosDTO, @RequestPart(value = "img", required = false) MultipartFile img) throws Exception {
        try {
            Categoria cateFound = catergoriaService.findbyID(insumosDTO.getCategoria().getId());
            if (insumosDTO.getCategoria() != null && cateFound == null)
                throw new RuntimeException("No existe esa categoria");
            Insumo newInsumo = insumosDTO.toEntity(insumosDTO);
            if (img == null && insumosDTO.getEs_complemento()) {
                throw new RuntimeException("Si el insumo es un complemento, es necesario una foto");
            } else if (img != null) {
                BufferedImage imgActual = ImageIO.read(img.getInputStream());
                var result = cloudServices.UploadIMG(img);
                newInsumo.setUrlIMG((String) result.get("url"));
            }

            return ResponseEntity.status(HttpStatus.OK).body(insumoService.createInsumo(newInsumo));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of(
                            "success", false,
                            "message", e.getMessage()
                    ));
        }
    }

    @GetMapping("/getAgregados")
    public ResponseEntity<?> getAllInsumosAgregados() throws Exception {
        try {
            if (insumoService.getAllInsumosByIndividual().size() == 0) {
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body(insumoService.getAllInsumosByIndividual());
            }
            return ResponseEntity.status(HttpStatus.OK).body(insumoService.getAllInsumosByIndividual());
        } catch (Exception e) {
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
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("success", false, "message", e.getMessage()));
        }
    }

    @GetMapping("/allWOPage")
    public ResponseEntity<?> getAllInsumos() throws Exception {
        return ResponseEntity.status(HttpStatus.OK).body(insumoService.getAllInsumosWOPage());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getInsumoById(@PathVariable("id") Long ID) throws Exception {
        var insumoFound = insumoService.findByID(ID);
        if (insumoFound == null) return ResponseEntity.status(HttpStatus.OK).body(null);
        return ResponseEntity.status(HttpStatus.OK).body(insumoFound);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateInsumo(@RequestPart(value = "insumo", required = true) InsumosDTO insumosDTO, @RequestPart(value = "img", required = false) MultipartFile img, @PathVariable("id") Long ID) throws Exception {
        try {
            Insumo newInsumo = insumosDTO.toEntity(insumosDTO);

            if (newInsumo.getEstado().equals(Baja_Alta.NO_DISPONIBLE) && insumoService.verificarAsociacion(ID)) {
                throw new RuntimeException("No se puede dar de baja un insumo que esta asociado a un producto.");
            }

            String insumoImg = insumoRepository.findInsumoByID(ID).getUrlIMG();
            if (insumosDTO.getEs_complemento().equals(false) && insumoImg != null) {
                String publicId = insumoImg.substring(insumoImg.indexOf("imgs/"), insumoImg.lastIndexOf("."));
                var result = cloudServices.delete(publicId);

                System.out.println(result);
            }

            if ((img == null && newInsumo.getUrlIMG() == null) && insumosDTO.getEs_complemento()) {
                throw new RuntimeException("Si el insumo es un complemento, es necesario una foto.");
            } else if (img != null) {
                ImageIO.read(img.getInputStream());
                var result = cloudServices.UploadIMG(img);
                newInsumo.setUrlIMG((String) result.get("url"));
            }

            return ResponseEntity.status(HttpStatus.OK).body(insumoService.updateInsumo(ID, newInsumo));
        } catch (
                Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("success", false, "message", e.getMessage()));
        }

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteInsumo(@PathVariable("id") Long ID) throws Exception {
        try {
            insumoService.deleteInsumo(ID);
            return ResponseEntity.status(HttpStatus.OK).body("Se borro el elemento correctamente");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("success", false, "message", e.getMessage()));
        }
    }

    @GetMapping("/filter")
    public ResponseEntity<?> getInsumoByName(@RequestParam(required = false) Long id, @RequestParam(required = false) String nombre, @RequestParam(value = "es_complemento", required = false) Boolean esComplemento,
                                             @RequestParam(required = false) Baja_Alta estado, @RequestParam(value = "um", required = false) Long umId,
                                             @PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.ASC) Pageable page) throws Exception {
        try {
            Page<Insumo> insumoPage = insumoService.filterSupplies(id, nombre, esComplemento, estado, umId, page);
            return ResponseEntity.status(HttpStatus.OK).body(insumoPage);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("success", false, "message", e.getMessage()));
        }


    }
}
