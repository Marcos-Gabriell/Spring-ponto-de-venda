package com.gm2.pdv.controller;

import com.gm2.pdv.entity.User;
import com.gm2.pdv.repository.UserRepository;
import com.gm2.pdv.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<?> getAll() { return new ResponseEntity<>(userService.findAll(), HttpStatus.OK);}

    @PostMapping
    public ResponseEntity<?> post(@RequestBody User user) {
        try {
            user.setEnabled(true);
            return new ResponseEntity<>(userService.save(user), HttpStatus.CREATED);
        } catch (Exception error) {
            return new ResponseEntity<>(error.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping
    public ResponseEntity<?> put(@RequestBody User user) {
        Optional<User> userToEdit = userService.findById(user.getId());

        if (userToEdit.isPresent()) {
            userService.save(user);
            return new ResponseEntity<>(user, HttpStatus.OK);
        }

        return ResponseEntity.notFound().build();
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable long id) {
        try {
            userService.deleteById(id);
            return new ResponseEntity<>("Usuário excluído com sucesso!", HttpStatus.OK);
        } catch (Exception error) {
            return new ResponseEntity<>(error.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
