package com.example.demo.Controllers;

import com.example.demo.Controllers.DTOS.CategoriaDTO;
import com.example.demo.Entitys.Categoria;
import com.example.demo.Services.CatergoriaService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
    public ResponseEntity<?> getAllCategorias(@PageableDefault(page = 0, size = 10) Pageable page) throws Exception {
        try {

        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("success", false, "message", e.getMessage()));
        }
        CategoriaDTO dto = new CategoriaDTO();
        Page<Categoria> categorias = catergoriaService.getAllCategoria(page);
        Page<CategoriaDTO> categoriasDTO = categorias.map(CategoriaDTO::toDTO);
        return ResponseEntity.status(HttpStatus.OK).body(categoriasDTO);
    }

    @GetMapping("/filter")
    public ResponseEntity<?> findByIDandName(
            //OPTIONAL PARAMS
            @RequestParam(required = false) Long id,
            @RequestParam(required = false) String nombre,
            @PageableDefault(page = 0, size = 10) Pageable page) throws Exception {
        try {
            CategoriaDTO dto = new CategoriaDTO();
            Page<Categoria> categorias = catergoriaService.findParentAndName(id, nombre, page);
            Page<CategoriaDTO> categoriaDTOPage = categorias.map(CategoriaDTO::toDTO);
            return ResponseEntity.status(HttpStatus.OK).body(categoriaDTOPage);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("success", false, "message", e.getMessage()));
        }

    }

    //GET ALL CATEGORIAS WITHOUT pagination
    @GetMapping("/allWOPage")
    public ResponseEntity<List<Categoria>> getAllCategorias() throws Exception {
        CategoriaDTO dto = new CategoriaDTO();
        List<Categoria> categorias = catergoriaService.getAllCategoriaList();
        return ResponseEntity.status(HttpStatus.OK).body(categorias);
    }

    @PostMapping("")
    public ResponseEntity<?> crearNuevaCategoria(@RequestBody CategoriaDTO categoriaDTO) throws Exception {
        try {
            CategoriaDTO dto = new CategoriaDTO();
            Categoria categoria = dto.toEntity(categoriaDTO, catergoriaService.findbyID(categoriaDTO.getCategoriaPadre()));
            catergoriaService.crearCategoria(categoria);
            return ResponseEntity.status(HttpStatus.OK).body(CategoriaDTO.toDTO(categoria));
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("success", false, "message", e.getMessage()));
        }
    }

    @GetMapping(value = "")
    public ResponseEntity<?> getChildren(@RequestParam("id") Long ID, @PageableDefault(page = 0, size = 10) Pageable pageable) throws Exception {
        try {
            Page<Categoria> categoriasChildrens = catergoriaService.findParent(ID, pageable);
            return ResponseEntity.status(HttpStatus.OK).body(categoriasChildrens);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("success", false, "message", e.getMessage()));
        }
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<?> updateCategoria(@PathVariable("id") Long ID, @RequestBody Categoria categoria) throws Exception {
        try {
            Categoria updatedCategoria = catergoriaService.updateCategoria(categoria, ID);
            return ResponseEntity.status(HttpStatus.OK).body(updatedCategoria);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("success", false, "message", e.getMessage()));
        }

    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> deleteCategoria(@PathVariable("id") Long ID) throws Exception {

        try {
            catergoriaService.deleteCategoria(ID);
            return ResponseEntity.status(HttpStatus.OK).body("El objeto fue borrado con exito");
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("success", false, "message", e.getMessage()));
        }
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Categoria> getCategoria(@PathVariable("id") Long ID) throws Exception {
        Categoria categoria = catergoriaService.findbyID(ID);
        return ResponseEntity.status(HttpStatus.OK).body(categoria);
    }
}
