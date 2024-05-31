package com.example.demo.Services;

import com.example.demo.Entitys.Categoria;
import com.example.demo.Entitys.Enum.Baja_Alta;
import com.example.demo.Entitys.Enum.TipoCategoria;
import com.example.demo.Repository.CategoriaRepository;
import com.example.demo.Services.Interfaces.CatergoriaServiceInterface;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class CatergoriaService implements CatergoriaServiceInterface {

    private final CategoriaRepository categoriaRepository;


    @Override
    public List<Categoria> getAllCategoriaProductos() throws Exception {
        return categoriaRepository.findCategoriaProductos();
    }


    @Override
    public Categoria crearCategoria(Categoria categoria) throws Exception {
        return categoriaRepository.save(categoria);
    }

    @Override
    public void deleteCategoria(Long ID) throws Exception {
        Categoria cateFound = categoriaRepository.findByID(ID);
        if(cateFound == null){
            throw new Exception("No existe la categoria");
        }
        log.info("Se borro la categoria");
        categoriaRepository.delete(cateFound);
    }

    @Override
    public Categoria updateCategoria(Categoria categoria, Long ID) throws Exception {
        Categoria cateFound = categoriaRepository.findByID(ID);
        if(cateFound == null){
            throw new Exception("No existe la categoria");
        }
        cateFound.setNombre(categoria.getNombre());
        cateFound.setEstado(categoria.getEstado());
        cateFound.setTipo(categoria.getTipo());
        cateFound.setCategoriaPadre(categoria.getCategoriaPadre());
        cateFound.setSubCategoria(categoria.getSubCategoria());
        categoriaRepository.save(cateFound);
        return cateFound;
    }


    @Override
    public Page<Categoria> findParent(Long id, Pageable page) throws Exception {
        return categoriaRepository.findParent(id, page);
    }

    @Override
    public Categoria findbyID(Long id) {
        return categoriaRepository.findByID(id);
    }

    @Override
    public Page<Categoria> filterCategories(Long id, String nombre, TipoCategoria tipoCategoria, Baja_Alta estado, Pageable pageable) throws Exception {
        return categoriaRepository.filterCategories(id, nombre, tipoCategoria, estado, pageable);
    }

    @Override
    @Transactional()
    public List<Categoria> getAllCategoriaList(Long parentId, String parentType) throws Exception {
        return categoriaRepository.findAllExcludingDescendants(parentId, parentType);
    }

}
