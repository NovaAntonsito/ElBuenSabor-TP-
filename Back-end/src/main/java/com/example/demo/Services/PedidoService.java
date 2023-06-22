package com.example.demo.Services;

import com.example.demo.Entitys.Pedido;
import com.example.demo.Repository.CarritoRepository;
import com.example.demo.Repository.PedidoRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class PedidoService implements PedidoServiceInterface{

    private final PedidoRepository pedidoRepository;

    @Override
    public Pedido savePedido(Pedido pedido) throws Exception {
        return pedidoRepository.save(pedido);
    }

    @Override
    public Page<Pedido> getAllPedidos(Pageable page) throws Exception {
        return pedidoRepository.findAll(page);
    }


}
