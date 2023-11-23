package com.example.demo.Repository;

import com.example.demo.Entitys.Producto;
import jakarta.persistence.TypedQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductoRepository extends BaseRepository<Producto,Long>{
    Producto findByID (Long ID);

    @Query(value = "select * from producto_manufacturado where alta = 0", nativeQuery = true)
    Page<Producto> findAllinAlta(Pageable page);
    @Query(value = "select * from producto_manufacturado where alta = 0", nativeQuery = true)
    List<Producto> findAllinAltaNoPage();

   @Query(value = "select * from producto_manufacturado p where alta = 0 and " +
           "(:nombre is null or p.nombre like concat('%', :nombre, '%'))" +
           " and (p.producto_categoria_id is null or producto_categoria_id = :id)",nativeQuery = true)
    Page<Producto> findByNameAndCategoria(@Param("id")Long ID,@Param("nombre") String Nombre, Pageable page);

    @Query(value = "WITH RECURSIVE subcategorias AS (\n" +
            "    SELECT id\n" +
            "    FROM categoria\n" +
            "    WHERE id = :idCategoria OR :idCategoria IS NULL  -- Aquí traerá todos los registros si :id es NULL\n" +
            "    UNION ALL\n" +
            "    SELECT c.id\n" +
            "    FROM categoria c\n" +
            "    INNER JOIN subcategorias s ON c.categoria_padre = s.id\n" +
            ")\n" +
            "SELECT DISTINCT p.*\n" +
            "FROM producto_manufacturado p\n" +
            "JOIN categoria c ON p.producto_categoria_id = c.id\n" +
            "JOIN subcategorias s ON c.id = s.id OR c.categoria_padre = s.id\n" +
            "WHERE p.alta = 0\n" +
            "AND (:nombre IS NULL OR p.nombre LIKE CONCAT('%', :nombre, '%')) AND (:precioMin IS NULL OR p.precio_unitario >= :precioMin) AND (:precioMax IS NULL OR p.precio_unitario <= :precioMax) AND (:descuento IS TRUE AND p.descuento > 0 OR :descuento IS FALSE);",nativeQuery = true)
    List<Producto> searchByNameAndCategoria(@Param("idCategoria") Long ID,
                                            @Param("nombre") String nombre,
                                            @Param("precioMin") double precioMin,
                                            @Param("precioMax") double precioMax,
                                            @Param("descuento") boolean descuento);

}
