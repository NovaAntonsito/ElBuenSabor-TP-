package com.example.demo.Repository;

import com.example.demo.Entitys.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;


//Razon porque no es generico, la id de Auth0 es un String que es Unica, No un long
//Se la extraemos de JWT que nos genera Auth0 y la vinculamos al propio usuario
public interface UserRepository extends JpaRepository<Usuario, String> {

}
