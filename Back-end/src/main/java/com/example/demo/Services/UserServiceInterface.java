package com.example.demo.Services;

import com.example.demo.Entitys.Rol;
import com.example.demo.Entitys.Usuario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface UserServiceInterface {
    Usuario crearNuevoUsuario (Usuario user) throws Exception;
    Rol crearNuevoRol (Rol rol) throws Exception;
    void addRoltoUser(String username, String rolName) throws Exception;
    Page<Usuario> verTodosUsuarios (Pageable page) throws Exception;
    void borrarUsuario (String username) throws UsernameNotFoundException;

    Usuario actualizarUsuario(Long ID, Usuario newUser) throws UsernameNotFoundException;


}
