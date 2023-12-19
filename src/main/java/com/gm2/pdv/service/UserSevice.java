package com.gm2.pdv.service;

import com.gm2.pdv.dto.UserDTO;
import com.gm2.pdv.entity.User;
import com.gm2.pdv.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserSevice {

    private UserRepository userRepository;

    public List<UserDTO> findAll() {
        return userRepository.findAll().stream().map(user ->
                new UserDTO(user.getId(), user.getName(), user.isEnabled()) ).collect(Collectors.toList());
    }

    public User save(User user) {
        return userRepository.save(user);
    }

    public Opitional<User> findById(long id) {
        return userRepository.findById(id);
    }

    public void deleteById(long id){
        userRepository.deleteById(id);
    }
}
