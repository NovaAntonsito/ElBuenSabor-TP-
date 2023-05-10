package com.example.demo.Services;

import com.example.demo.Entitys.Producto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductoServiceInterface {

    Page<Producto> getAll (Pageable pageable) throws Exception;

    Producto crearProducto (Producto newProducto) throws Exception;

    void deleteSoftProducto (Long ID) throws Exception;

    Producto updateProducto (Long ID, Producto newProducto) throws Exception;

    Page<Producto> getProductosByCategoria(String name) throws Exception;
}
