package com.example.demo.Services.Interfaces;

import com.example.demo.Entitys.Categoria;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CatergoriaServiceInterface {

    List<Categoria> getAllCategoriaProductos () throws Exception;

    Categoria crearCategoria (Categoria categoria) throws Exception;

    void deleteCategoria (Long ID) throws Exception;

    Categoria updateCategoria (Categoria categoria, Long ID) throws Exception;

    Page<Categoria> findParent (Long id, Pageable page) throws Exception;

    Categoria findbyID (Long id);

    Page<Categoria> findParentAndName (Long id, String nombre, Pageable pageable) throws Exception;

    List<Categoria> getAllCategoriaList () throws Exception;
}
