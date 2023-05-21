package com.example.demo.Repository;

import com.example.demo.Entitys.Insumo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface InsumoRepository extends BaseRepository<Insumo,Long>{
    Insumo findByID (Long ID);

    @Query(value ="Select * from insumo where estado = 0" ,nativeQuery = true)
    Page<Insumo> getAllInsumosInAlta (Pageable page);

    //TODO Crear query para filtrar por nombre y categoria
    @Query(value = "Select * from insumo " +
            "join producto_insumo pi on insumo.id = pi.insumo_set_id " +
            "where (nombre is null or nombre like concat('%', :nombre, '%'))" +
            "and (pi.producto_set_id is null or pi.producto_set_id = :id) ", nativeQuery = true)
    Page<Insumo> getInsumoByName(@Param("nombre")String name,@Param("id") Long idProductoCategoria, Pageable page);

}
