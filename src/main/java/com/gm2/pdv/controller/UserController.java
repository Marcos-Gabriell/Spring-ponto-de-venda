package com.gm2.pdv.controller;

import com.gm2.pdv.dto.ResponseDTO;
import com.gm2.pdv.dto.UserDTO;
import com.gm2.pdv.entity.User;
import com.gm2.pdv.exceptions.NoltemException;
import com.gm2.pdv.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<UserDTO>> getAll() {
        List<UserDTO> users = userService.findAll();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<UserDTO> post(@Valid @RequestBody User user) {
        try {
            user.setEnabled(true);
            UserDTO savedUser = userService.save(user);
            return new ResponseEntity<>(savedUser, HttpStatus.CREATED);
        } catch (Exception error) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping
    public ResponseEntity<UserDTO> put(@Valid @RequestBody User user) {
        try {
            UserDTO updatedUser = userService.update(user);
            return new ResponseEntity<>(updatedUser, HttpStatus.OK);
        } catch (NoltemException error) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDTO> delete(@PathVariable long id) {
        try {
            userService.deleteById(id);
            return new ResponseEntity<>(new ResponseDTO("Usuário excluído com sucesso!"), HttpStatus.OK);
        } catch (EmptyResultDataAccessException error) {
            return new ResponseEntity<>(new ResponseDTO("Não foi possível localizar o usuário!"), HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception error) {
            return new ResponseEntity<>(new ResponseDTO(error.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
