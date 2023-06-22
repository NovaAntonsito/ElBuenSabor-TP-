package com.example.demo.Services;

import com.example.demo.Entitys.Pedido;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PedidoServiceInterface {
    Pedido savePedido (Pedido pedido) throws Exception;
    Page<Pedido> getAllPedidos (Pageable page) throws Exception;

}
