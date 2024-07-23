package com.example.demo.Repository;

import com.example.demo.Entitys.Categoria;
import com.example.demo.Entitys.Enum.Baja_Alta;
import com.example.demo.Entitys.Enum.TipoCategoria;
import com.example.demo.Entitys.UnidadMedida;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UnidadMedidaRepository extends BaseRepository<UnidadMedida, Long> {

    UnidadMedida findByID(Long id) throws Exception;

    @Query("SELECT uc FROM UnidadMedida uc " +
            "WHERE (:nombre IS NULL OR uc.nombre LIKE CONCAT('%', :nombre, '%')) " +
            "AND (:id IS NULL OR uc.ID = :id) " +
            "AND (:estado IS NULL OR uc.estado = :estado)")
    Page<UnidadMedida> filterUnidadesDeMedida(@Param("id") Long id,
                                              @Param("nombre") String nombre,
                                              @Param("estado") Baja_Alta estado,
                                              Pageable pageable);

    // query to filter all unidades de medida with estado = Disponible
    @Query(value = "SELECT * FROM unidad_medida uc " +
            "where uc.estado = 0",
            countQuery = "SELECT count(*) FROM unidad_medida uc " +
                    "WHERE uc.estado = 0",
            nativeQuery = true
    )
    List<UnidadMedida> findAllByEstadoDisponible();

}
