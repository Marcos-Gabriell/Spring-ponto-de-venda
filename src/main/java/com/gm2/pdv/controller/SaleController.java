package com.gm2.pdv.controller;


import com.gm2.pdv.dto.ResponseDTO;
import com.gm2.pdv.dto.SaleDTO;
import com.gm2.pdv.dto.SaleInfoDTO;
import com.gm2.pdv.exceptions.InvalidOperationException;
import com.gm2.pdv.exceptions.NoltemException;
import com.gm2.pdv.service.SaleService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@Controller
@RequestMapping("/sale")
@RequiredArgsConstructor
public class SaleController {

    @Autowired
    private final SaleService saleService;

    @GetMapping
    public ResponseEntity getAll() {
        return new ResponseEntity(saleService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")  // Adiciona o path variable para o ID
    public ResponseEntity getById(@PathVariable long id) {
        try {
            return new ResponseEntity(saleService.getById(id), HttpStatus.OK);
        } catch (NoltemException | InvalidOperationException error) {
            return new ResponseEntity<>(new ResponseDTO(error.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping
    public ResponseEntity post(@Valid @RequestBody SaleDTO saleDTO) {
        try {
            long id = saleService.save(saleDTO);
            return new ResponseEntity<>(new ResponseDTO("venda realizada com sucesso!"), HttpStatus.CREATED);
        } catch (NoltemException | InvalidOperationException error) {
            return new ResponseEntity<>(new ResponseDTO(error.getMessage()), HttpStatus.BAD_REQUEST);
        } catch (Exception error) {
            return new ResponseEntity<>(new ResponseDTO(error.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
