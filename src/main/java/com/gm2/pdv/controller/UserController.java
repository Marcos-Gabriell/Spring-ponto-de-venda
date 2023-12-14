package com.gm2.pdv.controller;

import com.gm2.pdv.entity.User;
import com.gm2.pdv.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/user")
public class UserController {

    private UserRepository userRepository;


    public UserController(@Autowired UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping
    public ResponseEntity getAll() {
        List<User> userList = userRepository.findAll();

        if (userList.isEmpty()) {
            return new ResponseEntity<>("Nenhum usuário encontrado.", HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(userList, HttpStatus.OK);
        }
    }


    @PostMapping
    public ResponseEntity post(@RequestBody User user) { // Corrigido o tipo do parâmetro para User
        try {
            user.setEnabled(true);
            return new ResponseEntity<>(userRepository.save(user), HttpStatus.CREATED);
        } catch (Exception error) {
            return new ResponseEntity(error.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping
    public ResponseEntity puy(@RequestBody User user) {
        Optional<User> userToEdit = userRepository.findById(user.getId());

        if(userToEdit.isPresent()) {
            userRepository.save(user);
            return new ResponseEntity(user, HttpStatus.OK);
        }

        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable long id) {
        try {
            userRepository.deleteById(id);
            return new ResponseEntity<>("Usuário excluído com sucesso!", HttpStatus.OK);
        } catch (Exception error) {
            return new ResponseEntity<>(error.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}