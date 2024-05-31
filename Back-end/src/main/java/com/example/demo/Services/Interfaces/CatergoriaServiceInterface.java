package com.example.demo.Services.Interfaces;

import com.example.demo.Entitys.Categoria;
import com.example.demo.Entitys.Enum.Baja_Alta;
import com.example.demo.Entitys.Enum.TipoCategoria;
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

    Page<Categoria> filterCategories(Long id, String nombre, TipoCategoria tipoCategoria, Baja_Alta estado, Pageable page) throws Exception;

    List<Categoria> getAllCategoriaList(Long parentId, String parentType) throws Exception;
}
