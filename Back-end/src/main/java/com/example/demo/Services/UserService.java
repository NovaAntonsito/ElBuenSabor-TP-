package com.example.demo.Services;

import com.example.demo.Entitys.Usuario;
import com.example.demo.Repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class UserService implements UserServiceInterface {

 private final UserRepository userRepository;


    @Override
    public Usuario saveUsuario(String subAuth0) throws Exception {
        Usuario newUser = new Usuario(subAuth0, new ArrayList<>());
        return userRepository.save(newUser);
    }
}
