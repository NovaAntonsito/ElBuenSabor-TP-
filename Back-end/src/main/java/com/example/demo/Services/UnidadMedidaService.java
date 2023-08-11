package com.example.demo.Services;

import com.example.demo.Entitys.UnidadMedida;
import com.example.demo.Repository.UnidadMedidaRepository;
import com.example.demo.Services.Interfaces.UnidadMedidaServiceInterface;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class UnidadMedidaService implements UnidadMedidaServiceInterface {
    private final UnidadMedidaRepository unidadMedidaRepository;
    @Override
    public void save(UnidadMedida unidadMedida) throws Exception {
        unidadMedidaRepository.save(unidadMedida);
    }


    @Override
    public UnidadMedida findbyID(Long id) throws Exception {
        if(!unidadMedidaRepository.findByID(id).equals(null)){
            return unidadMedidaRepository.findByID(id);
        }else{
            throw new RuntimeException("No existe ese objeto");
        }
    }
    @Override
    public UnidadMedida updateUnidadMedida(Long id, UnidadMedida medidaUpdate) throws Exception {
        UnidadMedida medidaFound = this.findbyID(id);
        if (medidaFound != null) {
            medidaFound.setNombre(medidaUpdate.getNombre());
            medidaFound.setEstado(medidaUpdate.getEstado());
            return unidadMedidaRepository.save(medidaFound);
        } else {
            throw new RuntimeException("No existe ese objeto");
        }
    }


    @Override
    public List<UnidadMedida> findAll() throws Exception {
        return unidadMedidaRepository.findAll();
    }

    @Override
    public Page<UnidadMedida> filterByName(String nombre, Pageable pageable) throws Exception {
        return unidadMedidaRepository.filterByName(nombre, pageable);
    }

    @Override
    public List<UnidadMedida> findAllByEstadoDisponible() throws Exception {
        return unidadMedidaRepository.findAllByEstadoDisponible();
    }
}
