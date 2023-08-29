package com.example.demo.Repository;

import com.example.demo.Entitys.Configuracion;

public interface ConfigRepository extends BaseRepository<Configuracion, Long>{

    long count();
    Configuracion findByID (Long id);
}
