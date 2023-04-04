package com.example.demo.Services;

import com.example.demo.Entitys.User;
import com.example.demo.Repository.BaseRepository;
import com.example.demo.Repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserServicesImpl extends BaseServiceImpl<User,Long> implements UserServices {
    private final UserRepository userRepository;

    public UserServicesImpl(BaseRepository<User, Long> baseRepository, UserRepository userRepository) {
        super(baseRepository);
        this.userRepository = userRepository;
    }

}
