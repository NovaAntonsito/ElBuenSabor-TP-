package com.example.demo.Repository;

import com.example.demo.Entitys.ProductoInsumos;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductosInsumosRepository extends BaseRepository<ProductoInsumos, Long> {

    @Query(value = "SELECT COUNT(pm.id) " +
            "FROM producto_manufacturado pm " +
            "JOIN producto_manufacturado_insumos pmi ON pm.id = pmi.producto_id " +
            "JOIN producto_insumo pi ON pmi.insumos_id = pi.id " +
            "WHERE pi.insumo_id = :id", nativeQuery = true)
    Integer getProductoInsumosAsociados(@Param("id") Long id);
}
