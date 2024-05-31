package com.example.demo.Repository;

import com.example.demo.Entitys.Categoria;
import com.example.demo.Entitys.Enum.Baja_Alta;
import com.example.demo.Entitys.Enum.TipoCategoria;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoriaRepository extends BaseRepository<Categoria, Long> {
    @Query(value = "SELECT * FROM categoria WHERE id = :id", nativeQuery = true)
    Categoria findByID(@Param("id") Long id);

    @Query(value = "select * from categoria c where c.categoria_padre = :id", nativeQuery = true)
    Page<Categoria> findParent(@Param("id") Long id, Pageable page);
//
//    @Query(value = "SELECT * FROM categoria c " +
//            "where (:nombre is null or c.nombre like concat('%', :nombre, '%')) " +
//            "and (:id is null or c.categoria_padre = :id)" +
//            "and (:tipoCategoria is null or c.tipo = :tipoCategoria)" +
//            "and (:estado is null or c.estado = :estado)",
//            countQuery = "SELECT count(*) FROM categoria c " +
//                    "where (:nombre is null or c.nombre like concat('%', :nombre, '%')) " +
//                    "and (:id is null or c.categoria_padre = :id)" +
//                    "and (:tipoCategoria is null or c.tipo = :tipoCategoria)" +
//                    "and (:estado is null or c.estado = :estado)",
//            nativeQuery = true
//    )
//    Page<Categoria> filterCategories(@Param("id") Long id, @Param("nombre") String nombre, @Param("tipoCategoria") TipoCategoria tipoCategoria, @Param("estado") Baja_Alta estado, Pageable page);


        @Query("SELECT c FROM Categoria c " +
                "WHERE (:nombre IS NULL OR c.nombre LIKE CONCAT('%', :nombre, '%')) " +
                "AND (:id IS NULL OR c.ID = :id) " +
                "AND (:tipoCategoria IS NULL OR c.tipo = :tipoCategoria) " +
                "AND (:estado IS NULL OR c.estado = :estado)")
        Page<Categoria> filterCategories(@Param("id") Long id,
                                         @Param("nombre") String nombre,
                                         @Param("tipoCategoria") TipoCategoria tipoCategoria,
                                         @Param("estado") Baja_Alta estado,
                                         Pageable pageable);


    @Query(value = "SELECT * FROM categoria WHERE categoria_padre IS NULL AND tipo = 0", nativeQuery = true)
    List<Categoria> findCategoriaProductos();

    @Query(value = "WITH RECURSIVE Descendants AS (" +
            "SELECT id FROM categoria WHERE id = :parentId " +
            "UNION ALL " +
            "SELECT c.id FROM categoria c INNER JOIN Descendants d ON c.categoria_padre = d.id" +
            ") " +
            "SELECT * FROM categoria WHERE id NOT IN (SELECT id FROM Descendants) AND (:parentType IS NULL OR tipo = :parentType)",
            nativeQuery = true)
    List<Categoria> findAllExcludingDescendants(@Param("parentId") Long parentId, @Param("parentType") String parentType);

}

