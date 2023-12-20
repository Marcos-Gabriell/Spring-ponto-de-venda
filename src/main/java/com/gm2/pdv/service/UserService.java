package com.gm2.pdv.service;

import com.gm2.pdv.dto.UserDTO;
import com.gm2.pdv.entity.User;
import com.gm2.pdv.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public List<UserDTO> findAll() {
        return userRepository.findAll().stream().map(user ->
                new UserDTO(user.getId(), user.getName(), user.isEnabled())).collect(Collectors.toList());
    }

    public UserDTO save(User user) {
        userRepository.save(user);
        return new UserDTO(user.getId(), user.getName(), user.isEnabled());
    }

    public Optional<User> findById(long id ) {
        User user = userRepository.findById(id);
    }

    public Optional<User> findById(long id) {
        return userRepository.findById(id);
    }

    public void deleteById(long id) {
        userRepository.deleteById(id);
    }
}
