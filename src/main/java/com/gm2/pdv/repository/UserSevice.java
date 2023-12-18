package com.gm2.pdv.repository;

import com.gm2.pdv.dto.UserDTO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserSevice {

    private UserRepository userRepository;

    public List<UserDTO> getAll() {
        return userRepository.findAll().stream().map(user ->{

        }).collect(Collectors.toList());
    }
}
