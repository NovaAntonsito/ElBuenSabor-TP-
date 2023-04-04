package com.example.demo.Repository;

import com.example.demo.Entitys.User;

import java.util.Optional;

public interface UserRepository extends BaseRepository<User,Long> {

    Optional<User> findByUsername(String username);

}
