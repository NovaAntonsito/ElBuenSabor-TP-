package com.example.demo.Repository;

import com.example.demo.Entitys.Producto;
import jakarta.persistence.TypedQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductoRepository extends BaseRepository<Producto,Long>{
    Producto findByID (Long ID);

    @Query(value = "select * from producto_manufacturado where alta = 0", nativeQuery = true)
    Page<Producto> findAllinAlta(Pageable page);

   @Query(value = "select * from producto_manufacturado p where " +
           "(:nombre is null or p.nombre like concat('%', :nombre, '%'))" +
           " and (p.producto_categoria_id is null or producto_categoria_id = :id)",nativeQuery = true)
    Page<Producto> findByNameAndCategoria(@Param("id")Long ID,@Param("nombre") String Nombre, Pageable page);
}
