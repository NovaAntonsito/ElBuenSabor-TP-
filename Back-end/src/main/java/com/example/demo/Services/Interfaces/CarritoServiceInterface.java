package com.example.demo.Services.Interfaces;

import com.example.demo.Entitys.Carrito;

public interface CarritoServiceInterface {

    Carrito cartSave (Carrito cart) throws Exception;

    Carrito getCarritobyUserID (String id) throws Exception;

    void deleteCarritoPostCompra (Carrito cart) throws Exception;
}
