package com.example.demo.Repository;

import com.example.demo.Entitys.Rol;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RolesRepository extends JpaRepository<Rol, String> {
    boolean existsById(String id);

}
