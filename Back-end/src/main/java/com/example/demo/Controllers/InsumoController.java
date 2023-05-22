package com.example.demo.Controllers;

import com.example.demo.Controllers.DTOS.InsumosDTO;
import com.example.demo.Entitys.Insumo;

import com.example.demo.Services.CloudinaryServices;
import com.example.demo.Services.InsumoService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;


@RestController
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
@RequestMapping("v1/api/insumo")
@Slf4j
public class InsumoController {
    private final CloudinaryServices cloudServices;
    private final InsumoService insumoService;

    @PostMapping(value = "", consumes = {"multipart/form-data"})
    public ResponseEntity<Insumo> crearInsumo(@RequestPart("insumo") InsumosDTO insumosDTO, @RequestPart(value = "img", required = false) MultipartFile file) throws Exception {
        BufferedImage imgActual = ImageIO.read(file.getInputStream());
        var result = cloudServices.UploadIMG(file);
        String url = (String) result.get("url");
        Insumo newInsumo = insumosDTO.toEntity(insumosDTO, url);
        insumoService.createInsumo(newInsumo);
        return ResponseEntity.status(HttpStatus.OK).body(newInsumo);
    }

    @GetMapping()
    public ResponseEntity<Page<Insumo>> viewAllinsumosInAlta(@PageableDefault(page = 0, size = 10) Pageable page) throws Exception {
        Page<Insumo> allInsumos = insumoService.getAllInsumos(page);
        return ResponseEntity.status(HttpStatus.OK).body(allInsumos);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Insumo> updateInsumo(@RequestPart("insumo") InsumosDTO insumosDTO, @RequestPart("img") MultipartFile file, @PathVariable("id") Long ID) throws Exception {
        BufferedImage imgActual = ImageIO.read(file.getInputStream());
        var result = cloudServices.UploadIMG(file);
        String url = (String) result.get("url");
        Insumo insumo = insumosDTO.toEntity(insumosDTO, url);
        insumo = insumoService.updateInsumo(ID, insumo);
        return ResponseEntity.status(HttpStatus.OK).body(insumo);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteInsumo(@PathVariable("id") Long ID) throws Exception {
        insumoService.deleteInsumo(ID);
        return ResponseEntity.status(HttpStatus.OK).body("Se borro el elemento correctamente");
    }

    @GetMapping("/filter")
    public ResponseEntity<Page<Insumo>> getInsumoByName(@RequestParam(value = "nombre", required = false) String name,
                                                        @RequestParam(value = "id", required = false) Long id,
                                                        Pageable page) throws Exception {
        Page<Insumo> insumoPage = insumoService.getInsumoByName(name, id, page);
        return ResponseEntity.status(HttpStatus.OK).body(insumoPage);

    }
}
