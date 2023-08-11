package com.example.demo.Services.Interfaces;

import com.example.demo.Entitys.Direccion;
import com.example.demo.Entitys.Usuario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserServiceInterface {

    Page<Usuario> viewAllUsuarios(Pageable page) throws Exception;

    Page<Usuario> filterUsuarios(String nombre, String rol, Pageable pageable) throws Exception;

    Usuario userbyID(String username) throws Exception;

    Usuario userbyUsername(String username) throws Exception;

    Usuario addAddressToUser(String username, Direccion direccion) throws Exception;

    Boolean existsbyID(String userID) throws Exception;

    Usuario saveUser(Usuario newUser) throws Exception;

    Usuario upsertUser(Usuario user) throws Exception;


}
