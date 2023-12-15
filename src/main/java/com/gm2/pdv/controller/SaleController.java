package com.gm2.pdv.controller;

import com.gm2.pdv.dto.SaleDTO;
import com.gm2.pdv.service.SaleService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/sale")
@RequiredArgsConstructor
public class SaleController {

    private final SaleService saleService;


    @GetMapping()
    public ResponseEntity getAll() {
        return new ResponseEntity(saleService.findAll(), HttpStatus.OK);
    }


    @PostMapping()
    public ResponseEntity post(@RequestBody SaleDTO saleDTO) {

        try {
            long id = saleService.save(saleDTO);
            return new ResponseEntity<>("venda realizada com sucesso: " + id, HttpStatus.CREATED);
        } catch (Exception error) {
            return new ResponseEntity<>(error.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
