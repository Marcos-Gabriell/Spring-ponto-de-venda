package com.gm2.pdv.controller;

import com.gm2.pdv.entity.User;

import com.gm2.pdv.respository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestBody; // Adicionado para corrigir o erro
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/user")
public class UserController {

    private UserRepository userRepository;

    // Corrigido o nome do parâmetro para userRepository
    public UserController(@Autowired UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping
    public ResponseEntity getAll() {
        return new ResponseEntity(userRepository.findAll(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity post(@RequestBody User user) { // Corrigido o tipo do parâmetro para User
        try {
            // Corrigido "retrun" para "return"
            return new ResponseEntity<>(userRepository.save(user), HttpStatus.CREATED);
        } catch (Exception error) {
            return new ResponseEntity(error.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
