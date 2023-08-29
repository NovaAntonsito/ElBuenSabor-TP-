package com.example.demo.Services;

import com.example.demo.Entitys.Categoria;
import com.example.demo.Entitys.Enum.Baja_Alta;
import com.example.demo.Entitys.Producto;

import com.example.demo.Repository.CategoriaRepository;
import com.example.demo.Repository.ProductoRepository;
import com.example.demo.Services.Interfaces.ProductoServiceInterface;
import jakarta.transaction.Transactional;


import lombok.RequiredArgsConstructor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RequiredArgsConstructor
@Slf4j
@Service
@Transactional
public class ProductoService implements ProductoServiceInterface {


    private final ProductoRepository productoRepository;
    private final CategoriaRepository categoriaRepository;

    @Override
    public Page<Producto> getAll(Pageable page) throws Exception {
        return productoRepository.findAllinAlta(page);
    }
    @Override
    public List<Producto> getAllNoPage() throws Exception {
        return productoRepository.findAllinAltaNoPage();
    }
    @Override
    public Producto crearProducto(Producto newProducto, MultipartFile file) throws Exception {
        Categoria cateFound = categoriaRepository.findByID(newProducto.getProductoCategoria().getID());

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
        prodFound.setImgURL(newProducto.getImgURL());
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

    @Override
    public Producto findbyID(Long ID) throws Exception {
        return productoRepository.findByID(ID);
    }

    @Override
    public Page<Producto> findByIDandCategoria(Long ID, String nombre, Pageable page) throws Exception {
        return productoRepository.findByNameAndCategoria(ID,nombre,page);
    }
    @Override
    public List<Producto> searchByNameAndCategoria(Long ID, String nombre) throws Exception {
        return productoRepository.searchByNameAndCategoria(ID,nombre);
    }
}
