package com.example.demo.Services;

import com.example.demo.Entitys.Direccion;
import com.example.demo.Entitys.Usuario;
import com.example.demo.Repository.UserRepository;
import com.example.demo.Services.Interfaces.UserServiceInterface;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

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
    public Page<Usuario> filterUsuarios(String username, Pageable pageable) throws Exception {
        return userRepository.filterUsers(username, pageable);
    }

    @Override
    public Usuario userbyID(String username) throws Exception {
        return userRepository.userFound(username);
    }

    @Override
    public Usuario userbyUsername(String username) throws Exception {
        return userRepository.getUserbyUsername(username);
    }

    @Override
    public Usuario addAddressToUser(String id, Direccion direccion) throws Exception {
        Usuario userFound = userRepository.userFound(id);
        userFound.getDireccionList().add(direccion);
        return userFound;
    }

    @Override
    public Boolean existsbyID(String userID) throws Exception {
        return userRepository.existsById(userID);
    }

    @Override
    public Usuario saveUser(Usuario newUser) throws Exception {
        return userRepository.save(newUser);
    }


}
