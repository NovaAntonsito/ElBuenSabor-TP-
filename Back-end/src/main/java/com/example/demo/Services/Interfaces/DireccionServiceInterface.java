package com.example.demo.Services.Interfaces;

import com.example.demo.Entitys.Direccion;

public interface DireccionServiceInterface {
    public Direccion saveDireccion (Direccion address) throws Exception;
    public Direccion getOneDireccion (long address_id) throws Exception;
    public void delDireccion (Direccion address) throws Exception;
}
