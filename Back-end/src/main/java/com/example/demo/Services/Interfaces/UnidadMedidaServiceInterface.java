package com.example.demo.Services.Interfaces;

import com.example.demo.Entitys.UnidadMedida;

import java.util.List;

public interface UnidadMedidaServiceInterface {
    void save(UnidadMedida unidadMedida) throws Exception;


    UnidadMedida findbyID(Long id) throws Exception;


    UnidadMedida updateUnidadMedida(Long id, UnidadMedida medidaUpdate) throws Exception;

    List<UnidadMedida> findAll () throws Exception;
}
