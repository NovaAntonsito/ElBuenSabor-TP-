package com.example.demo.Repository;

import com.example.demo.Entitys.Rol;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import org.springframework.stereotype.Repository;

@Repository
public interface RolRepository extends BaseRepository<Rol,Long> {
    @Query(value = "SELECT COUNT(*) > 0 FROM roles WHERE nombre_rol = :nombre", nativeQuery = true)
    boolean existsByNombre(@Param("nombre") String nombre);
    Rol findByNombreRol (String nombre);
}
