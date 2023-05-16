package com.example.demo.Repository;

import com.example.demo.Entitys.Categoria;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoriaRepository extends BaseRepository<Categoria, Long> {
    Categoria findByID(Long ID);

    @Query(value = "select * from categoria c where c.categoria_padre = :id", nativeQuery = true)
    Page<Categoria> findParent(@Param("id") Long id, Pageable page);

    @Query(value = "SELECT * FROM categoria c " +
            "where (:nombre is null or c.nombre like concat('%', :nombre, '%')) " +
            "and (:id is null or c.categoria_padre = :id)",
            countQuery = "SELECT count(*) FROM categoria c " +
                    "where (:nombre is null or c.nombre like concat('%', :nombre, '%')) " +
                    "and (:id is null or c.categoria_padre = :id)",
            nativeQuery = true
    )
    Page<Categoria> findParentAndName(@Param("id") Long id, @Param("nombre") String nombre, Pageable pageable);

}
