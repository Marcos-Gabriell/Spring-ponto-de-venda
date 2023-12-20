package com.gm2.pdv.service;

import com.gm2.pdv.dto.UserDTO;
import com.gm2.pdv.entity.User;
import com.gm2.pdv.exceptions.NoltemException;
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

    public UserDTO findById(long id ) {


        Optional <User> optional = userRepository.findById(id);

        if(!optional.isPresent()) {
            throw new NoltemException("Usuário não encontrado!" );
        }
        User user = optional.get();
        return new UserDTO(user.getId(), user.getName(), user.isEnabled());
        if(!optional.isPresent()) {
            throw new NoltemException("Usuário não encontrado!" );
        }

        userRepository.save(user);
        return new UserDTO(user.getId(), user.getName(), user.isEnabled());
    }


    public UserDTO upadate(User user) {
        Optional <User> optional = userRepository.findById(user.getId());

        if(!optional.isPresent()) {
            throw new NoltemException("Usuário não encontrado!" );
        }
    }

    public void deleteById(long id) {
        userRepository.deleteById(id);
    }
}
