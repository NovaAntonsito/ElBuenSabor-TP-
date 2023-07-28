package com.example.demo.Services;

import com.example.demo.Entitys.ProductoInsumos;
import com.example.demo.Repository.ProductosInsumosRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class ProductoInsumoService implements ProductoInsumoInterface {
    private final ProductosInsumosRepository insumoRepository;
    @Override
    public void save(ProductoInsumos insumo) throws Exception {
        insumoRepository.save(insumo);
    }
}
