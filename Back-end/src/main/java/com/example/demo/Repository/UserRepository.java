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

    @Query(value = "SELECT * FROM users u WHERE (:username is null or :username like u.id) limit 1", nativeQuery = true)
    Usuario userFound(@Param("username") String username);

    @Query(value = "SELECT * FROM users u WHERE (:username is null or :username like u.username) limit 1", nativeQuery = true)
    Usuario getUserbyUsername(@Param("username") String username);
}
