package com.example.demo.Services;

import com.example.demo.Entitys.Categoria;
import com.example.demo.Entitys.Enum.Baja_Alta;
import com.example.demo.Entitys.Producto;

import com.example.demo.Repository.CategoriaRepository;
import com.example.demo.Repository.ProductoRepository;
import com.example.demo.Utils.ImagenUtils;
import jakarta.transaction.Transactional;


import lombok.RequiredArgsConstructor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@Slf4j
@Service
@Transactional
public class ProductoService implements ProductoServiceInterface{


    private final ProductoRepository productoRepository;
    private final CategoriaRepository categoriaRepository;

    @Override
    public Page<Producto> getAll(Pageable page) throws Exception {
        Page <Producto> prodCompress = productoRepository.findAllinAlta(page);
//        List<Producto> prodList = new ArrayList<>();
//        for (Producto list : prodCompress) {
//            list.setImagenBlob(descargarImg(list.getID()));
//            prodList.add(list);
//        }
//        Page<Producto> prodDecompress = new PageImpl<>(prodList,prodCompress.getPageable(), prodCompress.getTotalPages());
        return prodCompress;
    }

    @Override
    public Producto crearProducto(Producto newProducto, MultipartFile file) throws Exception {
        Categoria cateFound = categoriaRepository.findByID(newProducto.getProductoCategoria().getID());
//        BufferedImage bi = ImageIO.read(file.getInputStream());
//        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
//        ImageIO.write(bi, "png", byteArrayOutputStream);
//        byte[] imageData = byteArrayOutputStream.toByteArray();
        newProducto.setImagenBlob(new byte[0]);
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

    @Override
    public Producto findbyID(Long ID) throws Exception {
        return productoRepository.findByID(ID);
    }

    public byte[] descargarImg(Long ID){
        Producto prodFound = productoRepository.findByID(ID);
        byte[] imgActual = ImagenUtils.decompressImage(prodFound.getImagenBlob());
        return imgActual;
    }
}
