package com.example.demo.Repository;

import com.example.demo.Entitys.Pedido;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PedidoRepository extends BaseRepository<Pedido,Long> {


    @Query(value = "SELECT COUNT(*) FROM pedido p WHERE p.fecha_inicio >= (CURRENT_DATE - INTERVAL 3 MONTH) GROUP BY MONTH(p.fecha_inicio)", nativeQuery = true)
    List<Integer> obtenerPedidosPorMes ();
}
