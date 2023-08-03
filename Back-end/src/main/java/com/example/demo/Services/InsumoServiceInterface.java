package com.example.demo.Services;

import com.example.demo.Entitys.Insumo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface InsumoServiceInterface {
    Page<Insumo> getAllInsumos (Pageable page) throws Exception;
    List<Insumo> getAllInsumosByIndividual () throws Exception;
    Iterable<Insumo> getAllInsumosWOPage () throws Exception;
    Insumo createInsumo(Insumo insumo) throws Exception;
    void deleteInsumo(Long ID) throws Exception;
    Insumo updateInsumo (Long ID, Insumo insumo) throws Exception;
    Page<Insumo> getInsumoByName (String name, Pageable page) throws Exception;
    Insumo findByID (Long ID) throws Exception;
    Boolean verificarAsociacion (Insumo insumo) throws Exception;
}
