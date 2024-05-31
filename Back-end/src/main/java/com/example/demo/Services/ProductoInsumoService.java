package com.example.demo.Services;

import com.example.demo.Entitys.Insumo;
import com.example.demo.Entitys.ProductoInsumos;
import com.example.demo.Repository.ProductosInsumosRepository;
import com.example.demo.Services.Interfaces.ProductoInsumoInterface;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class ProductoInsumoService implements ProductoInsumoInterface {
    private final ProductosInsumosRepository insumoRepository;
    @Override
    public ProductoInsumos save(ProductoInsumos insumo) throws Exception {
        return insumoRepository.save(insumo);
    }
    @Override
    public Optional<ProductoInsumos>  findById(Long id) throws Exception {
        return insumoRepository.findById(id);
    }
}
