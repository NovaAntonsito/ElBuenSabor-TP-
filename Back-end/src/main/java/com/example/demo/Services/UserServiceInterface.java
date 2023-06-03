package com.example.demo.Services;

import com.example.demo.Entitys.Usuario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserServiceInterface {

    Page<Usuario> viewAllUsuarios (Pageable page) throws Exception;

    Page<Usuario> filterUsuarios (String nombre, Pageable pageable) throws Exception;


}
