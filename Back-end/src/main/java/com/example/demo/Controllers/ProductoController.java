package com.example.demo.Controllers;

import com.example.demo.Entitys.Producto;
import com.example.demo.Services.ProductoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RequiredArgsConstructor
@RestController
@RequestMapping("v1/api/producto")
@Slf4j
public class ProductoController {
    private final ProductoService productoService;

    @GetMapping("")
    public ResponseEntity<Page<Producto>> getAllinAlta(@PageableDefault(value = 10, page = 0)Pageable page) throws Exception{
        Page<Producto> prodsInAlta = productoService.getAll(page);
        return ResponseEntity.status(HttpStatus.OK).body(prodsInAlta);
    }

    @PostMapping("")
    public ResponseEntity<Producto> createProducto(@RequestBody Producto producto) throws Exception{
        Producto newProducto = productoService.crearProducto(producto);
        log.info("Recibi un producto");
        System.out.println(newProducto.getReceta());
        return ResponseEntity.status(HttpStatus.OK).body(newProducto);
    }

}
