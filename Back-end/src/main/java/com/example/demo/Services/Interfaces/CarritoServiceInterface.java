package com.example.demo.Services.Interfaces;

import com.example.demo.Controllers.DTOS.CarritoDTO;
import com.example.demo.Entitys.Carrito;
import com.example.demo.Services.ProductoService;

public interface CarritoServiceInterface {

    Carrito cartSave (Carrito cart) throws Exception;

    Carrito getCarritobyUserID (String id) throws Exception;

    public CarritoDTO generarCarrito (Carrito carrito);
    public CarritoDTO editarCarrito (CarritoDTO carritoDTO, Long newProduct );
    void deleteCarritoPostCompra (Carrito cart) throws Exception;
}
