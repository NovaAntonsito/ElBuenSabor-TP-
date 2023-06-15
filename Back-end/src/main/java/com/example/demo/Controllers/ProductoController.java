package com.example.demo.Controllers;

import com.example.demo.Controllers.DTOS.ProductoDTO;
import com.example.demo.Entitys.Categoria;
import com.example.demo.Entitys.Insumo;
import com.example.demo.Entitys.Producto;
import com.example.demo.Services.CatergoriaService;
import com.example.demo.Services.CloudinaryServices;
import com.example.demo.Services.InsumoService;
import com.example.demo.Services.ProductoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@RequiredArgsConstructor
@RestController
@RequestMapping("v1/api/producto")
@Slf4j
@CrossOrigin(origins = "*")
public class ProductoController {

    private final ProductoService productoService;

    private final CatergoriaService catergoriaService;

    private final CloudinaryServices cloudServices;

    private final InsumoService insumoService;

    @GetMapping("")
    public ResponseEntity<?> getAllinAlta(@PageableDefault(value = 10, page = 0)Pageable page) throws Exception{
        try {
            Page<Producto> prodsInAlta = productoService.getAll(page);
            return ResponseEntity.status(HttpStatus.OK).body(prodsInAlta);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("success", false, "message", e.getMessage()));
        }

    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getOneProducto(@PathVariable("id") Long id) throws Exception{
        try {
            Producto productoFound = productoService.findbyID(id);
            return ResponseEntity.status(HttpStatus.OK).body(productoFound);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("success", false, "message", e.getMessage()));
        }

    }

    @PostMapping("")
    public ResponseEntity<?> createProducto(@RequestPart("producto") ProductoDTO productoDTO, @RequestPart("imagen") MultipartFile file) throws Exception {

        try {
            List<Insumo> insumoList = new ArrayList<>();
            Categoria cateFound = catergoriaService.findbyID(productoDTO.getProductoCategoria());
            BufferedImage imgActual = ImageIO.read(file.getInputStream());
            var result = cloudServices.UploadIMG(file);
            for(Long id : productoDTO.getInsumosIDS()){
                Insumo insumoadd = insumoService.findByID(id);
                log.info(insumoadd.getNombre());
                insumoList.add(insumoadd);
            }
            var url = (String)result.get("url");
            Producto newProd = productoDTO.toEntity(productoDTO,cateFound,insumoList,url);
            newProd = productoService.crearProducto(newProd,file);
            return ResponseEntity.status(HttpStatus.OK).body(newProd);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("success", false, "message", e.getMessage()));
        }

    }
    @PutMapping("/{id}")
    public ResponseEntity<?> updateProducto(@PathVariable("id") Long ID, @RequestPart("producto") ProductoDTO productoDTO, @RequestPart("file") MultipartFile file) throws Exception {
        try {
            ProductoDTO newProdDTO = new ProductoDTO();
            List<Insumo> insumoSet = new ArrayList<>();
            BufferedImage imgActual = ImageIO.read(file.getInputStream());
            var result = cloudServices.UploadIMG(file);
            String url =(String)result.get("url");
            for (Long id: productoDTO.getInsumosIDS()) {
                insumoSet.add(insumoService.findByID(id));
            }
            Producto updatedProducto = productoService.updateProducto(ID, newProdDTO.toEntity(productoDTO, catergoriaService.findbyID(productoDTO.getProductoCategoria()),insumoSet,url));
            return ResponseEntity.status(HttpStatus.OK).body(updatedProducto);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("success", false, "message", e.getMessage()));
        }

    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProducto(@PathVariable("id")Long ID) throws Exception{
        try {
            productoService.deleteSoftProducto(ID);
            return ResponseEntity.status(HttpStatus.OK).body("El objeto se borro existosamente");
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("success", false, "message", e.getMessage()));
        }

    }
    @GetMapping("/search")
    public ResponseEntity<?> searchProducto
            (@RequestParam(required = false, value = "id") Long id,
             @RequestParam(required = false, value = "nombre") String nombre,
             Pageable page) throws Exception{
        try {
            Page<Producto> productoPage = productoService.findByIDandCategoria(id,nombre,page);
            return ResponseEntity.status(HttpStatus.OK).body(productoPage);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("success", false, "message", e.getMessage()));
        }

    }
}
