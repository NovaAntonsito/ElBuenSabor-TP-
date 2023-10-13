package com.example.demo.Services;

import com.example.demo.Controllers.DTOS.CarritoDTO;
import com.example.demo.Controllers.DTOS.InsumoCarritoDTO;
import com.example.demo.Controllers.DTOS.ProductosCarritoDTO;
import com.example.demo.Entitys.Carrito;
import com.example.demo.Entitys.Producto;
import com.example.demo.Repository.CarritoRepository;
import com.example.demo.Services.Interfaces.CarritoServiceInterface;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class CarritoService implements CarritoServiceInterface {

    private final CarritoRepository carritoRepository;
    private final ProductoService productoService;
    @Override
    public Carrito cartSave(Carrito cart) throws Exception {
        //System.out.println("cartSave: "+cart.getProductosComprados().size());
        return carritoRepository.save(cart);
    }

    @Override
    public Carrito getCarritobyUserID(String id) throws Exception {
        return carritoRepository.getCarritoByUserID(id);
    }

    @Override
    public void deleteCarritoPostCompra(Carrito cart) throws Exception {
        carritoRepository.delete(cart);
    }


    public CarritoDTO generarCarrito (Carrito carrito){

        try {
            ProductosCarritoDTO newDTO = new ProductosCarritoDTO();
            InsumoCarritoDTO complementosDTO = new InsumoCarritoDTO();
            CarritoDTO carritoDTO = new CarritoDTO();

            List<Producto> productosCart = carrito.getProductosComprados();
            List<ProductosCarritoDTO> dtoList = newDTO.toDTO(productosCart);
            List<InsumoCarritoDTO> complementosList = complementosDTO.toDTO(carrito.getProductosAdicionales());
            Double precioTotal = 0D;
            for (ProductosCarritoDTO productosCarritoDTO : dtoList) {
                precioTotal +=  productosCarritoDTO.getPrecioTotal();

            }
            for(InsumoCarritoDTO insumoComplemento : complementosList){
                precioTotal += insumoComplemento.getPrecioTotal();
            }
            carritoDTO.setInsumosAgregados(complementosList);
            carritoDTO.setProductosManufacturados(dtoList);
            carritoDTO.setTotalCompra(precioTotal);


            return carritoDTO;
        }catch (Exception e) {
            return null;
        }
    }

    public CarritoDTO editarCarrito (CarritoDTO carritoDTO,Long newProductID){
        ProductosCarritoDTO productosCarritoDTO = new ProductosCarritoDTO();
        boolean existeProducto = false;
        if (carritoDTO != null) return null;
        for(ProductosCarritoDTO dto : carritoDTO.getProductosManufacturados()){
            if (dto.getProductoId().equals(newProductID)) {
                // El producto ya existe en la lista, aumentar la cantidad y actualizar el precio total
                dto.setCantidad(dto.getCantidad() + 1);
                dto.setPrecioTotal(dto.getPrecioTotal() + dto.getPrecioUnitario());
                carritoDTO.setTotalCompra(carritoDTO.getTotalCompra() + dto.getPrecioUnitario());
                existeProducto = true;
                break;
            }
        }
        if (!existeProducto) {
            try {
                Producto producto = productoService.findbyID(newProductID);
                // Agregar el producto a la lista si no existe
                ProductosCarritoDTO nuevoProducto = new ProductosCarritoDTO();
                nuevoProducto.setNombre(producto.getNombre());
                nuevoProducto.setCantidad(1L);
                nuevoProducto.setDescuento(producto.getDescuento());
                nuevoProducto.setPrecioUnitario(producto.getPrecioUnitario());
                nuevoProducto.setTiempoCocina(producto.getTiempoCocina());
                nuevoProducto.setPrecioTotal(producto.getPrecioUnitario());
                nuevoProducto.setUrlIMG(producto.getImgURL());
                nuevoProducto.setProductoId(producto.getID());
                carritoDTO.getProductosManufacturados().add(nuevoProducto);
                carritoDTO.setTotalCompra(carritoDTO.getTotalCompra() + nuevoProducto.getPrecioTotal());
            }catch (Exception e){

            }

        }


//        for (ProductosCarritoDTO dto : productosCarritoDTOList) {
//            if (dto.getProductoId().equals(producto.getID())) {
//                // El producto ya existe en la lista, aumentar la cantidad y actualizar el precio total
//                dto.setCantidad(dto.getCantidad() + 1);
//
//                existeProducto = true;
//                break;
//            }
//        }
//

        return carritoDTO;
    }
}
