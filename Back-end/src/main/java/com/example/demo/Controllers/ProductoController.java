package com.example.demo.Controllers;

import com.example.demo.Controllers.DTOS.ProductoDTO;
import com.example.demo.Entitys.Categoria;
import com.example.demo.Entitys.Insumo;
import com.example.demo.Entitys.Producto;
import com.example.demo.Services.CatergoriaService;
import com.example.demo.Services.InsumoService;
import com.example.demo.Services.ProductoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.util.ArrayList;
import java.util.List;


@RequiredArgsConstructor
@RestController
@RequestMapping("v1/api/producto")
@Slf4j
@CrossOrigin(origins = "*")
public class ProductoController {
    private final ProductoService productoService;
    private final CatergoriaService catergoriaService;

    private final InsumoService insumoService;

    @GetMapping("")
    public ResponseEntity<Page<Producto>> getAllinAlta(@PageableDefault(value = 10, page = 0)Pageable page) throws Exception{
        Page<Producto> prodsInAlta = productoService.getAll(page);
        return ResponseEntity.status(HttpStatus.OK).body(prodsInAlta);
    }

    @PostMapping("")
    public ResponseEntity<Producto> createProducto(@RequestPart("producto") ProductoDTO productoDTO, @RequestPart("imagen") MultipartFile file) throws Exception{
        List<Insumo> insumoList = new ArrayList<>();
        Categoria cateFound = catergoriaService.findbyID(productoDTO.getProductoCategoria());
        log.info(cateFound.getNombre()+ "<-------------- Hasta aca todo bien");
        for(Long id : productoDTO.getInsumosIDS()){
            Insumo insumoadd = insumoService.findByID(id);
            log.info(insumoadd.getNombre());
            insumoList.add(insumoadd);
        }
        Producto newProd = productoDTO.toEntity(productoDTO,cateFound,insumoList);
        log.info(cateFound.getNombre()+ "<-------------- Hasta aca todo bien x2");
        newProd = productoService.crearProducto(newProd,file);
        return ResponseEntity.status(HttpStatus.OK).body(newProd);
    }
    @PutMapping("/{id}")
    public ResponseEntity<Producto> updateProducto(@PathVariable("id") Long ID, @RequestBody ProductoDTO productoDTO) throws Exception {
        ProductoDTO newProdDTO = new ProductoDTO();
        List<Insumo> insumoSet = new ArrayList<>();
        for (Long id: productoDTO.getInsumosIDS()) {
            insumoSet.add(insumoService.findByID(id));
        }
        Producto updatedProducto = productoService.updateProducto(ID, newProdDTO.toEntity(productoDTO, catergoriaService.findbyID(productoDTO.getProductoCategoria()),insumoSet));
        return ResponseEntity.status(HttpStatus.OK).body(updatedProducto);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProducto(@PathVariable("id")Long ID) throws Exception{
        productoService.deleteSoftProducto(ID);
        return ResponseEntity.status(HttpStatus.OK).body("El objeto se borro existosamente");
    }
}
