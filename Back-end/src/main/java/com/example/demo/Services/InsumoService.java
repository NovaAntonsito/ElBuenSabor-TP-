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

import java.util.List;

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
    public Iterable<Insumo> getAllInsumosWOPage() throws Exception {
        return insumoRepository.findAll();
    }

    @Override
    public List<Insumo> getAllInsumosByIndividual() throws Exception {
        return insumoRepository.findByIndividual();
    }

    @Override
    public Insumo createInsumo(Insumo insumo) throws Exception {
        insumoRepository.save(insumo);
        return insumo;
    }

    @Override
    public void deleteInsumo(Long ID) throws Exception {
        Insumo insumoSoftDeleted = insumoRepository.findByID(ID);
        insumoSoftDeleted.setEstado(Baja_Alta.NO_DISPONIBLE);
    }

    @Override
    public Insumo updateInsumo(Long ID, Insumo insumo) throws Exception {
        Insumo insumoFound = insumoRepository.findByID(ID);
        insumoFound.setNombre(insumo.getNombre());
        insumoFound.setCosto(insumo.getCosto());
        insumoFound.setEstado(insumo.getEstado());
        insumoFound.setStockActual(insumo.getStockActual());
        insumoFound.setStockMinimo(insumo.getStockMinimo());
        insumoRepository.save(insumoFound);
        return insumoFound;
    }

    @Override
    public Page<Insumo> getInsumoByName(String name, Pageable page) throws Exception {
        return insumoRepository.getInsumoByName(name, page);
    }

    @Override
    public Insumo findByID(Long ID) throws Exception {
        return insumoRepository.findByID(ID);
    }
}
