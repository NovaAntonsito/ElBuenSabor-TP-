package com.example.demo.Services.Interfaces;

import com.example.demo.Controllers.DTOS.ProductoDTO;
import com.example.demo.Entitys.Enum.Baja_Alta;
import com.example.demo.Entitys.Producto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ProductoServiceInterface {

    Page<Producto> getAll(Pageable pageable) throws Exception;

    List<Producto> getAllNoPage() throws Exception;

    Producto saveProduct(Producto newProducto) throws Exception;

    void deleteSoftProducto(Long ID) throws Exception;

    Producto updateProducto(Long ID, Producto newProducto) throws Exception;

    Page<Producto> getProductosByCategoria(String name) throws Exception;

    Producto findbyID(Long ID) throws Exception;

    Page<Producto> filterProducts(Long ID, String nombre, Baja_Alta estado, Pageable page) throws Exception;

    List<Producto> searchProductsWithFilters(Long ID, String nombre, double precioMin, double precioMax, boolean descuento) throws Exception;

}
