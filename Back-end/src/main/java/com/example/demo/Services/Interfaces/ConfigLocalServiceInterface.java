package com.example.demo.Services.Interfaces;

import com.example.demo.Entitys.Configuracion;

public interface ConfigLocalServiceInterface {
    void SaveConfig (Configuracion config) throws Exception;

    Double getPrecioPorTiempo (Double tiempoEnCocina) throws Exception;

    Double getPrecioPorDelivery() throws Exception;
}
