package com.example.demo.Services;

import com.example.demo.Entitys.Carrito;
import com.example.demo.Entitys.Direccion;
import com.example.demo.Repository.DirreccionRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class DireccionService implements DireccionServiceInterface{

    private final DirreccionRepository dirreccionRepository;

    @Override
    public Direccion saveDireccion(Direccion address) throws Exception {
        return dirreccionRepository.save(address);
    }

    @Override
    public Direccion getOneDireccion(long address_id) throws Exception {
        return dirreccionRepository.getOne(address_id);
    }

    @Override
    public void delDireccion(Direccion address) throws Exception {
        dirreccionRepository.delete(address);
    }

}
