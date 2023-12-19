package com.gm2.pdv.controller;

import com.gm2.pdv.entity.User;
import com.gm2.pdv.repository.UserRepository;
import com.gm2.pdv.service.UserSevice;
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

    private final UserSevice userSevice;

    @Autowired
    public UserController(UserSevice userSevice) {
        this.userSevice = userSevice;
    }

    @GetMapping
    public ResponseEntity<?> getAll() { return new ResponseEntity<>(userSevice.findAll(), HttpStatus.OK);}

    @PostMapping
    public ResponseEntity<?> post(@RequestBody User user) {
        try {
            user.setEnabled(true);
            return new ResponseEntity<>(userSevice.save(user), HttpStatus.CREATED);
        } catch (Exception error) {
            return new ResponseEntity<>(error.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping
    public ResponseEntity<?> put(@RequestBody User user) {
        Optional<User> userToEdit = userSevice.findById(user.getId());

        if (userToEdit.isPresent()) {
            userSevice.save(user);
            return new ResponseEntity<>(user, HttpStatus.OK);
        }

        return ResponseEntity.notFound().build();
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable long id) {
        try {
            userSevice.deleteById(id);
            return new ResponseEntity<>("Usuário excluído com sucesso!", HttpStatus.OK);
        } catch (Exception error) {
            return new ResponseEntity<>(error.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
