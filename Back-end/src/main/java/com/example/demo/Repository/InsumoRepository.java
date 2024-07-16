package com.example.demo.Repository;

import com.example.demo.Entitys.Categoria;
import com.example.demo.Entitys.Enum.Baja_Alta;
import com.example.demo.Entitys.Enum.TipoCategoria;
import com.example.demo.Entitys.Insumo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InsumoRepository extends BaseRepository<Insumo, Long> {
    Insumo findByID(Long ID);

    @Query(value = "Select * from insumo where estado = 0", nativeQuery = true)
    Page<Insumo> getAllInsumosInAlta(Pageable page);

    //TODO Crear query para filtrar por nombre y categoria
    @Query(value = "SELECT * FROM insumo " +
            "WHERE (:nombre IS NULL OR nombre LIKE CONCAT('%', :nombre, '%'))",
            countQuery = "SELECT COUNT(*) FROM insumo " +
                    "WHERE (:nombre IS NULL OR nombre LIKE CONCAT('%', :nombre, '%'))",
            nativeQuery = true)
    Page<Insumo> getInsumoByName(@Param("nombre") String name, Pageable page);

    @Query(value = "SELECT * FROM insumo WHERE es_complemento = TRUE", nativeQuery = true)
     List<Insumo> findByIndividual ();

    @Query("SELECT i FROM Insumo i " +
            "WHERE (:nombre IS NULL OR i.nombre LIKE CONCAT('%', :nombre, '%')) " +
            "AND (:id IS NULL OR i.ID = :id) " +
            "AND (:esComplemento IS NULL OR i.es_complemento = :esComplemento) " +
            "AND (:estado IS NULL OR i.estado = :estado)")
    Page<Insumo> filterSupplies(@Param("id") Long id,
                                     @Param("nombre") String nombre,
                                     @Param("esComplemento") Boolean esComplemento,
                                     @Param("estado") Baja_Alta estado,
                                     Pageable pageable);

}
