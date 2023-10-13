package com.example.demo.Services.Interfaces;

import com.example.demo.Entitys.Pedido;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface PedidoServiceInterface {
    Pedido savePedido (Pedido pedido) throws Exception;
    Page<Pedido> getAllPedidos (Pageable page) throws Exception;
    Map<String, Integer> cantidadPedidosporMes() throws Exception;
    Integer cuentaPedidos(Date fecha1, Date fecha2) throws Exception;
    Pedido getPedido (Long id) throws Exception;
    Pedido getPedidoByUsuario(String id) throws Exception;
    List<Pedido> getPedidosUsuario(String id) throws Exception;
}