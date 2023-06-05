package com.example.demo.Repository;

import com.example.demo.Entitys.Usuario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<Usuario, String> {
    @Query(value = "Select * from users u where (:username is null or :username like u.username) order by u.username", nativeQuery = true )
    Page<Usuario> filterUsers(@Param("username") String username, Pageable page);
}
