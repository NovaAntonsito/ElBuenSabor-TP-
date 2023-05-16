package com.example.demo.Services;

import com.example.demo.Entitys.Producto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

public interface ProductoServiceInterface {

    Page<Producto> getAll (Pageable pageable) throws Exception;

    Producto crearProducto (Producto newProducto, MultipartFile file) throws Exception;

    void deleteSoftProducto (Long ID) throws Exception;

    Producto updateProducto (Long ID, Producto newProducto) throws Exception;

    Page<Producto> getProductosByCategoria(String name) throws Exception;

    Producto findbyID (Long ID) throws Exception;

    Page<Producto> findByIDandCategoria (Long ID, String nombre, Pageable page) throws Exception;

}
