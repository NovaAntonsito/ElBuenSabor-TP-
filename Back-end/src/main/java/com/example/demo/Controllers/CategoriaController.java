package com.example.demo.Controllers;

import com.example.demo.Entitys.Categoria;
import com.example.demo.Services.CatergoriaService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("v1/api/categoria")
public class CategoriaController {
    private final CatergoriaService catergoriaService;

    @GetMapping("/all")
    public ResponseEntity<Page<Categoria>> getAllCategorias(@PageableDefault(page = 0, size = 10)Pageable page) throws Exception {
        Page<Categoria> categorias = catergoriaService.getAllCategoria(page);
        return ResponseEntity.status(HttpStatus.OK).body(categorias);

    }
    @PostMapping("")
    public ResponseEntity<Categoria> crearNuevaCategoria(@RequestBody Categoria categoria) throws Exception{
        catergoriaService.crearCategoria(categoria);
        return ResponseEntity.status(HttpStatus.OK).body(categoria);
    }

    @GetMapping(value = "")
    public ResponseEntity<Page<Categoria>> getChildren(@RequestParam("id") Long ID, @PageableDefault(page = 0,size = 10)Pageable pageable) throws Exception{
        Page<Categoria> categoriasChildrens = catergoriaService.findParent(ID,pageable);
        return ResponseEntity.status(HttpStatus.OK).body(categoriasChildrens);
    }
    @PutMapping(value = "")
    public ResponseEntity<Categoria> updateCategoria(@RequestParam("id") Long ID, @RequestBody Categoria categoria) throws Exception {
        Categoria updatedCategoria = catergoriaService.updateCategoria(categoria,ID);
        return ResponseEntity.status(HttpStatus.OK).body(updatedCategoria);
    }
    @DeleteMapping(value = "")
    public ResponseEntity<?> deleteCategoria(@RequestParam("id") Long ID) throws Exception{
        catergoriaService.deleteCategoria(ID);
        return ResponseEntity.status(HttpStatus.OK).body("El objeto fue borrado con exito");
    }
}
