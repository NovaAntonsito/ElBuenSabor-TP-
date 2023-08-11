package com.example.demo.Repository;

import com.example.demo.Entitys.Rol;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RolesRepository extends JpaRepository<Rol, String> {
    boolean existsById(String id);

    @Query(nativeQuery = true, value = "SELECT * FROM roles r WHERE (:id IS NULL OR :id LIKE r.id) limit 1")
    Rol findRolbyID (@Param("id") String id);
}
