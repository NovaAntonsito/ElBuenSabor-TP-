package com.example.demo.Repository;

import com.example.demo.Entitys.Carrito;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CarritoRepository extends BaseRepository<Carrito, Long> {
    @Query(nativeQuery = true, value = "Select * from cart where (:id is null or usuario_asignado_id like :id)")
    Carrito getCarritoByUserID (@Param("id")String id);
}
