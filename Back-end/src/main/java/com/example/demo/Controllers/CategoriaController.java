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
public class CategoriaController {
    private final CatergoriaService catergoriaService;

    @GetMapping("/all")
    public ResponseEntity<Page<CategoriaDTO>> getAllCategorias(@PageableDefault(page = 0, size = 10) Pageable page) throws Exception {
        Page<Categoria> categorias = catergoriaService.getAllCategoria(page);
        Page<CategoriaDTO> categoriasDTO = categorias.map(this::toDTO);
        return ResponseEntity.status(HttpStatus.OK).body(categoriasDTO);
    }
    @GetMapping("/filter")
    public ResponseEntity<Page<CategoriaDTO>> findByIDandName(@RequestParam("id")Long id, @RequestParam("name") String nombre, @PageableDefault(page = 0, size = 10) Pageable page) throws Exception{
        Page<Categoria> categorias = catergoriaService.findParentAndName(id, nombre, page);
        Page<CategoriaDTO> categoriaDTOPage = categorias.map(this::toDTO);
        return ResponseEntity.status(HttpStatus.OK).body(categoriaDTOPage);
    }

    @PostMapping("")
    public ResponseEntity<CategoriaDTO> crearNuevaCategoria(@RequestBody CategoriaDTO categoriaDTO) throws Exception {
        Categoria categoria = toEntity(categoriaDTO);
        catergoriaService.crearCategoria(categoria);
        return ResponseEntity.status(HttpStatus.OK).body(toDTO(categoria));
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
    private CategoriaDTO toDTO(Categoria categoria) {
        CategoriaDTO dto = new CategoriaDTO();
        dto.setNombre(categoria.getNombre());
        dto.setEstado(categoria.getAlta());
        if (categoria.getCategoriaPadre() != null) {
            dto.setCategoriaPadre(categoria.getCategoriaPadre().getID());
        }
        return dto;
    }
    private Categoria toEntity(CategoriaDTO dto) {
        Categoria categoria = new Categoria();
        categoria.setNombre(dto.getNombre());
        categoria.setAlta(dto.getEstado());
        if (dto.getCategoriaPadre() != null) {
            Categoria categoriaPadre = catergoriaService.findbyID(dto.getCategoriaPadre());
            categoria.setCategoriaPadre(categoriaPadre);
        }
        return categoria;
    }
}
