package com.example.demo.Services;

import com.example.demo.Entitys.Rol;
import com.example.demo.Repository.RolesRepository;
import com.example.demo.Services.Interfaces.RolServiceInterface;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class RolService implements RolServiceInterface {
    private final RolesRepository rolesRepository;


    @Override
    public Rol saveRol(Rol newRol) throws Exception {
        return rolesRepository.save(newRol);
    }

    @Override
    public Boolean checkID(String id) throws Exception {
        return rolesRepository.existsById(id);
    }

    @Override
    public Page<Rol> rolPage(Pageable page) throws Exception {
        return rolesRepository.findAll(page);
    }

    @Override
    public Rol findbyID(String id) throws Exception {
        return rolesRepository.findRolbyID(id);
    }


}
