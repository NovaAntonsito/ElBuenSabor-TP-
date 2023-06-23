package com.example.demo.Services;

import com.example.demo.Entitys.Categoria;
import com.example.demo.Repository.CategoriaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class CatergoriaService implements CatergoriaServiceInterface {

    private final CategoriaRepository categoriaRepository;


    @Override
    public Page<Categoria> getAllCategoria(Pageable page) throws Exception {
        log.info("Entre a Get All");
        return categoriaRepository.findAll(page);
    }


    @Override
    public Categoria crearCategoria(Categoria categoria) throws Exception {
        return categoriaRepository.save(categoria);
    }

    @Override
    public void deleteCategoria( Long ID) throws Exception {
        Categoria cateFound = categoriaRepository.findByID(ID);
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
    public Page<Categoria> findParentAndName(Long id, String nombre, Pageable pageable) throws Exception {
        return categoriaRepository.findParentAndName(id, nombre, pageable);
    }

    @Override
    public List<Categoria> getAllCategoriaList() throws Exception {
        return categoriaRepository.findAll();
    }

}
