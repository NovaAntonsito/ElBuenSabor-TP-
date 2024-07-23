package com.example.demo.Controllers;

import com.example.demo.Controllers.DTOS.CategoriaDTO;
import com.example.demo.Entitys.Categoria;
import com.example.demo.Entitys.Enum.Baja_Alta;
import com.example.demo.Entitys.Enum.TipoCategoria;
import com.example.demo.Services.CatergoriaService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("v1/api/categoria")
@CrossOrigin(origins = "*")
public class CategoriaController {
    private final CatergoriaService catergoriaService;

    @GetMapping("/all")
    public ResponseEntity<List<CategoriaDTO>> getAllCategoriasProductos() throws Exception {
        try {
            List<Categoria> categorias = catergoriaService.getAllCategoriaProductos();
            List<CategoriaDTO> categoriasDTO = (categorias.stream().map(CategoriaDTO::toDTO)).toList();
            return ResponseEntity.status(HttpStatus.OK).body(categoriasDTO);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body((List<CategoriaDTO>) Map.of("success", false, "message", e.getMessage()));
        }
    }

    @GetMapping("/filter")
    public ResponseEntity<?> findByIDandName(
            //OPTIONAL PARAMS
            @RequestParam(required = false) Long id, @RequestParam(required = false) String nombre, @RequestParam(required = false) TipoCategoria tipoCategoria, @RequestParam(required = false) Baja_Alta estado
            , @PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.ASC) Pageable page) throws Exception {
        try {
            Page<Categoria> categorias = catergoriaService.filterCategories(id, nombre, tipoCategoria, estado, page);
            Page<CategoriaDTO> categoriaDTOPage = categorias.map(CategoriaDTO::toDTO);
            return ResponseEntity.status(HttpStatus.OK).body(categoriaDTOPage);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("success", false, "message", e.getMessage()));
        }
    }

    //GET ALL CATEGORIAS WITHOUT pagination
    @GetMapping("/allWOPage")
    public ResponseEntity<List<Categoria>> getAllCategorias(@RequestParam(required = false) Long parentId, @RequestParam(required = false) String parentType) throws Exception {
        List<Categoria> categorias = catergoriaService.getAllCategoriaList(parentId, parentType);
        return ResponseEntity.status(HttpStatus.OK).body(categorias);
    }

    @PostMapping("")
    public ResponseEntity<?> crearNuevaCategoria(@RequestBody CategoriaDTO categoriaDTO) throws Exception {
        //display categoriaDTO in console to check if it is correct, remeber that is an object with atributes
        System.out.println(categoriaDTO.toString());

        try {
            Categoria categoria = categoriaDTO.toEntity(categoriaDTO);
            catergoriaService.crearCategoria(categoria);
            return ResponseEntity.status(HttpStatus.OK).body(CategoriaDTO.toDTO(categoria));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("success", false, "message", e.getMessage()));
        }
    }

    @GetMapping(value = "")
    public ResponseEntity<?> getChildren(@RequestParam("id") Long ID, @PageableDefault(page = 0, size = 10) Pageable pageable) throws Exception {
        try {
            Page<Categoria> categoriasChildrens = catergoriaService.findParent(ID, pageable);
            return ResponseEntity.status(HttpStatus.OK).body(categoriasChildrens);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("success", false, "message", e.getMessage()));
        }
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<?> updateCategoria(@PathVariable("id") Long ID, @RequestBody Categoria categoria) throws Exception {
        System.out.println(categoria.toString());
        try {
            Categoria updatedCategoria = catergoriaService.updateCategoria(categoria, ID);
            return ResponseEntity.status(HttpStatus.OK).body(updatedCategoria);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("success", false, "message", e.getMessage()));
        }
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> deleteCategoria(@PathVariable("id") Long ID) throws Exception {

        try {
            catergoriaService.deleteCategoria(ID);
            return ResponseEntity.status(HttpStatus.OK).body("El objeto fue borrado con exito");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("success", false, "message", e.getMessage()));
        }
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<CategoriaDTO> getCategoria(@PathVariable("id") Long ID) throws Exception {
        Categoria categoria = catergoriaService.findbyID(ID);
        CategoriaDTO categoriaDTO = CategoriaDTO.toDTO(categoria);
        return ResponseEntity.status(HttpStatus.OK).body(categoriaDTO);
    }
}
