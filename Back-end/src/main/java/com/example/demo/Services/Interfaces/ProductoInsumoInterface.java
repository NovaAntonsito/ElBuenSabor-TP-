package com.example.demo.Services.Interfaces;

import com.example.demo.Entitys.Insumo;
import com.example.demo.Entitys.ProductoInsumos;

import java.util.Optional;

public interface ProductoInsumoInterface {
    ProductoInsumos save(ProductoInsumos insumo) throws Exception;
    Optional<ProductoInsumos> findById(Long id) throws Exception;
}
