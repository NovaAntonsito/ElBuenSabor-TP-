package com.example.demo.Services;

import com.example.demo.Entitys.Categoria;
import com.example.demo.Entitys.Enum.Baja_Alta;
import com.example.demo.Entitys.Producto;

import com.example.demo.Repository.CategoriaRepository;
import com.example.demo.Repository.ProductoRepository;
import jakarta.transaction.Transactional;
import lombok.Data;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Getter
@Setter
@Slf4j
@Service
@Transactional
public class ProductoService implements ProductoServiceInterface{


    private final ProductoRepository productoRepository;
    private final CategoriaRepository categoriaRepository;

    @Override
    public Page<Producto> getAll(Pageable page) throws Exception {
        return productoRepository.findAllinAlta(page);
    }

    @Override
    public Producto crearProducto(Producto newProducto) throws Exception {
        Categoria cateFound = categoriaRepository.findByID(newProducto.getProductoCategoria().getID());
        log.info(cateFound.getNombre());
        newProducto.setProductoCategoria(cateFound);
        productoRepository.save(newProducto);
        return newProducto;
    }

    @Override
    public void deleteSoftProducto(Long ID) throws Exception {
       Producto prodFound = productoRepository.findByID(ID);
       prodFound.setAlta(Baja_Alta.NO_DISPONIBLE);
       log.info("El objeto fue borrado de forma logica");
    }

    @Override
    public Producto updateProducto(Long ID, Producto newProducto) throws Exception {
        Producto prodFound = productoRepository.findByID(ID);
        prodFound.setNombre(newProducto.getNombre());
        prodFound.setImagenBlob(newProducto.getImagenBlob());
        prodFound.setDescripcion(newProducto.getDescripcion());
        prodFound.setTiempoCocina(newProducto.getTiempoCocina());
        prodFound.setAlta(newProducto.getAlta());
        prodFound.setReceta(newProducto.getReceta());
        productoRepository.save(prodFound);
        return prodFound;
    }

    @Override
    public Page<Producto> getProductosByCategoria(String name) throws Exception {
        return null;
    }
}
