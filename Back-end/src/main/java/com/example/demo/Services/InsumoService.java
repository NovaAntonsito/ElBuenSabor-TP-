package com.example.demo.Services;

import com.example.demo.Entitys.Enum.Baja_Alta;
import com.example.demo.Entitys.Insumo;
import com.example.demo.Repository.InsumoRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class InsumoService implements InsumoServiceInterface {
    private final InsumoRepository insumoRepository;


    @Override
    public Page<Insumo> getAllInsumos(Pageable page) throws Exception {
        return insumoRepository.getAllInsumosInAlta(page);
    }

    @Override
    public Insumo createInsumo(Insumo insumo) throws Exception {
        insumoRepository.save(insumo);
        return insumo;
    }

    @Override
    public void deleteInsumo(Long ID) throws Exception {
        Insumo insumoSoftDeleted = insumoRepository.findByID(ID);
        insumoSoftDeleted.setAlta(Baja_Alta.NO_DISPONIBLE);
    }

    @Override
    public Insumo updateInsumo(Long ID, Insumo insumo) throws Exception {
        return null;
    }

    @Override
    public Insumo findByID(Long ID) throws Exception {
        return insumoRepository.findByID(ID);
    }
}
