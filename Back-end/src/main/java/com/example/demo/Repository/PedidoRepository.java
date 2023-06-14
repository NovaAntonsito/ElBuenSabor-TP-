package com.example.demo.Repository;

import com.example.demo.Entitys.Pedido;
import org.springframework.stereotype.Repository;

@Repository
public interface PedidoRepository extends BaseRepository<Pedido,Long> {
}
