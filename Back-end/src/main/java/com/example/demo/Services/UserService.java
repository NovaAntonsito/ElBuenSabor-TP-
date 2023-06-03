package com.example.demo.Services;

import com.example.demo.Entitys.Usuario;
import com.example.demo.Repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class UserService implements UserServiceInterface {

    private final UserRepository userRepository;

    @Override
    public Page<Usuario> viewAllUsuarios(Pageable page) throws Exception {
        return userRepository.findAll(page);
    }

    @Override
    public Page<Usuario> filterUsuarios(String nombre, Pageable pageable) throws Exception {
        return userRepository.filterUsers(nombre, pageable)
    }


}
