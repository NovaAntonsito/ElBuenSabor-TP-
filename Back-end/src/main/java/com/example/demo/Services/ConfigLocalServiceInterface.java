package com.example.demo.Services;

import com.example.demo.Entitys.Configuracion;

public interface ConfigLocalServiceInterface {
    void SaveConfig (Configuracion config) throws Exception;

    Double getPrecioPorTiempo (Double tiempoEnCocina) throws Exception;
}
