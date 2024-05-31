package com.example.demo.Services;

import com.example.demo.Controllers.DTOS.ProductoDTO;
import com.example.demo.Entitys.Categoria;
import com.example.demo.Entitys.Enum.Baja_Alta;
import com.example.demo.Entitys.Insumo;
import com.example.demo.Entitys.Producto;

import com.example.demo.Entitys.ProductoInsumos;
import com.example.demo.Repository.CategoriaRepository;
import com.example.demo.Repository.ProductoRepository;
import com.example.demo.Services.Interfaces.ProductoServiceInterface;
import jakarta.transaction.Transactional;


import lombok.RequiredArgsConstructor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Slf4j
@Service
@Transactional
public class ProductoService implements ProductoServiceInterface {


    private final ProductoRepository productoRepository;
    private final CategoriaRepository categoriaRepository;
    private final InsumoService insumoService;
    private final CatergoriaService catergoriaService;
    private final CloudinaryServices cloudServices;
    private final ProductoInsumoService productoInsumosService;

    @Override
    public Page<Producto> getAll(Pageable page) throws Exception {
        return productoRepository.findAllinAlta(page);
    }
    @Override
    public List<Producto> getAllNoPage() throws Exception {
        return productoRepository.findAllinAltaNoPage();
    }
    @Override
    public Producto saveProduct(Producto newProducto) throws Exception {
        productoRepository.save(newProducto);
        return newProducto;
    }

    public Producto createProduct(ProductoDTO productoDTO,MultipartFile file) throws Exception {
        List<ProductoInsumos> insumoList = new ArrayList<>();
        Categoria cateFound = catergoriaService.findbyID(productoDTO.getProductoCategoria());
        String url = null;
        Double precio = 0D;
        if (file != null) {
            BufferedImage imgActual = ImageIO.read(file.getInputStream());
            var result = cloudServices.UploadIMG(file);
            url = (String) result.get("url");
        }

        for(var i: productoDTO.getInsumos()){
            var insumoFound = insumoService.findByID(i.getInsumo());
            if (insumoFound == null) throw new Exception("Insumo not found");
            var productoInsumo =  productoInsumosService.save(new ProductoInsumos(insumoFound,i.getCantidad()));
            insumoList.add(productoInsumo);
            precio += insumoFound.getCosto() * 1.2;
        }

        // Convertir el porcentaje de descuento a un valor decimal
        double descuentoDecimal = productoDTO.getDescuento() / 100.0;

        // Calcular el precio con descuento
        double precioConDescuento = precio - (precio * descuentoDecimal);
        productoDTO.setPrecio(precioConDescuento);
        Producto newProd = productoDTO.toEntity(productoDTO,cateFound,insumoList,url);
        return newProd;
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
    public List<Producto> searchProductsWithFilters(Long ID, String nombre, double precioMin, double precioMax, boolean descuento) throws Exception {
        return productoRepository.searchByNameAndCategoria(ID,nombre,precioMin,precioMax,descuento);
    }
}
