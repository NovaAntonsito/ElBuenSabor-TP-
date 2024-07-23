package com.example.demo.Services;

import com.example.demo.Entitys.Enum.Baja_Alta;
import com.example.demo.Entitys.Insumo;
import com.example.demo.Entitys.ProductoInsumos;
import com.example.demo.Repository.InsumoRepository;

import com.example.demo.Repository.ProductosInsumosRepository;
import com.example.demo.Services.Interfaces.InsumoServiceInterface;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class InsumoService implements InsumoServiceInterface {
    private final InsumoRepository insumoRepository;
    private final ProductosInsumosRepository productosInsumosRepository;


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
        insumoFound.setStock_actual(insumo.getStock_actual());
        insumoFound.setStock_minimo(insumo.getStock_minimo());
        insumoFound.setUrlIMG(insumo.getUrlIMG());
        insumoFound.setUnidad_medida(insumo.getUnidad_medida());
        insumoFound.setCategoria(insumo.getCategoria());
        insumoFound.setEs_complemento(insumo.getEs_complemento());
        insumoRepository.save(insumoFound);
        return insumoFound;
    }

    @Override
    public Page<Insumo> filterSupplies(Long id, String nombre, Boolean esComplemento, Baja_Alta estado, Long umId, Pageable page) throws Exception {
        return insumoRepository.filterSupplies(id, nombre, esComplemento, estado, umId, page);
    }

    @Override
    public Insumo findByID(Long ID) throws Exception {
        return insumoRepository.findByID(ID);
    }

    @Override
    public Boolean verificarAsociacion(Long id) throws Exception {
        return productosInsumosRepository.getProductoInsumosAsociados(id) > 0;
    }
}
