package com.example.demo.Services;

import com.example.demo.Entitys.Insumo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface InsumoServiceInterface {
    Page<Insumo> getAllInsumos (Pageable page) throws Exception;
    Insumo createInsumo(Insumo insumo) throws Exception;
    void deleteInsumo(Long ID) throws Exception;
    Insumo updateInsumo (Long ID, Insumo insumo) throws Exception;

    Insumo findByID (Long ID) throws Exception;
}
