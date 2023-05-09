package com.example.demo.Services;

import com.example.demo.Entitys.Categoria;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CatergoriaServiceInterface {

    Page<Categoria> getAllCategoria (Pageable page) throws Exception;

    Categoria crearCategoria (Categoria categoria) throws Exception;

    void deleteCategoria (Long ID) throws Exception;

    Categoria updateCategoria (Categoria categoria, Long ID) throws Exception;

    Page<Categoria> findParent (Long id, Pageable page) throws Exception;


}
