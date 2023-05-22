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

@RestController
@RequiredArgsConstructor
@RequestMapping("v1/api/categoria")
@CrossOrigin(origins = "*")
public class CategoriaController {
    private final CatergoriaService catergoriaService;


    @GetMapping("/all")
    public ResponseEntity<Page<CategoriaDTO>> getAllCategorias(@PageableDefault(page = 0, size = 10) Pageable page) throws Exception {
        CategoriaDTO dto = new CategoriaDTO();
        Page<Categoria> categorias = catergoriaService.getAllCategoria(page);
        Page<CategoriaDTO> categoriasDTO = categorias.map(CategoriaDTO::toDTO);
        return ResponseEntity.status(HttpStatus.OK).body(categoriasDTO);
    }

    @GetMapping("/filter")
    public ResponseEntity<Page<CategoriaDTO>> findByIDandName(
            //OPTIONAL PARAMS
            @RequestParam(required = false) Long id,
            @RequestParam(required = false) String nombre,
            @PageableDefault(page = 0, size = 10) Pageable page) throws Exception {
        CategoriaDTO dto = new CategoriaDTO();
        Page<Categoria> categorias = catergoriaService.findParentAndName(id, nombre, page);
        Page<CategoriaDTO> categoriaDTOPage = categorias.map(CategoriaDTO::toDTO);
        return ResponseEntity.status(HttpStatus.OK).body(categoriaDTOPage);
    }

    @PostMapping("")
    public ResponseEntity<CategoriaDTO> crearNuevaCategoria(@RequestBody CategoriaDTO categoriaDTO) throws Exception {
        CategoriaDTO dto = new CategoriaDTO();
        Categoria categoria = dto.toEntity(categoriaDTO, catergoriaService.findbyID(categoriaDTO.getCategoriaPadre().getId()));
        catergoriaService.crearCategoria(categoria);
        return ResponseEntity.status(HttpStatus.OK).body(CategoriaDTO.toDTO(categoria));
    }

    @GetMapping(value = "")
    public ResponseEntity<Page<Categoria>> getChildren(@RequestParam("id") Long ID, @PageableDefault(page = 0, size = 10) Pageable pageable) throws Exception {
        Page<Categoria> categoriasChildrens = catergoriaService.findParent(ID, pageable);
        return ResponseEntity.status(HttpStatus.OK).body(categoriasChildrens);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<Categoria> updateCategoria(@PathVariable("id") Long ID, @RequestBody Categoria categoria) throws Exception {
        Categoria updatedCategoria = catergoriaService.updateCategoria(categoria, ID);
        return ResponseEntity.status(HttpStatus.OK).body(updatedCategoria);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> deleteCategoria(@PathVariable("id") Long ID) throws Exception {
        catergoriaService.deleteCategoria(ID);
        return ResponseEntity.status(HttpStatus.OK).body("El objeto fue borrado con exito");
    }


}
