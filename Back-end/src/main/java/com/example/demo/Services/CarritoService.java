package com.example.demo.Services;

import com.example.demo.Entitys.Carrito;
import com.example.demo.Repository.CarritoRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class CarritoService implements CarritoServiceInterface{

    private final CarritoRepository carritoRepository;
    @Override
    public Carrito cartSave(Carrito cart) throws Exception {
        return carritoRepository.save(cart);
    }
}
