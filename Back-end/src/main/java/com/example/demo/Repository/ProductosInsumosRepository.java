package com.example.demo.Repository;

import com.example.demo.Entitys.ProductoInsumos;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductosInsumosRepository extends BaseRepository<ProductoInsumos, Long>{

    @Query(value = "SELECT p.*\n" +
            "FROM Producto p\n" +
            "JOIN ProductoInsumos pi ON p.id_producto = pi.producto_id\n" +
            "JOIN Insumo i ON pi.insumo_id = i.id_insumo\n" +
            "WHERE i.id_insumo = :id", nativeQuery = true)
    List<ProductoInsumos> getProductoInsumosAsociados (@Param("id") Long id);
}
